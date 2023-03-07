package iti.android.wheatherappjetpackcompose.presentationLayer.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import iti.android.wheatherappjetpackcompose.R

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        return inflater.inflate(R.layout.fragment_map, container, false)
    }


}