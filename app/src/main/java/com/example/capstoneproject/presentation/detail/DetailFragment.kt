package com.example.capstoneproject.presentation.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.capstoneproject.common.extensions.Constant
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.data.model.product.Basket
import com.example.capstoneproject.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var detailBinding: FragmentDetailBinding? = null
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

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
            product.image?.let { ivProduct.downloadImage(it) }
            tvProductName.text = product.title
            tvProductPrice.text = product.price
        }
    }

    private fun initListeners() {
        detailBinding?.apply {
            bvAddCard.setOnClickListener {
                val sharedPref = activity?.getSharedPreferences(
                    "getSharedPref", Context.MODE_PRIVATE
                )
                val pref = sharedPref?.getString(Constant.SHARED_PREF_KEY, null)
                val count = tvItemCount.text.toString()
                val product = args.detail
                val price = tvProductPrice.text.toString()
                val basketItem = Basket(product.title, count, pref, price, product.image)
                detailViewModel.handleEvent(DetailUiEvent.InsertProductToBasket(basketItem))
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        detailBinding = null
    }
}