package com.henrylearns.adapterpractice.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.CollectionReference
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullExecObject
import de.hdodenhof.circleimageview.CircleImageView

class profileAdapter(val context: Context,val dbr:CollectionReference,val listener:((profileID:FullExecObject)->Unit)): RecyclerView.Adapter<profileAdapter.ViewHolder>() {
    val imageURLs="https://46yuuj40q81w3ijifr45fvbe165m-wpengine.netdna-ssl.com/wp-content/uploads/2018/08/horseshoe-bend-600x370.jpg"
    val profileList = ArrayList<FullExecObject>()
    init {
        dbr.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Toast.makeText(context, "cannot find this exec due to $error", Toast.LENGTH_LONG)
            } else if (snapshot != null) {
                for (document in snapshot){
                    val mExecObject=document.toObject(FullExecObject::class.java)
                    profileList.add(mExecObject)
                    notifyDataSetChanged()
                }
            }

        }
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var image=view.findViewById<CircleImageView>(R.id.circleImage)
        var text=view.findViewById<TextView>(R.id.execName)

    }
    fun unregister(){
        //TODO put in registration
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tempView=(LayoutInflater.from(parent.context).inflate(R.layout.profile_view_holder,parent,false))
        return ViewHolder(tempView)
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).asBitmap().load(imageURLs).into(holder.image)
        holder.text.text="${profileList[position].firstName}"
    holder.itemView.setOnClickListener { listener(profileList[position]) }}


}
