package com.henrylearns.adapterpractice.map

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.LocationObject
import org.w3c.dom.Text

class mapAdapter(val context:Context,val objectList:ArrayList<LocationObject>,val listener:(LocationObject)->Unit): RecyclerView.Adapter<mapAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val locationImageView = view.findViewById<ImageView>(R.id.locationImage)
        val locationText: TextView = view.findViewById<TextView>(R.id.location)
        val locationDescription: TextView = view.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mapAdapter.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.location_view_holder,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return objectList.size}

    override fun onBindViewHolder(holder: mapAdapter.ViewHolder, position: Int) {
        holder.locationDescription.text=objectList.get(position).eventDescrip
    holder.locationText.text=objectList.get(position).locationName
    Glide.with(context).load(objectList.get(position).imageName).into(holder.locationImageView)
    holder.itemView.setOnClickListener { listener(objectList[position]) }}

}