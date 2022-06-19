package com.example.capstoneproject.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.data.entities.product.Product
import com.example.capstoneproject.databinding.ItemCampaignsBinding

class CampaignsAdapter(private val onProductListClickHandler: OnProductListClickHandler) :
    RecyclerView.Adapter<CampaignsAdapter.ItemVH>() {
    class ItemVH(val binding: ItemCampaignsBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
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
        val product = differ.currentList[position]
        holder.binding.apply {
            product.productImage?.let { image ->
                ivCampaigns.downloadImage(image)
            }
            //tvCampaign.text = product.productTitle
        }
        holder.itemView.setOnClickListener {
            onProductListClickHandler.goDetailPage(product)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}