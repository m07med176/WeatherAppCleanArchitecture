package iti.android.wheatherappjetpackcompose.presentationLayer.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryImpl
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.databinding.FragmentSettingsBinding
import iti.android.wheatherappjetpackcompose.presentationLayer.MainActivity
import kotlinx.coroutines.launch
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
        viewModel.getSettingsData()


        binding.arabicSelectedRadio.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                changeLocality(Constants.ARABIC)
            }
        }

        binding.englishSelectedRadio.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                changeLocality(Constants.ENGLISH)
            }
        }



        lifecycleScope.launch {
            viewModel.settings.collect {
                binding.settings = it
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

}