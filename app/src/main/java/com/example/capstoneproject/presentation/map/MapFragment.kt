package com.example.capstoneproject.presentation.map

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
        try {
            val adressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (adressList.get(0).subAdminArea != null) {
                address += adressList.get(0).subAdminArea
                if (adressList.get(0).adminArea != null) {
                    address += adressList.get(0).adminArea
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding?.apply {
            twLocation.text = address
            println(address)
            bwCheck.setOnClickListener {
                val bundle = Bundle().apply {
                    putString(Constant.PARCELABLE_ARGS_ID, address)
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}