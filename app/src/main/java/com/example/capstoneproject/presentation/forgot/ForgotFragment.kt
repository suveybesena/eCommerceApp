package com.example.capstoneproject.presentation.forgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.capstoneproject.common.Constant.MAIL_SEND
import com.example.capstoneproject.databinding.FragmentForgotBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : Fragment() {
    private var forgotBinding: FragmentForgotBinding? = null
    private val forgotViewModel: ForgotViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        forgotBinding = FragmentForgotBinding.inflate(inflater)
        return forgotBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        forgotBinding?.bvReset?.setOnClickListener {
            val email = forgotBinding?.etMail?.text.toString()
            Snackbar.make(requireView(), MAIL_SEND, Snackbar.LENGTH_LONG).show()
            forgotViewModel.handleEvent(ForgotUiEvent.ResetPassword(email))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        forgotBinding = null
    }
}