package com.henrylearns.adapterpractice.map


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.henrylearns.adapterpractice.R



class googleMapFragment : Fragment(),OnMapReadyCallback {
    override fun onMapReady(map: GoogleMap?) {
        val myLoc=LatLng(45.00,23.123541)
        map?.addMarker((MarkerOptions().position(myLoc)))
        map?.moveCamera(CameraUpdateFactory.newLatLng(myLoc))


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mapView = inflater.inflate(R.layout.fragment_map, container, false)
        return mapView


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* var mapFragment=childFragmentManager.findFragmentById(R.id.MapNo1)
        mapFragment=mapFragment as SupportMapFragment
        mapFragment.getMapAsync(this)*/
    }
}


