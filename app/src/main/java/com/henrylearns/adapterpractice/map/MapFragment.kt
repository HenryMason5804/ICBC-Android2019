package com.henrylearns.adapterpractice.map


import android.content.Context
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullEventObject
import com.henrylearns.adapterpractice.dataobjects.locationObject
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.fragment_map.view.*


class MapFragment (): Fragment(),OnMapReadyCallback {

    var radiobuttonIn:RadioButton?=null

    var slidePanel:SlidingUpPanelLayout?=null




    var something =HashMap<String,Marker?>()
    var mMap:GoogleMap?=null
    override fun onMapReady(map: GoogleMap?) {
        FirebaseFirestore.getInstance()
        val myColRef=FirebaseFirestore.getInstance().collection("Locations")
        myColRef.addSnapshotListener{snapshot,error->
            if (error !=null){
                Log.d("SnapshotFailure","$error")
            }
            if (snapshot!=null) {
                for (document in snapshot) {
                    val locObject = document.toObject(locationObject::class.java)
                    something.put(
                        locObject.locationName,
                        map?.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    locObject.latitude.toDouble(),
                                    locObject.longitude.toDouble()
                                )
                            ).title(locObject.locationName).snippet(locObject.locationDescrip)
                        )
                    )

                }
//        something.put("The Mansion",map?.addMarker(MarkerOptions().position(LatLng(44.235030, -76.496399)).title("The Mansion").snippet("Come here to unwind for the QCC social")))
//        something.put("Renaissance",map?.addMarker(MarkerOptions().position(LatLng(44.234236, -76.491228)).title("Renaissance Event Hall").snippet("Location of Final Banquet")))
                if(mloc!=""){
                    if (mloc!="Stauffer" && mloc!="Renaissance" && mloc != "Renaissance"){
                        mloc="Goodes Hall"
                    }
                    something[mloc]!!.showInfoWindow()
                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(something[mloc]!!.position,16f))
                    slidePanel?.panelState=SlidingUpPanelLayout.PanelState.COLLAPSED
                    mMap=map
                }else if (mloc=="") {
                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(44.2258, -76.4948), 15f))
                    mMap=map
                }}}



    }

    private val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    lateinit var mMapView: MapView
    private lateinit var  mloc:String //Stauffer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val myView = inflater.inflate(R.layout.fragment_map, container, false)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        val myMap:MapView=myView.findViewById<MapView>(R.id.mapview)
        mMapView = myMap
        mMapView.onCreate(mapViewBundle)
        mMapView.getMapAsync(this)
        setupRadioListeners(myView)
        val mbundle=arguments
        if (mbundle!=null){
            mloc=mbundle["Location"].toString()
        }else
            mloc=""
        slidePanel=myView?.findViewById(R.id.slidingpanel)
        val clickListen:(loc:locationObject)->Unit= { loc ->
            when (loc.floor) {
                1L -> {
                    radiobuttonIn = view?.findViewById(R.id.indoorradioButtonTab1)
                    radiobuttonIn?.isChecked = true
                }
                2L -> {
                    radiobuttonIn = view?.findViewById(R.id.indoorradioButtonTab2)
                    radiobuttonIn?.isChecked = true
                }
                3L -> {
                    radiobuttonIn = view?.findViewById(R.id.indoorradioButtonTab3)
                    radiobuttonIn?.isChecked = true
                }
            }
            something[loc.locationName]?.showInfoWindow()
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(something[loc.locationName]?.position, 15f))
            slidePanel?.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED)
        }
        myView.frame_layout_for_click.setOnClickListener{
            slidePanel?.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED)
        }
        var eventRef: CollectionReference = FirebaseFirestore.getInstance().collection("Locations")

        var colRef=FirebaseFirestore.getInstance().collection("Locations")
        setupRecyclerAdapter(myView,eventRef,clickListen)

    return myView
    }


    private fun setupRecyclerAdapter(myView:View, colRef:CollectionReference,clickListen:(locationObject)->Unit){
       val recycler_view= myView.findViewById<RecyclerView>(R.id.locationRecycler)
       val manager= LinearLayoutManager(myView.context)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager=manager
        val adapter=mapAdapter(myView.context,colRef,clickListen)
        recycler_view.adapter=adapter

    }


fun setupRadioListeners(myView:View) {
    val radGroup1 = myView.findViewById<RadioGroup>(R.id.radioGroup1)
    radGroup1.setOnCheckedChangeListener { _, buttonID ->
        when (buttonID) {
            R.id.radioButtonTab1 -> {
                myView.findViewById<MapView>(R.id.mapview).visibility = View.VISIBLE
                myView.findViewById<ImageView>(R.id.indoorMapImage).visibility = View.GONE
                myView.findViewById<RadioGroup>(R.id.indoorRadioGroup).visibility = View.GONE

            }
            R.id.radioButtonTab2 -> {
                myView.findViewById<ImageView>(R.id.indoorMapImage).visibility = View.VISIBLE
                myView.findViewById<MapView>(R.id.mapview).visibility = View.GONE
                myView.findViewById<RadioGroup>(R.id.indoorRadioGroup).visibility = View.VISIBLE
            }
            else ->
                Log.d("Henry", "Neither button was selected?")
        }
    }
    val radGroup2= myView.findViewById<RadioGroup>(R.id.indoorRadioGroup)
    radGroup2.setOnCheckedChangeListener{_,buttonID ->
            when (buttonID) {
                R.id.indoorradioButtonTab1 ->{myView.findViewById<ImageView>(R.id.indoorMapImage).setImageResource(R.drawable.goodes_lower)}
                R.id.indoorradioButtonTab2 ->{myView.findViewById<ImageView>(R.id.indoorMapImage).setImageResource(R.drawable.goodes_1)}
                R.id.indoorradioButtonTab3 ->{myView.findViewById<ImageView>(R.id.indoorMapImage).setImageResource(R.drawable.goodes_2)}
            }
    }
}
    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        val mbundle=arguments
        if (mbundle!=null){
            mloc=mbundle["Location"].toString()
        }else
            mloc=""
        mMapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }


    override fun onPause() {
        mMapView.onPause()
        if (arguments!=null){
            val bundle=Bundle()
            bundle.putString("Location","")
            arguments=bundle
        }
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