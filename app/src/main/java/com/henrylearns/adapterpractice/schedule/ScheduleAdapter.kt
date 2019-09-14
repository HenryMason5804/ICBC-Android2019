package com.henrylearns.adapterpractice.schedule

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.henrylearns.adapterpractice.DayModel
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullEventObject
import kotlinx.android.synthetic.main.item_schedule.view.*
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyDayRecyclerViewAdapter(
        private val column:Int,
        private val mListener: ScheduleFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyDayRecyclerViewAdapter.ViewHolder>() {
    lateinit var dailyEventCollectionReference: Query
    private val mOnClickListener: View.OnClickListener
    var eventList=ArrayList<FullEventObject>()
    init {
        FirebaseFirestore.getInstance()
        val myColRef=FirebaseFirestore.getInstance().collection("Events")
        //replace with calendar instance Calendar

        var day2 =Date(2019,9,21)


        if (column==0){
             dailyEventCollectionReference=myColRef.orderBy("startDate").endBefore(day2)
        }
        else{
            dailyEventCollectionReference=myColRef.orderBy("startDate").startAfter(day2)
        }
        dailyEventCollectionReference.addSnapshotListener{snapshot,error->
            if (error !=null){
                Log.d("SnapshotFailure","$error")
            }
            if (snapshot!=null){
                for (document in snapshot){
                    val tempEvent=document.toObject(FullEventObject::class.java)
                    /*if (tempEvent.startDate.time>day2){
                        Log.d("ghahhhhh","som")
                    }*/
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
        var formatter:Format=SimpleDateFormat("MMM-dd HH:mm")
        val string=formatter.format(eventList[position].startDate)
        val myCalendar1=Calendar.getInstance().also{it.time=eventList[position].startDate}
        val myCalendar2=Calendar.getInstance().also{it.time=eventList[position].endDate}

        holder.mIdView.text = string
        holder.mContentView.text = eventList[position].title
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