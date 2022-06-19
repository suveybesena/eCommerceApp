package com.example.capstoneproject.presentation.home

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.data.entities.product.Favorites
import com.example.capstoneproject.data.entities.product.Product
import com.example.capstoneproject.data.model.Category
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment() : Fragment() {
    private var homeBinding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var productsAdapter: ProductsAdapter
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var campaignsAdapter: CampaignsAdapter
    lateinit var searchAdapter: SearchAdapter

    @Inject
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater)
        return homeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserve()
        initListeners()
    }

    private fun initListeners() {
        homeBinding?.apply {
            //etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //    override fun onQueryTextSubmit(query: String?): Boolean {
            //        searchAdapter.filter.filter(query)
            //        return true
            //    }

            //    override fun onQueryTextChange(newText: String?): Boolean {
            //        homeBinding?.apply {
            //            rvSearch.visibility = View.VISIBLE
            //            layout.visibility = View.INVISIBLE
            //        }
            //        searchAdapter.filter.filter(newText)
            //        return true
            //    }
            //})
        }
    }

    private fun goCategoriesFragment(categoryName: String) {
        val bundle = Bundle().apply {
            putString(Constant.STRING_ARGS_ID, categoryName)
        }
        findNavController().navigate(
            R.id.action_homeFragment_to_categoriesFragment,
            bundle
        )
    }

    private fun goDetailFragment(productModel: Product) {
        val bundle = Bundle().apply {
            putParcelable(Constant.PARCELABLE_ARGS_ID, productModel)
        }
        findNavController().navigate(
            R.id.action_homeFragment_to_detailFragment,
            bundle
        )
    }

    private fun addFavoritesToDb(productModel: Product) {
        val favoriteProduct =
            Favorites(
                productModel.productTitle,
                userId,
                productModel.productPrice,
                productModel.productImage
            )
        homeViewModel.handleEvent(HomeUiEvent.InsertProductToFavorite(favoriteProduct))
        Snackbar.make(requireView(), Constant.INSERT_PRODUCT_FAVORITES, Snackbar.LENGTH_LONG).show()
    }

    private fun addCollectionsToDb(productModel: Product) {
        val collectionProduct =
            Collection(
                productModel.productTitle,
                userId,
                productModel.productPrice,
                productModel.productImage
            )
        homeViewModel.handleEvent(HomeUiEvent.InsertProductToCollections(collectionProduct))
        Snackbar.make(requireView(), Constant.INSERT_PRODUCT_COLLECTIONS, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun initObserve() {
        val discount = "Discount"
        homeViewModel.handleEvent(HomeUiEvent.GetDiscountProducts(discount))
        homeViewModel.handleEvent(HomeUiEvent.GetAllProducts)
        homeViewModel.handleEvent(HomeUiEvent.GetBasketItemsCount(userId))
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel._uiState.collect { state ->
                    state.discountProducts.let { newProducts ->
                        campaignsAdapter.differ.submitList(newProducts)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel._uiState.collect { state ->
                    state.allProducts.let { allProducts ->
                        productsAdapter.differ.submitList(allProducts)
                        searchAdapter.differ.submitList(allProducts)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel._uiState.collect { state ->
                    state.basketItemsCount.let { count ->
                        homeBinding?.apply {
                            tvBasketItemCount.text = count.toString()
                        }
                    }
                }
            }
        }

        val categoryList = arrayListOf<Category>()
        val electronic = Category(
            "Electronic",
            R.drawable.electronic
        )
        val blues = Category(
            "Blues",
            R.drawable.blues
        )
        val pop = Category(
            "Pop",
            R.drawable.pop
        )
        val classical = Category(
            "Classical",
            R.drawable.classical
        )
        val funk = Category(
            "Funk",
            R.drawable.funk
        )
        val rock = Category(
            "Rock",
            R.drawable.rock
        )
        val jazz = Category(
            "Jazz",
            R.drawable.jazz
        )

        categoryList.add(electronic)
        categoryList.add(pop)
        categoryList.add(funk)
        categoryList.add(classical)
        categoryList.add(rock)
        categoryList.add(blues)
        categoryList.add(jazz)
        categoriesAdapter.differ.submitList(categoryList)
    }

    private fun initRecyclerView() {
        productsAdapter = ProductsAdapter(object : OnProductListClickHandler {
            override fun goDetailPage(productModel: Product) {
                goDetailFragment(productModel)
            }

        }, object : OnProductListToFavoritesClickHandler {
            override fun addFavorites(productModel: Product) {
                addFavoritesToDb(productModel)
            }

        }, object : OnProductListToCollectionsClickHandler {
            override fun addCollections(productModel: Product) {
                addCollectionsToDb(productModel)
            }

        })
        homeBinding?.apply {
            rvItem.adapter = productsAdapter
            rvItem.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        categoriesAdapter = CategoriesAdapter(object : OnCategoryListClickHandler {
            override fun goCategoryPage(categoryName: String) {
                goCategoriesFragment(categoryName)
            }
        })
        homeBinding?.rvCategories?.apply {
            adapter = categoriesAdapter
            set3DItem(true)
            setAlpha(true)
            setPadding(10, 0, 10, 0)
        }

        campaignsAdapter = CampaignsAdapter(object : OnProductListClickHandler {
            override fun goDetailPage(productModel: Product) {
                goDetailFragment(productModel)
            }
        })
        homeBinding?.apply {
            rvCampaigns.adapter = campaignsAdapter
            rvCampaigns.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        searchAdapter = SearchAdapter(object : OnProductListClickHandler {
            override fun goDetailPage(productModel: Product) {
                goDetailFragment(productModel)
            }
        }, object : OnProductListToFavoritesClickHandler {
            override fun addFavorites(productModel: Product) {
                addFavoritesToDb(productModel)
            }
        }, object : OnProductListToCollectionsClickHandler {
            override fun addCollections(productModel: Product) {
                addCollectionsToDb(productModel)
            }

        })
        homeBinding?.apply {
            rvSearch.adapter = searchAdapter
            rvSearch.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }
}