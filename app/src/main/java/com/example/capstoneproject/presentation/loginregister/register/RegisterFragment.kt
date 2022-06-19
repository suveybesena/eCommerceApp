package com.example.capstoneproject.presentation.loginregister.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.common.Constant.SUCCESS_REGISTER
import com.example.capstoneproject.data.entities.user.User
import com.example.capstoneproject.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

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
    }

    private fun initListeners() {
        registerBinding?.apply {
            bvRegister.setOnClickListener {
                val userPhone = etUserPhone.text.toString()
                val userAddress = etUserAddress.text.toString()
                val userEmail = etEmailAddress.text.toString()
                val userPassword = etPassword.text.toString()
                val name = etPersonName.text.toString()
                val uuid = UUID.randomUUID().toString()
                val sharedPref =
                    activity?.getSharedPreferences(
                        "getSharedPref",
                        Context.MODE_PRIVATE
                    )
                with(sharedPref?.edit()) {
                    this?.putString(Constant.SHARED_PREF_KEY, uuid)
                    this?.apply()
                }
                val user = User(name, userEmail, userPassword, userPhone, userAddress, uuid)
                registerViewModel.handleEvent(
                    RegisterUiEvent.SignUp(
                        userEmail,
                        userPassword,
                        name,
                        userAddress,
                        userPhone
                    )
                )
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        registerViewModel._uiState.collect { state ->
                            state.response.let { crudResponse ->
                                if (crudResponse?.status == 0) {
                                    Snackbar.make(
                                        requireView(),
                                        crudResponse.error.toString(),
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                } else {
                                    registerViewModel.handleEvent(
                                        RegisterUiEvent.InsertUserToDb(
                                            user
                                        )
                                    )
                                    state.dbError.let { dbError ->
                                        if (dbError.isNullOrEmpty()) {
                                            Snackbar.make(
                                                requireView(),
                                                SUCCESS_REGISTER,
                                                Snackbar.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        registerBinding = null
    }
}