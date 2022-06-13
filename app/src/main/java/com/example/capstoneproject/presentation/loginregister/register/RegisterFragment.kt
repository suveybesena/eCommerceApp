package com.example.capstoneproject.presentation.loginregister.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.data.model.user.*
import com.example.capstoneproject.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var registerBinding: FragmentRegisterBinding? = null
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerBinding = FragmentRegisterBinding.inflate(inflater)
        return registerBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel._uiState.collect { state ->
                    state.error.let { error ->
                        Snackbar.make(requireView(), error.toString(), Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun initListeners() {
        registerBinding?.apply {
            bvRegister.setOnClickListener {
                val userName = etPersonName.text.toString()
                val userUserName = etUserName.text.toString()
                val userLastName = etLastName.text.toString()
                val userPhone = etPhone.text.toString()
                val userEmail = etEmailAddress.text.toString()
                val userPassword = etPassword.text.toString()
                val userItem = UserItem(
                    Address(geolocation = Geolocation()),
                    userEmail,
                    Name(userName, userLastName),
                    userPassword,
                    userPhone,
                    userUserName
                )
                val user = User(userUserName, userEmail, userPassword, userPhone)
                try {
                    registerViewModel.handleEvent(RegisterUiEvent.SignUp(userItem))
                    registerViewModel.handleEvent(RegisterUiEvent.InsertUserToDb(user))
                    findNavController().navigate(R.id.action_loginRegisterFragment_to_homeFragment)
                } catch (e: Exception) {
                    Snackbar.make(requireView(), e.localizedMessage, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        registerBinding = null
    }
}