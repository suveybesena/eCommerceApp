package com.example.capstoneproject.presentation.map

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var binding: FragmentMapBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener { latLng ->
            mapClickListener(latLng)
        }
    }

    private fun mapClickListener(location: LatLng) {
        mMap.clear()
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var address = ""
        val adressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        address = adressList.get(0).getAddressLine(0)
        binding?.apply {
            twLocation.text = address
            bwCheck.setOnClickListener {
                val sharedPref =
                    activity?.getSharedPreferences(
                        "getSharedPref",
                        Context.MODE_PRIVATE
                    )
                with(sharedPref?.edit()) {
                    this?.putString(Constant.MAP_SHARED_PREF_KEY, address)
                    this?.apply()
                }
                findNavController().navigate(R.id.action_mapFragment_to_basketFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}