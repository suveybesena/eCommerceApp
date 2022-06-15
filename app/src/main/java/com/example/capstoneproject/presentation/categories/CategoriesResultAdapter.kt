package com.example.capstoneproject.presentation.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.common.extensions.downloadImage
import com.example.capstoneproject.data.entities.product.Product
import com.example.capstoneproject.databinding.ItemProductBinding
import com.example.capstoneproject.presentation.home.OnProductListClickHandler
import com.example.capstoneproject.presentation.home.OnProductListToCollectionsClickHandler
import com.example.capstoneproject.presentation.home.OnProductListToFavoritesClickHandler


class CategoriesResultAdapter(
    private val onProductListClickHandler: OnProductListClickHandler,
    private val onProductListToCollectionsClickHandler: OnProductListToCollectionsClickHandler,
    private val onProductListToFavoritesClickHandler: OnProductListToFavoritesClickHandler
) :
    RecyclerView.Adapter<CategoriesResultAdapter.ItemVH>() {
    class ItemVH(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

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

        val product = differ.currentList[position]
        holder.binding.apply {
            product.productImage.let { image ->
                if (image != null) {
                    ivProduct.downloadImage(image)
                }
            }
            tvProductDesc.text = product.productDescription
            tvProductName.text = product.productTitle
            tvProductPrice.text = product.productPrice
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

            bvCollectionItem.setOnClickListener {
                onProductListToCollectionsClickHandler.addCollections(product)
            }
            bvFavItem.setOnClickListener {
                onProductListToFavoritesClickHandler.addFavorites(product)
            }

            holder.itemView.setOnClickListener {
                onProductListClickHandler.goDetailPage(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}