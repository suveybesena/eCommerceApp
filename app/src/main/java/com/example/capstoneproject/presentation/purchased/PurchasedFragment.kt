package com.example.capstoneproject.presentation.purchased

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
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.databinding.FragmentPurchasedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PurchasedFragment : Fragment() {
    private var purchasedBinding: FragmentPurchasedBinding? = null
    private val purchasedViewModel: PurchasedViewModel by viewModels()
    private lateinit var purchasedAdapter: PurchasedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        purchasedBinding = FragmentPurchasedBinding.inflate(inflater)
        return purchasedBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObserve()
    }

    private fun initObserve() {
        val sharedPref = activity?.getSharedPreferences(
            "getSharedPref", Context.MODE_PRIVATE
        )
        sharedPref?.getString(Constant.SHARED_PREF_KEY, null)
            ?.let { userId ->
                purchasedViewModel.handleEvent(PurchasedUiEvent.GetPurchasedProducts(userId))
            }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                purchasedViewModel._uiState.collect { state ->
                    state.purchasedProducts.let { flow ->
                        flow?.collect { purchased ->
                            if (purchased.isNotEmpty()) {
                                purchasedBinding?.apply {
                                    layoutEmpty.visibility = View.GONE
                                    layoutPurchased.visibility = View.VISIBLE
                                    purchasedAdapter.differ.submitList(purchased)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initRecycler() {
        purchasedAdapter = PurchasedAdapter()
        purchasedBinding?.apply {
            rvPurchasedItems.adapter = purchasedAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        purchasedBinding = null
    }
}