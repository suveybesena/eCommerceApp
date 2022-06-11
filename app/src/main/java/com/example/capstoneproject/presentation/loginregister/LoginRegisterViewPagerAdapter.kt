package com.example.capstoneproject.presentation.loginregister

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.capstoneproject.presentation.loginregister.login.LoginFragment
import com.example.capstoneproject.presentation.loginregister.register.RegisterFragment


class LoginRegisterViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RegisterFragment()
            1 -> LoginFragment()
            else -> {
                RegisterFragment()
            }
        }
    }
}