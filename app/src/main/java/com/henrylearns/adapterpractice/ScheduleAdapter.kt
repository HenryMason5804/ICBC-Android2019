package com.henrylearns.adapterpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_schedule.view.*

class MyDayRecyclerViewAdapter(
    private val mValues: List<DayModel.DayItem>,
    private val mListener: ScheduleFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyDayRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DayModel.DayItem
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
        val item = mValues[position]
        holder.mIdView.text = item.getTime()
        holder.mContentView.text = item.name
        holder.mAmPm.text = item.am_pm
        holder.mDuration.text = item.duration
        holder.mLocation.text = item.location
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

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