package com.example.capstoneproject.presentation.favorites.myfavorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.databinding.ItemMyFavoritesBinding
import com.example.capstoneproject.domain.model.Item

class MyFavoritesAdapter : RecyclerView.Adapter<MyFavoritesAdapter.FavoritesVH>() {
    class FavoritesVH(val binding: ItemMyFavoritesBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(
            oldItem: Item,
            newItem: Item
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: Item,
            newItem: Item
        ): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        val binding = ItemMyFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesVH(binding)
    }

    override fun onBindViewHolder(holder: FavoritesVH, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            list.productImage.let { image ->
                ivProduct.downloadImage(image)
            }
            tvProductDesc.text = list.productDesc
            tvProductName.text = list.productName
            tvProductPrice.text = list.productPrice
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}