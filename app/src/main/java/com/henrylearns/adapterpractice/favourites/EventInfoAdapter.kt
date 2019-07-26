package com.henrylearns.adapterpractice.favourites

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.henrylearns.adapterpractice.R


class EventInfoAdapter(val context: Context, val imageURLs:ArrayList<String>, val eventTitle:ArrayList<String>, val eventCode:ArrayList<String>, val eventDescrip:ArrayList<String>): RecyclerView.Adapter<EventInfoAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {Glide.with(context).asBitmap().load(imageURLs.get(position)).into(holder.eventImageView)
        holder.eventDescripView.text = eventDescrip.get(position)
        holder.eventTitleView.text = eventTitle.get(position)
        holder.eventcodeView.text = eventCode.get(position)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tempView=(LayoutInflater.from(parent.context).inflate(R.layout.fragment_event,parent,false))
        return ViewHolder(tempView)
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val eventImageView: ImageView =view.findViewById(R.id.event_image)
        val eventcodeView: TextView =view.findViewById(R.id.event_code)
        val eventTitleView: TextView =view.findViewById(R.id.event_title)
        val eventDescripView: TextView =view.findViewById(R.id.event_descr)
    }


    override fun getItemCount(): Int {
        return imageURLs.size
    }


}
