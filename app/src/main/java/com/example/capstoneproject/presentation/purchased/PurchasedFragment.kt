package com.example.capstoneproject.presentation.purchased

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.databinding.FragmentPurchasedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PurchasedFragment : Fragment() {
    private var purchasedBinding: FragmentPurchasedBinding? = null
    private val purchasedViewModel: PurchasedViewModel by viewModels()
    private lateinit var purchasedAdapter: PurchasedAdapter

    @Inject
    lateinit var userId: String

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
        initListeners()
    }

    private fun initListeners() {
        purchasedBinding?.bvArrowBack?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initObserve() {
        purchasedViewModel.handleEvent(PurchasedUiEvent.GetPurchasedProducts(userId))

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                purchasedViewModel._uiState.collect { state ->
                    state.purchasedProducts.let { flow ->
                        flow?.collect { purchased ->
                            println(purchased)
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