package iti.android.wheatherappjetpackcompose.presentationLayer.ui.alert

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryImpl
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.databinding.AlertPicTimeDialogButtomSheetBinding
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.AlertUseCases
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.DeleteAlertUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.GetAlertUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.InsertAlertsUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.utils.TimeConverter
import iti.android.wheatherappjetpackcompose.presentationLayer.ui.alert.services.AlertPeriodicWorkManger
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class AlertTimeDialog : DialogFragment() {

    private val viewModel: AlertViewModel by viewModels {
        val repositoryImpl = RepositoryImpl.getInstance(requireActivity().application)
        val useCases = AlertUseCases(
            deleteAlert = DeleteAlertUseCase(repositoryImpl),
            getAlert = GetAlertUseCase(repositoryImpl),
            insertAlert = InsertAlertsUseCase(repositoryImpl)
        )
        AlertViewModelFactory(useCases)
    }

    private lateinit var binding: AlertPicTimeDialogButtomSheetBinding
    private lateinit var weatherAlert: AlertEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.background_search)
        binding = AlertPicTimeDialogButtomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()

        binding.btnFrom.setOnClickListener {
            showDatePicker(true)
        }

        binding.btnTo.setOnClickListener {
            showDatePicker(false)
        }

        binding.btnSave.setOnClickListener {

            lifecycleScope.launch {
                val result = viewModel.insertAlert(weatherAlert)
                setPeriodWorkManger(result)
            }
            dialog!!.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dialog!!.dismiss()
        }

    }

    private fun setPeriodWorkManger(id: Long) {

        val data = Data.Builder()
        data.putLong("id", id)

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            AlertPeriodicWorkManger::class.java,
            24, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "$id",
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
    }

    private fun setInitialData() {
        val rightNow = Calendar.getInstance()

        // init day
        val year = rightNow.get(Calendar.YEAR)
        val month = rightNow.get(Calendar.MONTH)
        val day = rightNow.get(Calendar.DAY_OF_MONTH)
        val date = "$day/${month + 1}/$year"
        val dayNow = TimeConverter.convertStringToTimestamp(date, TimeConverter.DATE_PATTERN_SLASH)

        // init time
        val currentHour = TimeUnit.HOURS.toSeconds(rightNow.get(Calendar.HOUR_OF_DAY).toLong())
        val currentMinute = TimeUnit.MINUTES.toSeconds(rightNow.get(Calendar.MINUTE).toLong())

        val currentTime = (currentHour + currentMinute).minus(3600L * 2)
        val currentTimeText =
            TimeConverter.convertTimestampToString((currentTime + 60), TimeConverter.TIME_PATTERN)

        val afterOneHour = currentTime.plus(3600L)
        val afterOneHourText =
            TimeConverter.convertTimestampToString(afterOneHour, TimeConverter.TIME_PATTERN)


        // set values
        val currentDate =
            TimeConverter.convertTimestampToString(dayNow, TimeConverter.DATE_PATTERN_SLASH)
        binding.btnFrom.text = currentDate.plus(" ").plus(currentTimeText)
        binding.btnTo.text = currentDate.plus(" ").plus(afterOneHourText)


        //init default model
        weatherAlert = AlertEntity(
            startTime = (currentTime + 60),
            endTime = afterOneHour,
            startDate = dayNow,
            endDate = dayNow
        )

    }

    private fun showTimePicker(isFrom: Boolean, datePicker: Long) {
        val rightNow = Calendar.getInstance()
        val currentHour = rightNow.get(Calendar.HOUR_OF_DAY)
        val currentMinute = rightNow.get(Calendar.MINUTE)
        val listener: (TimePicker?, Int, Int) -> Unit =
            { _: TimePicker?, hour: Int, minute: Int ->
                val time = TimeUnit.MINUTES.toSeconds(minute.toLong()) +
                        TimeUnit.HOURS.toSeconds(hour.toLong()) - (3600L * 2)
                val dateString = TimeConverter.convertTimestampToString(
                    datePicker,
                    TimeConverter.DATE_PATTERN_SLASH
                )
                val timeString =
                    TimeConverter.convertTimestampToString(time, TimeConverter.TIME_PATTERN)
                val text = dateString.plus(" ").plus(timeString)
                if (isFrom) {
                    weatherAlert.startTime = time
                    weatherAlert.startDate = datePicker
                    binding.btnFrom.text = text
                } else {
                    weatherAlert.endTime = time
                    weatherAlert.endDate = datePicker
                    binding.btnTo.text = text
                }
            }

        val timePickerDialog = TimePickerDialog(
            requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
            listener, currentHour, currentMinute, false
        )

        timePickerDialog.setTitle(getString(R.string.time_picker))
        timePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        timePickerDialog.show()
    }

    private fun showDatePicker(isFrom: Boolean) {
        val myCalender = Calendar.getInstance()
        val year = myCalender[Calendar.YEAR]
        val month = myCalender[Calendar.MONTH]
        val day = myCalender[Calendar.DAY_OF_MONTH]
        val myDateListener =
            OnDateSetListener { view, year, month, day ->
                if (view.isShown) {
                    val date = "$day/${month + 1}/$year"
                    showTimePicker(
                        isFrom,
                        TimeConverter.convertStringToTimestamp(
                            date,
                            TimeConverter.DATE_PATTERN_SLASH
                        )
                    )
                }
            }
        val datePickerDialog = DatePickerDialog(
            requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
            myDateListener, year, month, day
        )
        datePickerDialog.setTitle(getString(R.string.date_picker))
        datePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        datePickerDialog.show()
    }

    override fun onStart() {
        super.onStart()
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

}