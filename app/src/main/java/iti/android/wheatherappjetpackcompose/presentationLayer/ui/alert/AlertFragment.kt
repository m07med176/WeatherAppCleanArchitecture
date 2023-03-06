package iti.android.wheatherappjetpackcompose.presentationLayer.ui.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import iti.android.wheatherappjetpackcompose.R

class AlertFragment : Fragment() {
    private lateinit var viewModel: AlertViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(this)[AlertViewModel::class.java]
        return inflater.inflate(R.layout.fragment_alert, container, false)
    }
}