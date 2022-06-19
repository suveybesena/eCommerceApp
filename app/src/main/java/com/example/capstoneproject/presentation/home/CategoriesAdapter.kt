package com.example.capstoneproject.presentation.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.databinding.ItemCategoryBinding
import com.example.capstoneproject.data.model.Category

class CategoriesAdapter(private val onCategoryListClickHandler: OnCategoryListClickHandler) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesVH>() {
    class CategoriesVH(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesVH {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesVH(binding)
    }

    override fun onBindViewHolder(holder: CategoriesVH, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            ivCategories.setImageResource(list.categoryImage)
        }
        holder.itemView.setOnClickListener {
            onCategoryListClickHandler.goCategoryPage(list.categoryName)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}