package com.example.capstoneproject.presentation.loginregister.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.capstoneproject.databinding.FragmentLoginBinding
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
        initObserve()
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel._uiState.collect { state ->
                    state.users.let { userList ->
                           // println(userList)
                    }
                }
            }
        }
    }

    private fun initListeners() {
        loginBinding?.apply {
            bvLogin.setOnClickListener {
                val userName = etUserName.text.toString()
                val userPassword = etPassword.text.toString()
                loginViewModel.handleEvent(LoginUiEvent.SignIn(userName, userPassword))
                lifecycleScope.launch {
                    loginViewModel._uiState.collect { state ->
                        state.token.let {
                            //println(it?.keys)
                        }
                    }
                }
                loginViewModel.handleEvent(LoginUiEvent.GetAllUsers)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginBinding = null
    }
}