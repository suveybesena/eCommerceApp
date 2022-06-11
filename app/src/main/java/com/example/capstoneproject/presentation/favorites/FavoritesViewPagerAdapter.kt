package com.example.capstoneproject.presentation.favorites

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.capstoneproject.presentation.favorites.mycollection.MyCollectionFragment
import com.example.capstoneproject.presentation.favorites.myfavorites.MyFavoritesFragment


class FavoritesViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyFavoritesFragment()
            1 -> MyCollectionFragment()
            else -> {
                MyFavoritesFragment()
            }
        }
    }
}