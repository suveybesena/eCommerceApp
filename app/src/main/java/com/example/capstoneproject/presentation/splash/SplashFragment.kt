package com.example.capstoneproject.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var splashBinding: FragmentSplashBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        splashBinding = FragmentSplashBinding.inflate(inflater)
        return splashBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(R.id.loginRegisterFragment)
        }, 6000)
    }

    override fun onDestroy() {
        super.onDestroy()
        splashBinding = null
    }
}