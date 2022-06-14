package com.example.capstoneproject.presentation.categories

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
import com.example.capstoneproject.data.model.product.Product
import com.example.capstoneproject.databinding.FragmentCategoriesBinding
import com.example.capstoneproject.presentation.home.OnProductListClickHandler
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
                    state.products.let { product ->
                        categoriesResultAdapter.differ.submitList(product)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        categoriesResultAdapter = CategoriesResultAdapter(object : OnProductListClickHandler {
            override fun goDetailPage(product: Product) {
                goDetailFragment(product)
            }

        })
        categoriesBinding?.apply {
            rvShoppingItems.adapter = categoriesResultAdapter
            rvShoppingItems.layoutManager = GridLayoutManager(requireContext(), 2)
        }
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