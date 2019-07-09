package com.henrylearns.adapterpractice

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.fragment.app.FragmentTransaction


class RecyclerAdapterForEvent(val context:Context,val imageURLs:ArrayList<String>,val eventTitle:ArrayList<String>, val eventCode:ArrayList<String>, val eventDescrip:ArrayList<String>, val clickListenerFunction:(ArrayList<String>)->Int): RecyclerView.Adapter<RecyclerAdapterForEvent.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val eventImageView: ImageView =view.findViewById(R.id.event_image)
        val eventcodeView: TextView =view.findViewById(R.id.event_code)
        val eventTitleView: TextView =view.findViewById(R.id.event_title)
        val eventDescripView: TextView =view.findViewById(R.id.event_descr)
    }


    override fun getItemCount(): Int {
        return imageURLs.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapterForEvent.ViewHolder, position: Int) {
        Glide.with(context).asBitmap().load(imageURLs.get(position)).into(holder.eventImageView)
        holder.eventDescripView.text = eventDescrip.get(position)
        holder.eventTitleView.text = eventTitle.get(position)
        holder.eventcodeView.text = eventCode.get(position)
        holder.itemView.setOnClickListener{clickListenerFunction(eventTitle)
            Log.d("Henry","made it past the setOnClickListener")}

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterForEvent.ViewHolder {
        val tempView=(LayoutInflater.from(parent.context).inflate(R.layout.fragment_event,parent,false))
        return ViewHolder(tempView)
    }

}
