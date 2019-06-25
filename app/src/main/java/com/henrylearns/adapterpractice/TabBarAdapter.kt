package com.henrylearns.adapterpractice

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class TabBarAdapter (fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {


        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> SponsorFragment()
                1 -> SponsorFragment()
                else -> SponsorFragment()
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }
