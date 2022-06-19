package com.example.capstoneproject.presentation.favorites.myfavorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.data.entities.product.Favorites
import com.example.capstoneproject.databinding.ItemMyFavoritesBinding

class MyFavoritesAdapter(private val onFavoritesListToDeleteClickHandler: OnFavoritesListToDeleteClickHandler) :
    RecyclerView.Adapter<MyFavoritesAdapter.FavoritesVH>() {
    class FavoritesVH(val binding: ItemMyFavoritesBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Favorites>() {
        override fun areItemsTheSame(
            oldItem: Favorites,
            newItem: Favorites
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: Favorites,
            newItem: Favorites
        ): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        val binding =
            ItemMyFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesVH(binding)
    }

    override fun onBindViewHolder(holder: FavoritesVH, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            product.productImage?.let { image ->
                ivProduct.downloadImage(image)
            }
            tvProductName.text = product.productName
            tvProductPrice.text = "$${product.productPrice}"

            bvFavItem.setOnClickListener {
                onFavoritesListToDeleteClickHandler.deleteProduct(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}