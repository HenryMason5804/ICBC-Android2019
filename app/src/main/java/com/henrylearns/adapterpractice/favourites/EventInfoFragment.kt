package com.henrylearns.adapterpractice.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.henrylearns.adapterpractice.R
import kotlinx.android.synthetic.main.fragment_event_info.*
import kotlinx.android.synthetic.main.fragment_event_info.view.*
import kotlinx.android.synthetic.main.fragment_event_info.view.eventInfoRecyclerView

import kotlinx.android.synthetic.main.fragment_sponsor_info.view.aboutParagraph
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.companyName
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.imageView
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.introduction


class EventInfoFragment : Fragment() {

    lateinit var eventCode:ArrayList<String>
    lateinit var eventTitle:ArrayList<String>
    lateinit var eventDescrip:ArrayList<String>
    lateinit var eventImage:ArrayList<String>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_event_info, container, false)
        Glide.with(view).asBitmap().load("https://46yuuj40q81w3ijifr45fvbe165m-wpengine.netdna-ssl.com/wp-content/uploads/2018/08/horseshoe-bend-600x370.jpg").into(view.imageView)
        view.companyName.setText("Company Name")
        view.introduction.setText("Company Introduction")
        view.aboutParagraph.setText("This paragraph will describe the company")
        view.notifyButton.setText(" linkedIn")
        view.mapButton.setText("company Website")
        createArray()
        initiateAdapter(view)
        return view



    }

    fun createArray(){
        eventCode= ArrayList()
        eventTitle=ArrayList()
        eventDescrip= ArrayList()
        eventImage=ArrayList()
        eventTitle.add("How to succeed as a woman in the workforce")
        eventTitle.add("How to make a REAL NICE pivot table")
        eventTitle.add("What does success mean")
        eventTitle.add("How to make your boss think you are better than 'Fred'")
        eventDescrip.add("In this seminar we explore some of the unique challenges that you may face" +
                "as a woman in the workplace and some strategies to overcome them.")
        eventDescrip.add("Learn how to display data in a way that is so intuitive, so ATTRACTIVE" +
                "that you will never get promoted out of data entry")
        eventDescrip.add("In this we do some yoga and talk about how to find your inner success")
        eventDescrip.add("Learn how to consistently make your boss think that all of the mistakes are" +
                "'Fred`s' mistakes and how all of his successes are yours" )
        eventImage.add("https://46yuuj40q81w3ijifr45fvbe165m-wpengine.netdna-ssl.com/wp-content/uploads/2018/08/horseshoe-bend-600x370.jpg")
        eventImage.add("https://www.nationalgeographic.com/content/dam/travel/2019-digital/yosemite-guide/yosemite-national-park-california.jpg")
        eventImage.add("https://yt3.ggpht.com/a/AGF-l7_v3YS5U2yeCA-JaS4Yos54xjf3TFE6U5eeRA=s900-mo-c-c0xffffffff-rj-k-no")
        eventImage.add("https://coda.newjobs.com/api/imagesproxy/ms/cms/content30/images/liar-boss.jpg")
        eventCode.add("gettitLADS")
        eventCode.add("gettitLADS")
        eventCode.add("gettitLADS")
        eventCode.add("gettitLADS")
        eventCode.add("gettitLADS")}

    fun initiateAdapter(view:View){
        val recView=view.findViewById<RecyclerView>(R.id.eventInfoRecyclerView)
        val manager=LinearLayoutManager(view.context)
        recView.setHasFixedSize(true)
        recView.layoutManager=manager
        val adapter=EventInfoAdapter(view.context,eventImage,eventTitle,eventCode,eventDescrip)
    }
}