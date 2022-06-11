package com.example.capstoneproject.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.databinding.ItemCampaignsBinding
import com.example.capstoneproject.domain.model.Item

class CampaignsAdapter : RecyclerView.Adapter<CampaignsAdapter.ItemVH>() {
    class ItemVH(val binding: ItemCampaignsBinding) : RecyclerView.ViewHolder(binding.root)

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
            ItemCampaignsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVH(binding)
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            list.productImage.let { image ->
                ivCampaigns.downloadImage(image)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}