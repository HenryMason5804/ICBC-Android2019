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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullSponsorObject
import com.henrylearns.adapterpractice.dataobjects.SponsorInfoViewHolderObject
import kotlinx.android.synthetic.main.fragment_event_info_viewholder.*
import org.w3c.dom.Text
import java.util.*

class SponsorInfoAdapter(val context: Context,val assocEventID:MutableList<Long> ,val dbr:CollectionReference,val listener:((sponsorID:Long, fragType:Int)->Unit)): RecyclerView.Adapter<SponsorInfoAdapter.ViewHolder>() {
    var registration:ListenerRegistration? = null
    var objectArrayList:ArrayList<FullSponsorObject> = ArrayList()

  init {

      registration = dbr.addSnapshotListener { snapshot, e ->
          if (e != null) {
          }
          if (snapshot != null) {
              objectArrayList.clear()
              for (document in snapshot) {
                  if (assocEventID.contains((document.data["id"]) as Long)) {
                      val newDoc = document.toObject(FullSponsorObject::class.java)
                      objectArrayList.add(newDoc)
                  }

              }
              notifyDataSetChanged()
          }

      }
  }




    fun unregister(){
        objectArrayList.clear()
        registration?.remove()
        registration=null
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tempView=(LayoutInflater.from(parent.context).inflate(R.layout.fragment_event_info_viewholder,parent,false))
        return SponsorInfoAdapter.ViewHolder(tempView)
    }

    override fun getItemCount(): Int {
        return objectArrayList.count()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("frick","yeet ${objectArrayList[position].name}")
        holder.sponsorName.text=objectArrayList[position].name
        holder.sponsorDescrip.text=objectArrayList[position].about
        Glide.with(context).asBitmap().load(objectArrayList[position].image).into(holder.imageView)
        holder.itemView.setOnClickListener{listener(objectArrayList[position].id,3)}

        }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var imageView=view.findViewById<ImageView>(R.id.event_info_image)
        var sponsorName=view.findViewById<TextView>(R.id.event_info_name)
        var sponsorDescrip:TextView=view.findViewById(R.id.event_info_descr)

    }
}