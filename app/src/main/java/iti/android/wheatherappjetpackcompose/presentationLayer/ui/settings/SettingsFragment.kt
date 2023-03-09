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
import iti.android.wheatherappjetpackcompose.utils.toast
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    private val viewModel: SettingsViewModel by lazy {
        val cash = DataStoreManager.invoke(requireContext())
        val repository: ISettingsRepository = SettingsRepositoryImpl(cash)
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



        lifecycleScope.launch {
            viewModel.settings.collect {
                binding.settings = it
                it.toString().toast(requireContext())
            }
        }

        return binding.root
    }

}