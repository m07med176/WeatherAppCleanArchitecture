package iti.android.wheatherappjetpackcompose.presentationLayer.ui.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryImpl
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.databinding.FragmentAlertBinding
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.AlertUseCases
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.DeleteAlertUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.GetAlertUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.InsertAlertsUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.utils.DataResponseState
import kotlinx.coroutines.launch

class AlertFragment : Fragment() {
    private lateinit var binding: FragmentAlertBinding
    private val viewModel: AlertViewModel by lazy {

        val repository: RepositoryInterface =
            RepositoryImpl.getInstance(requireActivity().application)
        val useCases = AlertUseCases(
            getAlert = GetAlertUseCase(repository),
            insertAlert = InsertAlertsUseCase(repository),
            deleteAlert = DeleteAlertUseCase(repository)
        )
        ViewModelProvider(
            requireActivity(),
            AlertViewModelFactory(useCases)
        )[AlertViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alert, container, false)
        binding.lifecycleOwner = this
        viewModel.getAlertsList()


        binding.addAlertBtn.setOnClickListener {
            AlertTimeDialog().show(requireActivity().supportFragmentManager, "AlertDialog")
        }

        val adapter = AlertAdapter(AlertAdapter.ItemOnCLickListener { alertModel ->
            Toast.makeText(requireContext(), "this is test for toast", Toast.LENGTH_SHORT).show()
        })
        binding.mAdapter = adapter
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is DataResponseState.OnNothingData -> {
                        binding.stateEmptyDataHolder.visibility = View.VISIBLE
                        binding.stateLoadingHolder.visibility = View.GONE
                        binding.stateErrorHolder.visibility = View.GONE
                        binding.rvAlert.visibility = View.GONE
                    }
                    is DataResponseState.OnLoading -> {
                        binding.stateEmptyDataHolder.visibility = View.GONE
                        binding.stateLoadingHolder.visibility = View.VISIBLE
                        binding.stateErrorHolder.visibility = View.GONE
                        binding.rvAlert.visibility = View.GONE
                    }
                    is DataResponseState.OnError -> {
                        binding.stateEmptyDataHolder.visibility = View.GONE
                        binding.stateLoadingHolder.visibility = View.GONE
                        binding.stateErrorHolder.visibility = View.VISIBLE
                        binding.rvAlert.visibility = View.GONE
                    }
                    is DataResponseState.OnSuccess -> {
                        binding.stateEmptyDataHolder.visibility = View.GONE
                        binding.stateLoadingHolder.visibility = View.GONE
                        binding.stateErrorHolder.visibility = View.GONE
                        binding.rvAlert.visibility = View.VISIBLE
                        adapter.submitList(state.data)
                    }
                }
            }
        }

        return binding.root
    }
}