package com.example.capstoneproject.presentation.loginregister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneproject.databinding.FragmentLoginRegisterBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginRegisterFragment : Fragment() {

    private var binding: FragmentLoginRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginRegisterBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = LoginRegisterViewPagerAdapter(this)

        binding?.let {
            it.vpLoginRegister.adapter = adapter
            TabLayoutMediator(
                it.tabs, it.vpLoginRegister
            ) { tab, position ->
                if (position == 0) {
                    tab.text = "Register"
                } else {
                    tab.text = "Login"
                }
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}