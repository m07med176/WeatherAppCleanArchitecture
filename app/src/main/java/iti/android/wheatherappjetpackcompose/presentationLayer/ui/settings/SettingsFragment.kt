package iti.android.wheatherappjetpackcompose.presentationLayer.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import iti.android.wheatherappjetpackcompose.R

class SettingsFragment : Fragment() {


    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

}