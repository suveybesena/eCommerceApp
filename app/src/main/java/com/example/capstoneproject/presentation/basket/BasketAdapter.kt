package com.example.capstoneproject.presentation.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.data.model.product.Basket
import com.example.capstoneproject.databinding.ItemShoppingBinding


class BasketAdapter : RecyclerView.Adapter<BasketAdapter.BasketVH>() {
    class BasketVH(val binding: ItemShoppingBinding) : RecyclerView.ViewHolder(binding.root)

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
            ItemShoppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketVH(binding)
    }

    override fun onBindViewHolder(holder: BasketVH, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            list.productImage?.let { image ->
                ivShoppingProduct.downloadImage(image)
            }
            tvItemCount.text = list.productCount
            tvShoppingProductName.text = list.productName
            tvShoppingProductPrice.text = list.productPrice.toString()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}