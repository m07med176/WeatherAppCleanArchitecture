package iti.android.wheatherappjetpackcompose.presentationLayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.utils.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000)
            findNavController(requireActivity())?.navigate(R.id.action_splashFragment_to_home_menu)
        }
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


}