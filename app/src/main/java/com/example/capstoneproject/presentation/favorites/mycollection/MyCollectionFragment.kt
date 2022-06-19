package com.example.capstoneproject.presentation.favorites.mycollection

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.data.entities.product.Product
import com.example.capstoneproject.databinding.FragmentMyCollectionBinding
import com.example.capstoneproject.presentation.home.OnProductListClickHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyCollectionFragment : Fragment() {
    private var myCollectionBinding: FragmentMyCollectionBinding? = null
    private lateinit var productAdapter: CheckThisOutAdapter
    private lateinit var collectionsAdapter: MyCollectionsAdapter
    private val myCollectionViewModel: MyCollectionViewModel by viewModels()

    @Inject
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myCollectionBinding = FragmentMyCollectionBinding.inflate(inflater)
        return myCollectionBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserve()
    }

    private fun initObserve() {
        val new = "New"
        myCollectionViewModel.handleEvent(MyCollectionUiEvent.GetNewProducts(new))
        myCollectionViewModel.handleEvent(MyCollectionUiEvent.GetAllCollections(userId))
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myCollectionViewModel._uiState.collect { state ->
                    state.collectionProduct.let { flow ->
                        flow?.collect { collection ->
                            if (collection.isNotEmpty()) {
                                myCollectionBinding?.apply {
                                    ivEmptyImage.visibility = View.GONE
                                    tvEmpty.visibility = View.GONE
                                    rvMyCollections.visibility = View.VISIBLE
                                    collectionsAdapter.differ.submitList(collection)
                                }
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myCollectionViewModel._uiState.collect { state ->
                    state.newProducts.let { newProducts ->
                        productAdapter.differ.submitList(newProducts)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        productAdapter = CheckThisOutAdapter(object : OnProductListClickHandler {
            override fun goDetailPage(productModel: Product) {
                goDetail(productModel)
            }

        })
        collectionsAdapter = MyCollectionsAdapter(object : OnCollectionsListToDeleteClickHandler {
            override fun deleteProduct(collection: Collection) {
                deleteCollection(collection)
            }
        })
        myCollectionBinding?.apply {
            rvCheckThisOut.adapter = productAdapter
            rvCheckThisOut.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvMyCollections.adapter = collectionsAdapter
        }
    }

    private fun goDetail(productModel: Product) {
        val bundle = Bundle().apply {
            putParcelable(Constant.PARCELABLE_ARGS_ID, productModel)
        }
        findNavController().navigate(
            R.id.detailFragment,
            bundle
        )
    }

    private fun deleteCollection(collection: Collection) {
        myCollectionViewModel.handleEvent(MyCollectionUiEvent.DeleteProduct(collection))
    }

    override fun onDestroy() {
        super.onDestroy()
        myCollectionBinding = null
    }
}