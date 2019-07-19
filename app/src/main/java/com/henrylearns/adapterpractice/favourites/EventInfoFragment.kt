package com.henrylearns.adapterpractice.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.bumptech.glide.Glide
import com.henrylearns.adapterpractice.R
import kotlinx.android.synthetic.main.fragment_event_info.view.*

import kotlinx.android.synthetic.main.fragment_sponsor_info.view.aboutParagraph
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.companyName
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.imageView
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.introduction


class EventInfoFragment : Fragment() {


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
        return view
    }
}