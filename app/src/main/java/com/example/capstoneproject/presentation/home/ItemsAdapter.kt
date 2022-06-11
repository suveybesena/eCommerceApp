package com.example.capstoneproject.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.databinding.ItemProductBinding
import com.example.capstoneproject.domain.model.Item

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemVH>() {
    class ItemVH(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

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
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVH(binding)
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val rotateOpen: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.rotate_open_anim
            )
        }
        val rotateClose: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.rotate_close_anim
            )
        }
        val fromBottom: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.from_bottom_anim
            )
        }
        val toBottom: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.to_bottom_anim
            )
        }

        val list = differ.currentList[position]
        holder.binding.apply {
            list.productImage.let { image ->
                ivProduct.downloadImage(image)
            }
            tvProductDesc.text = list.productDesc
            tvProductName.text = list.productName
            tvProductPrice.text = list.productPrice
            val clicked = false
            bvAdd.setOnClickListener {
                if (!clicked) {
                    bvAdd.startAnimation(rotateOpen)
                    bvFavItem.startAnimation(fromBottom)
                    bvCollectionItem.startAnimation(fromBottom)
                } else {
                    bvAdd.startAnimation(rotateClose)
                    bvFavItem.startAnimation(toBottom)
                    bvCollectionItem.startAnimation(toBottom)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}