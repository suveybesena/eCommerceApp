package com.example.capstoneproject.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.domain.model.Category
import com.example.capstoneproject.domain.model.Item
import com.mig35.carousellayoutmanager.CarouselLayoutManager
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.mig35.carousellayoutmanager.CenterScrollListener


class HomeFragment : Fragment() {
    private var homeBinding: FragmentHomeBinding? = null
    lateinit var itemAdapter: ItemsAdapter
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var campaignsAdapter: CampaignsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater)
        return homeBinding?.root
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

        itemAdapter.differ.submitList(list)
        campaignsAdapter.differ.submitList(list)

        val categoriesItem1 = Category(
            "a",
            "https://cdn.shopify.com/s/files/1/1338/0845/collections/lippie-pencil_grande.jpg?v=1512588769"
        )
        val categoriesItem2 = Category(
            "´b",
            "https://theaoi.com/wp-content/uploads/2020/01/ayshatengiz_aspotoflonliness1-846x1200.jpg"
        )
        val categoriesItem3 = Category(
            "´c",
            "https://theaoi.com/wp-content/uploads/2020/02/Franc%CC%A7ois-Truffaut_Victria_Semykina_image04-849x1200.jpg"
        )
        val categoriesItem4 = Category(
            "´d",
            "https://theaoi.com/wp-content/uploads/2020/02/Franc%CC%A7ois-Truffaut_Victria_Semykina_image04-849x1200.jpg"
        )

        val list2 = arrayListOf<Category>()
        list2.add(categoriesItem1)
        list2.add(categoriesItem2)
        list2.add(categoriesItem3)
        list2.add(categoriesItem4)

        categoriesAdapter.differ.submitList(list2)
    }

    private fun initRecyclerView() {
        itemAdapter = ItemsAdapter()
        homeBinding?.apply {
            rvItem.adapter = itemAdapter
            rvItem.layoutManager = GridLayoutManager(requireContext(), 2)
        }

        val layoutManagerHorizontal = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        layoutManagerHorizontal.setPostLayoutListener(CarouselZoomPostLayoutListener())
        categoriesAdapter = CategoriesAdapter()
        homeBinding?.rvCategories?.adapter = categoriesAdapter
        homeBinding?.rvCategories?.layoutManager = layoutManagerHorizontal
        homeBinding?.rvCategories?.setHasFixedSize(true)
        homeBinding?.rvCategories?.addOnScrollListener(CenterScrollListener())

        campaignsAdapter = CampaignsAdapter()
        homeBinding?.apply {
            rvCampaigns.adapter = campaignsAdapter
            rvCampaigns.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }
}