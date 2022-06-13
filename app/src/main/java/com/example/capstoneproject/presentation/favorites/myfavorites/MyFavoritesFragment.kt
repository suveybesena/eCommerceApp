package com.example.capstoneproject.presentation.favorites.myfavorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.capstoneproject.common.extensions.Constant
import com.example.capstoneproject.databinding.FragmentMyFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFavoritesFragment : Fragment() {
    private var myFavoritesBinding: FragmentMyFavoritesBinding? = null
    lateinit var myFavoritesAdapter: MyFavoritesAdapter
    private val myFavoritesViewModel: MyFavoritesViewModel by viewModels()

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
        val sharedPref = activity?.getSharedPreferences(
            "getSharedPref", Context.MODE_PRIVATE
        )
        sharedPref?.getString(Constant.SHARED_PREF_KEY, null)
            ?.let { userId ->
                myFavoritesViewModel.handleEvent(MyFavoritesUiEvent.GetAllFavorites(userId))
            }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myFavoritesViewModel._uiState.collect { state ->
                    state.favorites.let { flow ->
                        flow?.collect { favorites ->
                            myFavoritesAdapter.differ.submitList(favorites)
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        myFavoritesAdapter = MyFavoritesAdapter()
        myFavoritesBinding?.apply {
            rvMyFavorites.adapter = myFavoritesAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myFavoritesBinding = null
    }
}