package iti.android.wheatherappjetpackcompose.presentationLayer.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryImpl
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.databinding.FragmentFavoriteBinding
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite.DeleteFavoriteUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite.FavoriteUseCases
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite.GetFavoritesUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite.InsertFavoriteUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.utils.DataResponseState
import iti.android.wheatherappjetpackcompose.utils.findNavController
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by lazy {
        val repository: RepositoryInterface =
            RepositoryImpl.getInstance(requireActivity().application)
        val useCases = FavoriteUseCases(
            deleteFavorite = DeleteFavoriteUseCase(repository),
            insertFavorite = InsertFavoriteUseCase(repository),
            getFavoritesUseCase = GetFavoritesUseCase(repository)
        )
        ViewModelProvider(
            requireActivity(),
            FavoriteViewModelFactory(useCases)
        )[FavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        binding.lifecycleOwner = this
        binding.addFavoriteBtn.setOnClickListener {
            findNavController(requireActivity())?.navigate(R.id.action_favorite_menu_to_mapFragment)
        }
        viewModel.getFavPlacesData()
        val adapter = FavoriteAdapter(FavoriteAdapter.ItemOnCLickListener {
            findNavController(requireActivity())?.navigate(R.id.action_favorite_menu_to_details_menu)
        })
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is DataResponseState.OnNothingData -> {
                        binding.stateEmptyDataHolder.visibility = View.VISIBLE
                        binding.stateLoadingHolder.visibility = View.GONE
                        binding.stateErrorHolder.visibility = View.GONE
                        binding.rvFavorite.visibility = View.GONE
                    }
                    is DataResponseState.OnLoading -> {
                        binding.stateEmptyDataHolder.visibility = View.GONE
                        binding.stateLoadingHolder.visibility = View.VISIBLE
                        binding.stateErrorHolder.visibility = View.GONE
                        binding.rvFavorite.visibility = View.GONE
                    }
                    is DataResponseState.OnError -> {
                        binding.stateEmptyDataHolder.visibility = View.GONE
                        binding.stateLoadingHolder.visibility = View.GONE
                        binding.stateErrorHolder.visibility = View.VISIBLE
                        binding.rvFavorite.visibility = View.GONE
                    }
                    is DataResponseState.OnSuccess -> {
                        binding.stateEmptyDataHolder.visibility = View.GONE
                        binding.stateLoadingHolder.visibility = View.GONE
                        binding.stateErrorHolder.visibility = View.GONE
                        binding.rvFavorite.visibility = View.VISIBLE
                        adapter.submitList(state.data)
                    }
                }
            }

        }

        return binding.root
    }
}