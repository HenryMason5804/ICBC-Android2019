package com.henrylearns.adapterpractice.map

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.CollectionReference
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.LocationObject
import org.w3c.dom.Text

class mapAdapter(val context:Context, val cref: CollectionReference, val listener:(LocationObject)->Unit): RecyclerView.Adapter<mapAdapter.ViewHolder>() {
private val locArray=ArrayList<LocationObject>()
    init{
        cref.addSnapshotListener{snapshot,error->
            locArray.clear()
            if (error!=null){
                Toast.makeText(context,"Cannot access database due to $error",Toast.LENGTH_SHORT)

            }
            else if (snapshot!=null){
                for (document in snapshot){
                    val tempLoc=document.toObject(LocationObject::class.java)
                    locArray.add(tempLoc)
                }
            }
            notifyDataSetChanged()
        }
    }
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
        return locArray.size}

    override fun onBindViewHolder(holder: mapAdapter.ViewHolder, position: Int) {
        holder.locationDescription.text=locArray.get(position).locationDescrip
    holder.locationText.text=locArray.get(position).locationName
    Glide.with(context).load(locArray.get(position).imageName).into(holder.locationImageView)
    holder.itemView.setOnClickListener { listener(locArray[position]) }}

}