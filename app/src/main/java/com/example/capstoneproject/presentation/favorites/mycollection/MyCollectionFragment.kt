package com.example.capstoneproject.presentation.favorites.mycollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.databinding.FragmentMyCollectionBinding
import com.example.capstoneproject.domain.model.Item
import com.example.capstoneproject.presentation.home.ItemsAdapter

class MyCollectionFragment : Fragment() {
    private var myCollectionBinding: FragmentMyCollectionBinding? = null
    private lateinit var itemAdapter: CheckThisOutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myCollectionBinding = FragmentMyCollectionBinding.inflate(inflater)
        return myCollectionBinding?.root
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
    }

    private fun initRecyclerView() {
        itemAdapter = CheckThisOutAdapter()
        myCollectionBinding?.apply {
            rvCheckThisOut.adapter = itemAdapter
            rvCheckThisOut.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myCollectionBinding = null
    }
}