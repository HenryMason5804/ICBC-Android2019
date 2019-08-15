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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullEventObject
import com.henrylearns.adapterpractice.dataobjects.FullSponsorObject


class EventAdapterforRecylerView(val context:Context,val eventColref:CollectionReference, val clickListenerFunction:(id: Long,fragType:Int) -> Unit): RecyclerView.Adapter<EventAdapterforRecylerView.ViewHolder>() {
    private var eventObjectList=ArrayList<FullEventObject>()
    var registration: ListenerRegistration? = null

    fun unregister(){
        eventObjectList.clear()
        registration?.remove()
        registration=null
    }
    init {
        val queryEventColRef=eventColref.whereEqualTo("scheduleOnly",false)
        registration =queryEventColRef.addSnapshotListener { snapshot, error ->
            eventObjectList.clear()
            if (error != null) {
                Log.d("DatabaseReferenceFail", "Database reference error $error")
            }
            if (snapshot != null) {
                for (document in snapshot) {
                    val eventObject = document.toObject(FullEventObject::class.java)
                    eventObjectList.add(eventObject)

                }
            }
            notifyDataSetChanged()
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val eventImageView: ImageView =view.findViewById(R.id.event_image)
        val eventcodeView: TextView =view.findViewById(R.id.event_code)
        val eventTitleView: TextView =view.findViewById(R.id.event_title)
        val eventDescripView: TextView =view.findViewById(R.id.event_descr)
    }


    override fun getItemCount(): Int {
        return eventObjectList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).asBitmap().load(eventObjectList.get(position).image).into(holder.eventImageView)
        holder.eventDescripView.text = eventObjectList.get(position).description
        holder.eventTitleView.text = eventObjectList.get(position).title
        holder.eventcodeView.text = eventObjectList.get(position).eventCode
        holder.itemView.setOnClickListener{clickListenerFunction(eventObjectList[position].id, 3)
            Log.d("Henry","made it past the setOnClickListener")}

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tempView=(LayoutInflater.from(parent.context).inflate(R.layout.fragment_event,parent,false))
        return ViewHolder(tempView)
    }

}
