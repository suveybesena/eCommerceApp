package com.example.capstoneproject.presentation.loginregister.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneproject.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var registerBinding: FragmentRegisterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerBinding = FragmentRegisterBinding.inflate(inflater)
        return registerBinding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        registerBinding = null
    }
}