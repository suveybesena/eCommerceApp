package com.example.capstoneproject.presentation.shopping

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
import com.example.capstoneproject.databinding.FragmentShoppingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingFragment() : Fragment() {
    private var shoppingBinding: FragmentShoppingBinding? = null
    private val shoppingViewModel: ShoppingViewModel by viewModels()
    lateinit var basketAdapter: BasketAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shoppingBinding = FragmentShoppingBinding.inflate(inflater)
        return shoppingBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserver()
    }

    private fun initObserver() {
        val sharedPref = activity?.getSharedPreferences(
            "getSharedPref", Context.MODE_PRIVATE
        )
        sharedPref?.getString(Constant.SHARED_PREF_KEY, null)
            ?.let { userId ->
                shoppingViewModel.handleEvent(ShoppingUiEvent.GetAllBasketItems(userId))
            }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                shoppingViewModel._uiState.collect { state ->
                    state.basketItems.let { flow ->
                        flow?.collect { basketItems ->
                            basketAdapter.differ.submitList(basketItems)
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        basketAdapter = BasketAdapter()
        shoppingBinding?.apply {
            rvShoppingItems.adapter = basketAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        shoppingBinding = null
    }
}