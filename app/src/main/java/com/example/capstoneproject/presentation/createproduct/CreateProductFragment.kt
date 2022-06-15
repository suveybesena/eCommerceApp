package com.example.capstoneproject.presentation.createproduct

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
import com.example.capstoneproject.R
import com.example.capstoneproject.common.Constant.INSERT_PRODUCT_ERROR
import com.example.capstoneproject.common.Constant.INSERT_PRODUCT_SUCCESS
import com.example.capstoneproject.data.entities.product.Product
import com.example.capstoneproject.data.model.InsertedProduct
import com.example.capstoneproject.databinding.FragmentCreateProductBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateProductFragment : Fragment() {
    private var binding: FragmentCreateProductBinding? = null
    private val viewModel: CreateProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateProductBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding?.apply {
            bvProceedToCheckout.setOnClickListener {
                val productTitle = etProductTitle.text.toString()
                val productDescription = etProductDescription.text.toString()
                val productPrice = etProductPrice.text.toString()
                val productCategory = etProductCategory.text.toString()
                val productImage = etProductImage.text.toString()
                val insertedProduct = InsertedProduct(
                    productTitle,
                    productPrice,
                    productDescription,
                    productCategory,
                    productImage
                )
                viewModel.handleEvent(CreateProductUiEvent.CreateProduct(insertedProduct))
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel._uiState.collect { state ->
                            if (state.apiError != null) {
                                Snackbar.make(
                                    requireView(),
                                    INSERT_PRODUCT_ERROR,
                                    Snackbar.LENGTH_LONG
                                ).show()
                            } else {
                                state.product?.let { product ->
                                    val productInserted = Product(
                                        product.title,
                                        product.description,
                                        product.category,
                                        product.price,
                                        product.image
                                    )
                                    viewModel.handleEvent(
                                        CreateProductUiEvent.InsertProductToDatabase(
                                            productInserted
                                        )
                                    )
                                    if (state.dbError.isNullOrEmpty()) {
                                        Snackbar.make(
                                            requireView(),
                                            INSERT_PRODUCT_SUCCESS,
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                        findNavController().navigate(R.id.homeFragment)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}