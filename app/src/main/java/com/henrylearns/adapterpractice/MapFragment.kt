package com.henrylearns.adapterpractice


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions





class MapFragment : Fragment(),OnMapReadyCallback {
    override fun onMapReady(map: GoogleMap?) {
        map?.addMarker(MarkerOptions().position(LatLng(0.0, 0.0)).title("Stauffer"))
    }

    private val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    lateinit var mMapView: MapView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myView = inflater.inflate(R.layout.fragment_map, container, false)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        val test=myView.findViewById<TextView>(R.id.jjj)
        val myMap:MapView=myView.findViewById<MapView>(R.id.mapview)
        mMapView = myMap
        mMapView.onCreate(mapViewBundle)

        mMapView.getMapAsync(this)
    return myView
    }


    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mMapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }


    override fun onPause() {
        mMapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mMapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }
}