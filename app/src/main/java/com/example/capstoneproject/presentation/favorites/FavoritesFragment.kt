package com.example.capstoneproject.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneproject.databinding.FragmentFavoritesBinding
import com.google.android.material.tabs.TabLayoutMediator


class FavoritesFragment : Fragment() {

    private var favoritesBinding: FragmentFavoritesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoritesBinding = FragmentFavoritesBinding.inflate(inflater)
        return favoritesBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoritesViewPagerAdapter(this)

        favoritesBinding?.let {
            it.vpProductFeed.adapter = adapter
            TabLayoutMediator(
                it.tabs, it.vpProductFeed
            ) { tab, position ->
                if (position == 0) {
                    tab.text = "My Favorites"
                } else {
                    tab.text = "My Collections"
                }
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favoritesBinding = null
    }
}