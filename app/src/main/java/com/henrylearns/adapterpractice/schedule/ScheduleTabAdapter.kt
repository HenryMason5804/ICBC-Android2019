package com.henrylearns.adapterpractice.schedule

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ScheduleTabAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            //TODO Add bundle to each tab so that adapter can fill schedules differently
            0 -> ScheduleFragment.newInstance(position)
            else -> ScheduleFragment.newInstance(position)//Not sure why Bruce made it this way but 0 is the number of columns (also works for 1 but doesn't workd for >=2
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Thursday"
            1 -> "Friday"
            else -> "Saturday"
        }
    }
}