package com.example.capstoneproject.presentation.loginregister.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.common.Constant.BLANK_FIELD_ERROR
import com.example.capstoneproject.common.Constant.FAILED_LOGIN
import com.example.capstoneproject.common.Constant.SUCCESS_LOGIN
import com.example.capstoneproject.data.model.AuthModel
import com.example.capstoneproject.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModels()
    private var loginBinding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBinding = FragmentLoginBinding.inflate(inflater)
        return loginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        loginBinding?.apply {
            bvLogin.setOnClickListener {
                val userEmail = etMail.text.toString().trim()
                val userPassword = etPassword.text.toString().trim()
                if (userEmail.isEmpty()) {
                    etMail.error = BLANK_FIELD_ERROR
                } else if (userPassword.isEmpty()) {
                    etPassword.error = BLANK_FIELD_ERROR
                } else {
                    val auth = AuthModel(userEmail, userPassword)
                    loginViewModel.handleEvent(LoginUiEvent.Login(auth))
                    lifecycleScope.launch {
                        loginViewModel._uiState.collect { state ->
                            state.isLoggedIn.let { loggedIn ->
                                if (loggedIn == true) {
                                    Snackbar.make(
                                        requireView(),
                                        SUCCESS_LOGIN,
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    findNavController().navigate(R.id.homeFragment)
                                } else {
                                }
                            }
                        }
                    }
                }
                val sharedPref =
                    activity?.getSharedPreferences(
                        "getSharedPref",
                        Context.MODE_PRIVATE
                    )
                with(sharedPref?.edit()) {
                    this?.putString(Constant.SHARED_PREF_KEY, userEmail)
                    this?.apply()
                }
            }
            bvForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginRegisterFragment_to_forgotFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginBinding = null
    }
}