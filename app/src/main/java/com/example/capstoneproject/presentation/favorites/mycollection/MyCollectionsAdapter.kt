package com.example.capstoneproject.presentation.favorites.mycollection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.databinding.ItemMyCollectionsBinding


class MyCollectionsAdapter(private val onCollectionsListToDeleteClickHandler: OnCollectionsListToDeleteClickHandler) :
    RecyclerView.Adapter<MyCollectionsAdapter.CollectionsVH>() {
    class CollectionsVH(val binding: ItemMyCollectionsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Collection>() {
        override fun areItemsTheSame(
            oldItem: Collection,
            newItem: Collection
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: Collection,
            newItem: Collection
        ): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsVH {
        val binding =
            ItemMyCollectionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionsVH(binding)
    }

    override fun onBindViewHolder(holder: CollectionsVH, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            product.productImage?.let { image ->
                ivProduct.downloadImage(image)
            }
            tvProductName.text = product.productName
            tvProductPrice.text = "$${product.productPrice}"

            bvCollectionItem.setOnClickListener {
                onCollectionsListToDeleteClickHandler.deleteProduct(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}