package com.example.capstoneproject.presentation.basket

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.example.capstoneproject.R
import com.example.capstoneproject.data.entities.product.Basket
import com.example.capstoneproject.data.entities.product.Purchased
import com.example.capstoneproject.databinding.FragmentBasketBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class BasketFragment() : Fragment() {
    private var basketBinding: FragmentBasketBinding? = null
    private val basketViewModel: BasketViewModel by viewModels()
    lateinit var basketAdapter: BasketAdapter
    private lateinit var request: WorkRequest


    @Inject
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        basketBinding = FragmentBasketBinding.inflate(inflater)
        return basketBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserver()
        initListener()
    }

    private fun initListener() {
        basketBinding?.apply {
            bvAdress.setOnClickListener {
                findNavController().navigate(R.id.action_basketFragment_to_mapFragment)
            }
        }
    }

    private fun initObserver() {
        val workCondition = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        request = PeriodicWorkRequestBuilder<RunWorker>(1, TimeUnit.MINUTES)
            .setConstraints(workCondition)
            .build()

        basketViewModel.handleEvent(BasketUiEvent.GetAllBasketItems(userId))
        basketViewModel.handleEvent(BasketUiEvent.GetBasketItemCount(userId))
        basketViewModel.handleEvent(BasketUiEvent.GetBagBasketFromAPI(userId))
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                basketViewModel._uiState.collect { state ->
                    state.basketItems.let { flow ->
                        flow?.collect { basketItems ->
                            if (basketItems.isNotEmpty()) {
                                setWorkReminder(request)
                                basketBinding?.apply {
                                    layoutEmpty.visibility = View.GONE
                                    layoutBasket.visibility = View.VISIBLE
                                    basketAdapter.differ.submitList(basketItems)
                                    var start = 0.0
                                    basketItems.forEach { product ->
                                        val price = product.productPrice?.toDouble()
                                        price?.let { it ->
                                            start = (start + it)
                                            tvShoppingSubTotal.text = "$${start.toInt()}"
                                        }
                                        bvProceedToCheckout.setOnClickListener {
                                            cancelReminder(request)
                                            basketItems.forEach { purchasedItem ->
                                                val purchased = Purchased(
                                                    purchasedItem.productName,
                                                    purchasedItem.productCount,
                                                    purchasedItem.currentUserId,
                                                    purchasedItem.productPrice,
                                                    purchasedItem.productImage,
                                                )
                                                basketViewModel.handleEvent(
                                                    BasketUiEvent.InsertPurchasedToDatabase(
                                                        purchased
                                                    )
                                                )
                                            }
                                            if (state.error == null) {
                                                showSuccessDialog()
                                                basketItems.forEach { delete ->
                                                    basketViewModel.handleEvent(
                                                        BasketUiEvent.DeleteBasketItem(
                                                            delete
                                                        )
                                                    )
                                                }
                                                basketViewModel.handleEvent(
                                                    BasketUiEvent.DeleteBasketItem(
                                                        product
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    tvShoppingSubTotal.text = "Subtotal : $${start}"
                                }
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                basketViewModel._uiState.collect { state ->
                    state.basketItemsCount?.let { count ->
                        basketBinding?.apply {
                            tvBasketItemCount.text = count.toString()
                        }
                    }
                }
            }
        }
    }

    private fun cancelReminder(uploadRequest: WorkRequest) {
        WorkManager.getInstance(requireContext())
            .cancelWorkById(uploadRequest.id)
    }

    private fun setWorkReminder(uploadRequest: WorkRequest) {
        WorkManager.getInstance(requireContext())
            .enqueue(uploadRequest)
    }

    private fun showSuccessDialog() {
        val layoutView = LayoutInflater.from(requireContext())
            .inflate(R.layout.layout_success, null, false)
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(layoutView)
        val alertDialog = dialogBuilder.create()

        val timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                alertDialog.show()
                alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }

            override fun onFinish() {
                alertDialog.dismiss()
                requireView().findNavController()
                    .navigate(R.id.homeFragment)
            }
        }
        timer.start()
    }

    private fun initRecyclerView() {
        basketAdapter = BasketAdapter(object : OnBasketListToDeleteClickHandler {
            override fun deleteItem(basket: Basket) {
                deleteProduct(basket)
            }

        })
        basketBinding?.apply {
            rvShoppingItems.adapter = basketAdapter
        }
    }

    fun deleteProduct(basket: Basket) {
        basketViewModel.handleEvent(BasketUiEvent.DeleteBasketItem(basket))
    }

    override fun onDestroy() {
        super.onDestroy()
        basketBinding = null
    }
}