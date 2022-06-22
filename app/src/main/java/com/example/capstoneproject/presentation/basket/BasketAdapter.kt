package com.example.capstoneproject.presentation.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.data.entities.product.Basket
import com.example.capstoneproject.databinding.ItemBasketBinding


class BasketAdapter(
    private val onBasketListToDeleteClickHandler: OnBasketListToDeleteClickHandler
) :
    RecyclerView.Adapter<BasketAdapter.BasketVH>() {
    class BasketVH(val binding: ItemBasketBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Basket>() {
        override fun areItemsTheSame(
            oldItem: Basket,
            newItem: Basket
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: Basket,
            newItem: Basket
        ): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketVH {
        val binding =
            ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketVH(binding)
    }

    override fun onBindViewHolder(holder: BasketVH, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            product.productImage?.let { image ->
                ivShoppingProduct.downloadImage(image)
            }
            tvItemCount.text = product.productCount
            tvShoppingProductName.text = product.productName
            tvShoppingProductPrice.text = "$${product.productPrice}"

            bvDelete.setOnClickListener {
                onBasketListToDeleteClickHandler.deleteItem(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}