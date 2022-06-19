package com.example.capstoneproject.presentation.categories

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.common.Constant.INSERT_PRODUCT_COLLECTIONS
import com.example.capstoneproject.common.Constant.INSERT_PRODUCT_FAVORITES
import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.data.entities.product.Favorites
import com.example.capstoneproject.data.entities.product.Product
import com.example.capstoneproject.databinding.FragmentCategoriesBinding
import com.example.capstoneproject.presentation.home.OnProductListClickHandler
import com.example.capstoneproject.presentation.home.OnProductListToCollectionsClickHandler
import com.example.capstoneproject.presentation.home.OnProductListToFavoritesClickHandler
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private var categoriesBinding: FragmentCategoriesBinding? = null
    lateinit var categoriesResultAdapter: CategoriesResultAdapter
    private val args: CategoriesFragmentArgs by navArgs()
    private val categoriesViewModel: CategoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoriesBinding = FragmentCategoriesBinding.inflate(inflater)
        return categoriesBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserve()
        initListeners()
    }

    private fun initListeners() {
        categoriesBinding?.bvArrowBack?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initObserve() {
        val categoryName = args.category
        categoriesViewModel.handleEvent(
            CategoriesUiEvent.GetProductsByCategoriesUseCase(
                categoryName
            )
        )
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoriesViewModel._uiState.collect { state ->
                    state.productModels.let { products ->
                        categoriesResultAdapter.differ.submitList(products)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        categoriesResultAdapter = CategoriesResultAdapter(object : OnProductListClickHandler {
            override fun goDetailPage(productModel: Product) {
                goDetailFragment(productModel)
            }
        },
            object : OnProductListToCollectionsClickHandler {
                override fun addCollections(productModel: Product) {
                    addCollection(productModel)
                }
            },
            object : OnProductListToFavoritesClickHandler {
                override fun addFavorites(productModel: Product) {
                    addFavorite(productModel)
                }
            })
        categoriesBinding?.apply {
            rvShoppingItems.adapter = categoriesResultAdapter
            rvShoppingItems.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun addFavorite(product: Product) {
        val sharedPref = activity?.getSharedPreferences(
            "getSharedPref", Context.MODE_PRIVATE
        )
        val userId = sharedPref?.getString(Constant.SHARED_PREF_KEY, null)
        val favoriteProduct =
            Favorites(
                product.productTitle,
                userId,
                product.productPrice,
                product.productImage
            )
        categoriesViewModel.handleEvent(CategoriesUiEvent.InsertProductToFavorites(favoriteProduct))
        Snackbar.make(requireView(), INSERT_PRODUCT_FAVORITES, Snackbar.LENGTH_LONG).show()
    }

    private fun addCollection(product: Product) {
        val sharedPref = activity?.getSharedPreferences(
            "getSharedPref", Context.MODE_PRIVATE
        )
        val userId = sharedPref?.getString(Constant.SHARED_PREF_KEY, null)
        val collectionProduct =
            Collection(
                product.productTitle,
                userId,
                product.productPrice,
                product.productImage
            )
        categoriesViewModel.handleEvent(
            CategoriesUiEvent.InsertProductToCollections(
                collectionProduct
            )
        )
        Snackbar.make(requireView(), INSERT_PRODUCT_COLLECTIONS, Snackbar.LENGTH_LONG).show()
    }

    fun goDetailFragment(product: Product) {
        val bundle = Bundle().apply {
            putParcelable(Constant.PARCELABLE_ARGS_ID, product)
        }
        findNavController().navigate(
            R.id.action_categoriesFragment_to_detailFragment,
            bundle
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        categoriesBinding = null
    }
}