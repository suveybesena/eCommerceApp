package com.example.capstoneproject.presentation.profile

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
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var profileBinding: FragmentProfileBinding? = null
    private val profileViewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding = FragmentProfileBinding.inflate(inflater)
        return profileBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initListeners()
    }

    private fun initObserve() {

        profileViewModel.handleEvent(ProfileUiEvent.GetCurrentUserId(userId))
        profileViewModel.handleEvent(ProfileUiEvent.GetCollectionItems(userId))
        profileViewModel.handleEvent(ProfileUiEvent.GetPurchasedItems(userId))
        profileViewModel.handleEvent(ProfileUiEvent.GetFavoritesItems(userId))
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel._uiState.collect { state ->
                    state.user?.let { user ->
                        profileBinding?.apply {
                            tvUserName.text = user.userName
                            tvUserMail.text = user.userMail
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel._uiState.collect { state ->
                    state.collectionsCount?.let { collections ->
                        profileBinding?.apply {
                            tvCollectionsCount.text = collections.toString()
                        }
                    }
                    state.favoritesCount?.let { favorites ->
                        profileBinding?.apply {
                            tvFavItemsCount.text = favorites.toString()
                        }
                    }
                    state.purchasedCount?.let { purchased ->
                        profileBinding?.apply {
                            tvPurchasedItemsCount.text = purchased.toString()
                        }
                    }
                }
            }
        }
    }

    private fun initListeners() {
        profileBinding?.apply {
            layoutCollections.setOnClickListener {
                findNavController().navigate(R.id.favoritesFragment)
            }
            layoutFavorites.setOnClickListener {
                findNavController().navigate(R.id.favoritesFragment)
            }
            layoutPurchased.setOnClickListener {
                findNavController().navigate(R.id.purchasedFragment)
            }
            bvCollectionItem.setOnClickListener {
                val sharedPref =
                    activity?.getSharedPreferences(
                        "getSharedPref",
                        Context.MODE_PRIVATE
                    )
                with(sharedPref?.edit()) {
                    this?.remove(Constant.SHARED_PREF_KEY)
                    this?.commit()
                }
                findNavController().navigate(R.id.loginRegisterFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        profileBinding = null
    }
}