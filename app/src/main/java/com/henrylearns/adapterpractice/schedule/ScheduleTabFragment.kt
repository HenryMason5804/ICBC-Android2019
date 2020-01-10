package com.henrylearns.adapterpractice.schedule

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.prefUtil
import kotlinx.android.synthetic.main.fragment_tab_schedule.*
import kotlinx.android.synthetic.main.fragment_tab_schedule.view.*

class ScheduleTabFragment : Fragment() {
    lateinit var i:Intent
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
    fun getIntnt():Intent{
        return i
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): android.view.View? {
        val view = inflater.inflate(R.layout.fragment_tab_schedule, container, false)
        //val i:Intent
        var userID=getUserID(context!!)
        if (userID == "volunteer"){
             i= Intent(Intent.ACTION_VIEW,Uri.parse("https://docs.google.com/spreadsheets/u/1/d/1nQRQonQ0UAhISbqnAZ3BmkyHcDO1vFvn"))
            view.execSchedule.setText("Volunteer Schedule")
        }
        else if ( userID == "judge"){
             i= Intent(Intent.ACTION_VIEW,Uri.parse("https://docs.google.com/spreadsheets/d/1gJWml-tpbrJAGAFDw1HC30KA5RBAwGs5"))
            view.execSchedule.setText("Judge Schedule")
        }
        else if( userID == "exec"){
             i= Intent(Intent.ACTION_VIEW,Uri.parse("https://docs.google.com/spreadsheets/u/1/d/1rDU8Z5UigZdMYAUQyFWSKBEcgeyfjt-B"))
            view.execSchedule.setText("Exec Schedule")
        }
        else{
            view.execSchedule.setVisibility(View.GONE)
    }

        view.execSchedule.setOnClickListener {
            i
        startActivity(i)
        }
        val userName=getUserName(context!!)
        val first = userName?.split(" ")
        if (first!=null) {
            if (!(first[0].equals(""))) {
                val name=first[0].capitalize()
                view.greeting_text_schedule.text = "Hello ${name}, here is your day"
            }
            else view.greeting_text_schedule.text = "Hello, here is your day"
        }
        else
            view.greeting_text_schedule.text="Hello.  Here is your day"
        return view
    }
    fun getUserName(context: Context): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(prefUtil.USER_NAME_TAG, null)
    }
    fun getUserID(context: Context): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(prefUtil.USER_ID_TAG, null)
    }
}
