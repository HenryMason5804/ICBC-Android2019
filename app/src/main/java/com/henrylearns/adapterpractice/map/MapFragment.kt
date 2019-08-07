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
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.LocationObject
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class MapFragment : Fragment(),OnMapReadyCallback {

   var radiobuttonOut:RadioButton?=null
    var radiobuttonIn:RadioButton?=null
    var radiobuttonFloor1:RadioButton?=null
    var radiobuttonFloor2:RadioButton?=null
    var radiobuttonFloor3:RadioButton?=null
    var slidePanel:SlidingUpPanelLayout?=null




    var something =HashMap<String,Marker?>()
    var mMap:GoogleMap?=null
    override fun onMapReady(map: GoogleMap?) {

        something.put("Stauffer", map?.addMarker(MarkerOptions().position(LatLng(44.228488, -76.496531)).title("Stauffer Library").snippet("Here there be books")))
        something.put("Goode's Hall",map?.addMarker(MarkerOptions().position(LatLng(44.228518, -76.497547)).title("Goodes Hall").snippet("This is where you go if you want to flex your neighbourhood")))
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(44.2258, -76.4948),15f))
        mMap=map
    }

    private val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    lateinit var mMapView: MapView

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
        val objectList= createAdapterObjectList()
        slidePanel=view?.findViewById(R.id.indoorradioButtonTab1)
        val clickListen:(loc:LocationObject)->Unit={loc->7
            something[loc.locationName]?.showInfoWindow()
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(something[loc.locationName]?.position,16f))
            slidePanel?.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED)
            Toast.makeText(context,"yeet",Toast.LENGTH_SHORT)
        }
        setupRecyclerAdapter(myView,objectList,clickListen)

    return myView
    }

    private fun setupRecyclerAdapter(myView:View, objectList:ArrayList<LocationObject>,clickListen:(LocationObject)->Unit){
       val recycler_view= myView.findViewById<RecyclerView>(R.id.locationRecycler)
       val manager= LinearLayoutManager(myView.context)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager=manager
        val adapter=mapAdapter(myView.context,objectList,clickListen)
        recycler_view.adapter=adapter

    }
    fun createAdapterObjectList():ArrayList<LocationObject>{
        val adapterList=ArrayList<LocationObject>()
        adapterList.add(LocationObject("https://www.theglobeandmail.com/resizer/N2gndinET8Jg1Ukij-HJZLnctbU=/620x0/filters:quality(80)/arc-anglerfish-tgam-prod-tgam.s3.amazonaws.com/public/TC74J3SVIJHJ5I756PPFDTKXNE.jpg", "Stauffer", "food Tasting and eating and also sitting on"))
        adapterList.add(LocationObject("https://d3vhc53cl8e8km.cloudfront.net/hello-staging/wp-content/uploads/2017/12/22223742/Events-1200x630.jpg", "Goode's Hall", "Learn to get people to smoke molly and other such FAT DROGUES"))
        adapterList.add(LocationObject("https://d33jrn6nlx7mhr.cloudfront.net/_novaimg/galleria/1356619.jpg", "THE WEDDING PAV", "Get group married you fools"))
        return adapterList
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
                R.id.indoorradioButtonTab1 ->{myView.findViewById<ImageView>(R.id.indoorMapImage).setImageResource(R.drawable.stauffer_lower_level)}
                R.id.indoorradioButtonTab2 ->{myView.findViewById<ImageView>(R.id.indoorMapImage).setImageResource(R.drawable.stauffer_1st_level)}
                R.id.indoorradioButtonTab3 ->{myView.findViewById<ImageView>(R.id.indoorMapImage).setImageResource(R.drawable.stauffer_2nd_level)}
            }
    }
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