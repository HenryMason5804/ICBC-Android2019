package com.henrylearns.adapterpractice.favourites

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabBarAdapter (fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 ->{ Log.d("getItemCheck","Went to 0")
                    SponsorFragment()
                }
                else -> {
                    Log.d("getItemCheck","Went to 1")
                    EventFragment()
                }

                }

        }

        override fun getCount(): Int {
            return 2
        }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0-> "Sponsors"
            1->"Events"
            else->{"Events"}
        }
    }
    }
