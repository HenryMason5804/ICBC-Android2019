package com.henrylearns.adapterpractice.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.henrylearns.adapterpractice.R
import kotlinx.android.synthetic.main.fragment_tab_schedule.*

class ScheduleTabFragment : Fragment() {
    companion object {
        fun newInstance(num: Int): ScheduleTabFragment {
            val f = ScheduleTabFragment()
            val args = Bundle()
            args.putInt("num", num)
            f.arguments = args
            return f
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nestedViewPager?.adapter = ScheduleTabAdapter(childFragmentManager, context!!)
        nestedViewPager?.setPagingEnabled(false)
        nestedViewPager?.currentItem = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): android.view.View? {
        val view = inflater.inflate(R.layout.fragment_tab_schedule, container, false)
        return view
    }

}
