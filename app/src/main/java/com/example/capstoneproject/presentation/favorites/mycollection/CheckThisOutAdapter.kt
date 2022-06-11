package com.example.capstoneproject.presentation.favorites.mycollection

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.databinding.ItemCheckThisOutBinding
import com.example.capstoneproject.domain.model.Item


class CheckThisOutAdapter : RecyclerView.Adapter<CheckThisOutAdapter.ItemVH>() {
    class ItemVH(val binding: ItemCheckThisOutBinding) : RecyclerView.ViewHolder(binding.root)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val binding =
            ItemCheckThisOutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVH(binding)
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
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
