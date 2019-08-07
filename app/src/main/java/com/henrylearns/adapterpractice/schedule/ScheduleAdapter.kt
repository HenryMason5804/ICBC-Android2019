package com.henrylearns.adapterpractice.schedule

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.henrylearns.adapterpractice.DayModel
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullEventObject
import kotlinx.android.synthetic.main.item_schedule.view.*
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyDayRecyclerViewAdapter(
        private val mValues: List<DayModel.DayItem>,
        private val mListener: ScheduleFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyDayRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    var eventList=ArrayList<FullEventObject>()
    init {
        FirebaseFirestore.getInstance()
        val myColRef=FirebaseFirestore.getInstance().collection("Events")
        //replace with calendar instance Calendar
        val day1=Date(120,0,19)
        var dailyEventCollectionReference=myColRef.orderBy("time").startAfter(day1)
        dailyEventCollectionReference.addSnapshotListener{snapshot,error->
            if (error !=null){
                Log.d("SnapshotFailure","$error")
            }
            if (snapshot!=null){
                for (document in snapshot){
                    val tempEvent=document.toObject(FullEventObject::class.java)
                    eventList.add(tempEvent)
                }
                notifyDataSetChanged()
            }
        }
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as FullEventObject
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var formatter:Format=SimpleDateFormat("MMMM-dd HH:mm")
        val string=formatter.format(eventList[position].time)
        val myCalendar1=Calendar.getInstance().also{it.time=eventList[position].time}
        val myCalendar2=Calendar.getInstance().also{it.time=eventList[position].timeEnd}

        holder.mIdView.text = string
        holder.mContentView.text = eventList[position].name
        holder.mDuration.text = "${(myCalendar2.timeInMillis-myCalendar1.timeInMillis)/(60*1000)}m"
        holder.mLocation.text = eventList[position].location
        with(holder.mView) {
            tag=eventList[position]
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = eventList.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val mAmPm: TextView = mView.am_pm
        val mDuration = mView.duration
        val mLocation = mView.location

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}