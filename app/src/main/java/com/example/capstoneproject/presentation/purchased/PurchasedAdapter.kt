package com.example.capstoneproject.presentation.purchased

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.data.model.product.Purchased
import com.example.capstoneproject.databinding.ItemPurchasedBinding


class PurchasedAdapter : RecyclerView.Adapter<PurchasedAdapter.PurchasedVH>() {
    class PurchasedVH(val binding: ItemPurchasedBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Purchased>() {
        override fun areItemsTheSame(
            oldItem: Purchased,
            newItem: Purchased
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: Purchased,
            newItem: Purchased
        ): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchasedVH {
        val binding =
            ItemPurchasedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PurchasedVH(binding)
    }

    override fun onBindViewHolder(holder: PurchasedVH, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            list.productImage?.let { image ->
                ivShoppingProduct.downloadImage(image)
            }
            tvShoppingProductName.text = list.productName
            tvShoppingProductPrice.text = list.productPrice
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}