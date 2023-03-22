package iti.android.wheatherappjetpackcompose.presentationLayer.ui.settings

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryImpl
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.*
import iti.android.wheatherappjetpackcompose.databinding.FragmentSettingsBinding
import iti.android.wheatherappjetpackcompose.presentationLayer.MainActivity
import iti.android.wheatherappjetpackcompose.presentationLayer.ui.map.MapDestination
import iti.android.wheatherappjetpackcompose.utils.GPSUtils
import java.util.*

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    private val viewModel: SettingsViewModel by lazy {
        val repository: RepositoryInterface =
            RepositoryImpl.getInstance(requireActivity().application)
        ViewModelProvider(
            requireActivity(),
            SettingsViewModelFactory(repository)
        )[SettingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.settings = requireContext().getSharedSettings()


        if (requireContext().getSettingsLanguage() == Language.Arabic) {
            binding.arabicSelectedRadio.isChecked = true
            binding.englishSelectedRadio.isChecked = false
        } else {
            binding.arabicSelectedRadio.isChecked = false
            binding.englishSelectedRadio.isChecked = true
        }


        if (requireContext().getSettingsAlertProvider() == AlertProvider.Alert) {
            binding.alertDialogSelectedRadio.isChecked = true
            binding.notificationSelectedRadio.isChecked = false
        } else {
            binding.alertDialogSelectedRadio.isChecked = false
            binding.notificationSelectedRadio.isChecked = true
        }


        if (requireContext().getSettingsLocationProvider() == LocationProvider.MAP) {
            binding.mapSelectedRadio.isChecked = true
            binding.gpsSelectedRadio.isChecked = false
        } else {
            binding.mapSelectedRadio.isChecked = false
            binding.gpsSelectedRadio.isChecked = true
        }



        binding.arabicSelectedRadio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                requireContext().setSharedSettings(Language.Arabic)
                changeLocality(Constants.ARABIC)
            }
        }

        binding.englishSelectedRadio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                requireContext().setSharedSettings(Language.English)
                changeLocality(Constants.ENGLISH)
            }
        }

        binding.alertDialogSelectedRadio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                requireContext().setSharedSettings(AlertProvider.Alert)
                checkPermissionOfOverlay()
            }
        }

        binding.notificationSelectedRadio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                requireContext().setSharedSettings(AlertProvider.Notification)
            }
        }

        binding.gpsSelectedRadio.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                requireContext().setSharedSettings(LocationProvider.GPS)
                GPSUtils.getLastLocation(requireActivity()) {
                    requireContext().setSharedSettings(LatLng(it.latitude, it.longitude))
                }
            }
        }

        binding.mapSelectedRadio.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                requireContext().setSharedSettings(LocationProvider.MAP)
                val bundle = Bundle()
                bundle.putSerializable(Constants.MAP_DESTINATION, MapDestination.SETTINGS)
                NavHostFragment.findNavController(this).navigate(
                    R.id.action_settings_menu_to_mapFragment,
                    bundle
                )

            }
        }

        return binding.root
    }

    private fun changeLocality(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = activity?.resources
        val configuration = resources?.configuration

        configuration?.setLocale(locale)
        resources?.updateConfiguration(configuration, resources.displayMetrics)

        val intentActivity = Intent(requireContext(), MainActivity::class.java)
        startActivity(intentActivity)
    }

    private fun checkPermissionOfOverlay() {
        if (!Settings.canDrawOverlays(requireContext())) {
            val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
            alertDialogBuilder.setTitle(getString(R.string.display_on_top_title))
                .setMessage(getString(R.string.display_on_top_message))
                .setPositiveButton(getString(R.string.okay)) { dialog: DialogInterface, _: Int ->

                    // Navigate to Manage Overlay settings in device
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + requireContext().applicationContext.packageName)
                    )
                    startActivityForResult(intent, 1)
                    dialog.dismiss()

                }.setNegativeButton(getString(R.string.no)) { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                }.show()
        }
    }
}