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
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullSponsorObject


class EventInfoAdapter(val context: Context,myList:ArrayList<Long>,sponsColRef:CollectionReference,val listener:(Long,Int)->Unit): RecyclerView.Adapter<EventInfoAdapter.ViewHolder>() {

   var  sponsorList=ArrayList<FullSponsorObject>()
    var registration:ListenerRegistration?


    fun unregister(){
        sponsorList.clear()
        registration?.remove()
        registration=null
    }
    init {
        sponsorList.clear()
       registration= sponsColRef.addSnapshotListener{snapshot,error->
            if (error!=null){
                Log.d("SnapshotFailrue","snapfailed $error")
            }
            if (snapshot!=null){
                for (document in snapshot) {
                    if (myList.contains(document["id"])) {
                        val sponsObj = document.toObject(FullSponsorObject::class.java)
                        sponsorList.add(sponsObj)
                    }
                }
                notifyDataSetChanged()
            }
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.eventDescripView.text = sponsorList[position].about
        holder.eventNameView.text = sponsorList[position].name
        Glide.with(context).asBitmap().load(sponsorList[position].image).into(holder.eventImageView)
        holder.itemView.setOnClickListener{listener(sponsorList[position].id,2)}


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tempView=(LayoutInflater.from(parent.context).inflate(R.layout.fragment_event_info_viewholder,parent,false))
        return ViewHolder(tempView)
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val eventImageView: ImageView =view.findViewById(R.id.event_info_image)
        val eventDescripView: TextView =view.findViewById(R.id.event_info_name)
        val eventNameView: TextView =view.findViewById(R.id.event_info_descr)
    }


    override fun getItemCount(): Int {
        return sponsorList.size
    }


}
