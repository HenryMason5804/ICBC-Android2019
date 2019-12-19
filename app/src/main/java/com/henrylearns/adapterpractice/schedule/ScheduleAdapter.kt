package com.henrylearns.adapterpractice.schedule

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.common.math.IntMath.mod
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.henrylearns.adapterpractice.DayModel
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullEventObject
import kotlinx.android.synthetic.main.item_schedule.view.*
import java.lang.Exception
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyDayRecyclerViewAdapter(
        private val column:Int,private val accessCodes:String?=null,
        private val mListener: ScheduleFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyDayRecyclerViewAdapter.ViewHolder>() {
    var dailyEventCollectionReference: Query
    private val mOnClickListener: View.OnClickListener
    var eventList=ArrayList<FullEventObject>()
    init {
        FirebaseFirestore.getInstance()
        val myColRef=FirebaseFirestore.getInstance().collection("Events")
        //replace with calendar instance Calendar
        var day1 =Calendar.getInstance()
        day1.set(2019,8,20)
        var day2 =Calendar.getInstance()
        day2.set(2019,8,21,0,0,0)

        if (column==0){
             dailyEventCollectionReference=myColRef.orderBy("startDate").endBefore(day2.time)
           // dailyEventCollectionReference=myColRef.whereGreaterThan("startDate",day1.timeInMillis).whereLessThan("startDate",day2.timeInMillis)
        }
        else{
            dailyEventCollectionReference=myColRef.orderBy("startDate").startAfter(day2.time)
        }

        var splitIDColor:Char=' '
        var splitIDNumber:String=" "
        var number:Int=-1
        if (accessCodes!=null) {
            splitIDColor = accessCodes[0]
            try {
                number = (accessCodes[1] + "" + accessCodes[2]).toInt()
                Log.d("shit", "shit")
            } catch (e: Exception) {
                splitIDColor = '&'
            }
            if (splitIDColor == 'g') {
                number = mod(number-1,3)+1
                splitIDNumber=("G"+number.toString())
            }
            if (splitIDColor == 'm') {
                number = mod(number-1,4)+1
                splitIDNumber=("M"+number.toString())
            }
        }
        dailyEventCollectionReference.addSnapshotListener{snapshot,error->
            if (error !=null){
                Log.d("SnapshotFailure","$error")
            }
            if (snapshot!=null){
                for (document in snapshot){
                    val eventObject = document.toObject(FullEventObject::class.java)
                    if (eventObject.eventCode==null){
                        when (splitIDColor){
                            'm'->if( eventObject.eventType.toString().toLowerCase()!="gold"){
                                eventList.add(eventObject)
                            }
                            'g'->if (eventObject.eventType.toLowerCase()!="maroon"){
                                eventList.add(eventObject)
                            }
                            else->eventList.add(eventObject)
                        }
                    }
                    else if(splitIDNumber.toLowerCase()==eventObject.eventCode.toLowerCase()){
                        eventList.add(eventObject)
                    }
                    else if (splitIDColor=='&'){
                        eventList.add(eventObject)
                    }

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
        var formatter:Format=SimpleDateFormat("MMM-dd hh:mm a")
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