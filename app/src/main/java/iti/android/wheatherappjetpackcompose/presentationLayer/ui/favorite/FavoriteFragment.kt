package iti.android.wheatherappjetpackcompose.presentationLayer.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import iti.android.wheatherappjetpackcompose.R

class FavoriteFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }


}