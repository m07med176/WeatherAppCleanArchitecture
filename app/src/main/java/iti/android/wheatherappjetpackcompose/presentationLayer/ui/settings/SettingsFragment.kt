package iti.android.wheatherappjetpackcompose.presentationLayer.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.dataLayer.repository.ISettingsRepository
import iti.android.wheatherappjetpackcompose.dataLayer.repository.SettingsRepositoryImpl
import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.DataStoreManager
import iti.android.wheatherappjetpackcompose.databinding.FragmentSettingsBinding
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.settings.GetSharedSettings
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.settings.SettingsUseCases
import iti.android.wheatherappjetpackcompose.utils.toast
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    private val viewModel: SettingsViewModel by lazy {
        val cash = DataStoreManager(requireContext())
        val repository: ISettingsRepository = SettingsRepositoryImpl(cash)
        val useCases = SettingsUseCases(
            getSharedSettings = GetSharedSettings(repository)
        )
        ViewModelProvider(
            requireActivity(),
            SettingsViewModelFactory(useCases)
        )[SettingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.lifecycleOwner = this
        viewModel.getSettingsData()

        lifecycleScope.launch {
            viewModel.settings.collect {
                it.toString().toast(requireContext())
            }
        }

        return binding.root
    }

}