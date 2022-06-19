package com.example.capstoneproject.presentation.favorites.myfavorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.capstoneproject.data.entities.product.Favorites
import com.example.capstoneproject.databinding.FragmentMyFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyFavoritesFragment : Fragment() {
    private var myFavoritesBinding: FragmentMyFavoritesBinding? = null
    lateinit var myFavoritesAdapter: MyFavoritesAdapter
    private val myFavoritesViewModel: MyFavoritesViewModel by viewModels()

    @Inject
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myFavoritesBinding = FragmentMyFavoritesBinding.inflate(inflater)
        return myFavoritesBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserve()
    }

    private fun initObserve() {
        myFavoritesViewModel.handleEvent(MyFavoritesUiEvent.GetAllFavorites(userId))
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myFavoritesViewModel._uiState.collect { state ->
                    state.favorites.let { flow ->
                        flow?.collect { favorites ->
                            if (favorites.isNotEmpty()) {
                                myFavoritesBinding?.apply {
                                    ivEmptyImage.visibility = View.GONE
                                    tvEmpty.visibility = View.GONE
                                    rvMyFavorites.visibility = View.VISIBLE
                                    myFavoritesAdapter.differ.submitList(favorites)
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        myFavoritesAdapter = MyFavoritesAdapter(object : OnFavoritesListToDeleteClickHandler {
            override fun deleteProduct(favorites: Favorites) {
                deleteFavorites(favorites)
            }
        })
        myFavoritesBinding?.apply {
            rvMyFavorites.adapter = myFavoritesAdapter
        }
    }

    private fun deleteFavorites(favorites: Favorites) {
        myFavoritesViewModel.handleEvent(MyFavoritesUiEvent.DeleteProduct(favorites))
    }

    override fun onDestroy() {
        super.onDestroy()
        myFavoritesBinding = null
    }
}