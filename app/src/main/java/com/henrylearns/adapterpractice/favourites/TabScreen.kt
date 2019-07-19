package com.henrylearns.adapterpractice.favourites


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.henrylearns.adapterpractice.R


class TabScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_tab_screen,container,false)
        setupViewPager(view)
        Log.d("Henry","enteredTabScreen")
        return view
    }


    fun setupViewPager(view:View) {
        val viewPager: ViewPager = view.findViewById(R.id.TabViewPager)
        val mymanager= this.childFragmentManager
        val pagerAdapter = TabBarAdapter(mymanager)
        viewPager.adapter = pagerAdapter
        view.findViewById<TabLayout>(R.id.theTabLayout).setupWithViewPager(viewPager)
    }


}
