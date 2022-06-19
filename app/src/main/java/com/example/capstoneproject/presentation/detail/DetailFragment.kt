package com.example.capstoneproject.presentation.detail

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
import androidx.navigation.fragment.navArgs
import com.example.capstoneproject.common.Constant.SUCCESS_ADDED_CART
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.data.entities.product.Basket
import com.example.capstoneproject.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var detailBinding: FragmentDetailBinding? = null
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    @Inject
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailBinding = FragmentDetailBinding.inflate(inflater)
        return detailBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObserve()
    }

    private fun initObserve() {
        val product = args.detail
        detailBinding?.apply {
            product.productImage?.let { ivProduct.downloadImage(it) }
            tvProductName.text = product.productTitle
            tvProductPrice.text = "$${product.productPrice}"
            tvProductDesc.text = product.productDescription
        }

        detailViewModel.handleEvent(DetailUiEvent.GetBasketItemsCount(userId))
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel._uiState.collect { state ->
                    state.basketItemsCount.let { count ->
                        detailBinding?.apply {
                            tvBasketItemCount.text = count.toString()
                        }
                    }
                }
            }
        }
    }

    private fun initListeners() {
        detailBinding?.apply {
            var count = tvItemCount.text.toString().toInt()
            bvPlus.setOnClickListener {
                count++
                tvItemCount.text = count.toString()
            }
            bvMinus.setOnClickListener {
                count--
                tvItemCount.text = count.toString()
            }
            bvAddCard.setOnClickListener {
                val itemCount = count.toString()
                val product = args.detail
                val price = tvProductPrice.text.toString().toFloat()
                val basketItem = Basket(
                    product.productTitle,
                    product.productDescription,
                    itemCount,
                    userId,
                    (count * price).toString(),
                    product.productImage
                )
                detailViewModel.handleEvent(DetailUiEvent.InsertProductToBasket(basketItem))
                Snackbar.make(requireView(), SUCCESS_ADDED_CART, Snackbar.LENGTH_LONG).show()
            }
            bvArrowBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        detailBinding = null
    }
}