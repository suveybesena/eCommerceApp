package com.example.capstoneproject.presentation.home

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.common.extensions.Constant
import com.example.capstoneproject.common.extensions.Constant.SHARED_PREF_KEY
import com.example.capstoneproject.data.model.product.Favorites
import com.example.capstoneproject.data.model.product.Product
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.domain.model.Item
import com.mig35.carousellayoutmanager.CarouselLayoutManager
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.mig35.carousellayoutmanager.CenterScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var homeBinding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var productsAdapter: ProductsAdapter
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var campaignsAdapter: CampaignsAdapter

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
        // initListeners()
    }

    private fun initListeners() {
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

    private fun goDetailFragment(product: Product) {
        val bundle = Bundle().apply {
            putParcelable(Constant.PARCELABLE_ARGS_ID, product)
        }
        findNavController().navigate(
            R.id.action_homeFragment_to_detailFragment,
            bundle
        )
    }

    private fun addFavoritesToDb(product: Product) {
        val sharedPref = activity?.getSharedPreferences(
            "getSharedPref", Context.MODE_PRIVATE
        )
        val pref = sharedPref?.getString(SHARED_PREF_KEY, null)
        val favoriteProduct = Favorites(product.title, pref, product.price, product.image)
        homeViewModel.handleEvent(HomeUiEvent.InsertProductToFavorite(favoriteProduct))
    }

    private fun initObserve() {
        homeViewModel.handleEvent(HomeUiEvent.GetAllCategories)
        homeViewModel.handleEvent(HomeUiEvent.GetAllProducts)
        homeViewModel.handleEvent(HomeUiEvent.GetLastUser)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel._uiState.collect { state ->
                    state.categories.let { categories ->
                        categoriesAdapter.differ.submitList(categories)
                    }
                    state.products.let { product ->
                        productsAdapter.differ.submitList(product)
                    }
                    state.currentUser?.let { flowList ->
                        flowList.collect { userList ->
                            val currentUserId = userList.lastIndex.toString()
                            val sharedPref =
                                activity?.getSharedPreferences(
                                    "getSharedPref",
                                    Context.MODE_PRIVATE
                                )
                            with(sharedPref?.edit()) {
                                this?.putString(SHARED_PREF_KEY, currentUserId)
                                this?.apply()
                            }
                        }
                    }
                }
            }
        }


        val item1 = Item(
            "ürün1fghfh",
            "25",
            "https://image.similarpng.com/very-thumbnail/2021/12/Cosmetics-beauty-products-for-makeup-on-transparent-background-PNG.png",
            "ürünfghfghfhfghfhgfgh"
        )

        val item2 = Item(
            "ürün2",
            "25",
            "https://image.similarpng.com/very-thumbnail/2021/12/Cosmetics-beauty-products-for-makeup-on-transparent-background-PNG.png",
            "ürün"
        )

        val item3 = Item(
            "ürün3",
            "25",
            "https://image.similarpng.com/very-thumbnail/2021/12/Cosmetics-beauty-products-for-makeup-on-transparent-background-PNG.png",
            "ürün"
        )

        val item4 = Item(
            "ürün4",
            "25",
            "https://image.similarpng.com/very-thumbnail/2021/12/Collection-of-make-up-products-on-transparent-background-PNG.png",
            "ürün"
        )

        val list = arrayListOf<Item>()
        list.add(item1)
        list.add(item2)
        list.add(item3)
        list.add(item4)

        campaignsAdapter.differ.submitList(list)
    }

    private fun initRecyclerView() {
        productsAdapter = ProductsAdapter(object : OnProductListClickHandler {
            override fun goDetailPage(product: Product) {
                goDetailFragment(product)
            }

        }, object : OnProductListToFavoritesClickHandler {
            override fun addFavorites(product: Product) {
                addFavoritesToDb(product)
            }

        })
        homeBinding?.apply {
            rvItem.adapter = productsAdapter
            rvItem.layoutManager = GridLayoutManager(requireContext(), 2)
        }

        val layoutManagerHorizontal = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        layoutManagerHorizontal.setPostLayoutListener(CarouselZoomPostLayoutListener())
        categoriesAdapter = CategoriesAdapter(object : OnCategoryListClickHandler {
            override fun goCategoryPage(categoryName: String) {
                goCategoriesFragment(categoryName)
            }

        })
        homeBinding?.rvCategories?.adapter = categoriesAdapter
        homeBinding?.rvCategories?.layoutManager = layoutManagerHorizontal
        homeBinding?.rvCategories?.setHasFixedSize(true)
        homeBinding?.rvCategories?.addOnScrollListener(CenterScrollListener())

        campaignsAdapter = CampaignsAdapter()
        homeBinding?.apply {
            rvCampaigns.adapter = campaignsAdapter
            rvCampaigns.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }
}