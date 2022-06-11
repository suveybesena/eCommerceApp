package com.example.capstoneproject.presentation.favorites.myfavorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneproject.databinding.FragmentMyFavoritesBinding
import com.example.capstoneproject.domain.model.Item


class MyFavoritesFragment : Fragment() {
    private var myFavoritesBinding: FragmentMyFavoritesBinding? = null
    lateinit var myFavoritesAdapter: MyFavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myFavoritesBinding = FragmentMyFavoritesBinding.inflate(inflater)
        return myFavoritesBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeData()
    }

    private fun observeData() {
        val item1 = Item(
            "ürün1fghfh",
            "25",
            "https://image.similarpng.com/very-thumbnail/2021/12/Cosmetics-beauty-products-for-makeup-on-transparent-background-PNG.png",
            "ürünfghfghfhfghfhgfgh"
        )

        val item2 = Item(
            "ürün2",
            "25",
            "https://image.similarpng.com/very-thumbnail/2021/12/Cosmetics-beauty-products-for-makeup-on-transparent-background-PNG.png",
            "ürün"
        )

        val item3 = Item(
            "ürün3",
            "25",
            "https://image.similarpng.com/very-thumbnail/2021/12/Cosmetics-beauty-products-for-makeup-on-transparent-background-PNG.png",
            "ürün"
        )

        val item4 = Item(
            "ürün4",
            "25",
            "https://image.similarpng.com/very-thumbnail/2021/12/Collection-of-make-up-products-on-transparent-background-PNG.png",
            "ürün"
        )
        val list = arrayListOf<Item>()
        list.add(item1)
        list.add(item2)
        list.add(item3)
        list.add(item4)

        myFavoritesAdapter.differ.submitList(list)
    }

    private fun initRecyclerView() {
        myFavoritesAdapter = MyFavoritesAdapter()
        myFavoritesBinding?.apply {
            rvMyFavorites.adapter = myFavoritesAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myFavoritesBinding = null
    }
}