package com.henrylearns.adapterpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class profileAdapter(val context: Context): RecyclerView.Adapter<profileAdapter.ViewHolder>() {
    val imageURLs="https://46yuuj40q81w3ijifr45fvbe165m-wpengine.netdna-ssl.com/wp-content/uploads/2018/08/horseshoe-bend-600x370.jpg"
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var image=view.findViewById<CircleImageView>(R.id.circleImage)
        var text=view.findViewById<TextView>(R.id.execName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): profileAdapter.ViewHolder {
        val tempView=(LayoutInflater.from(parent.context).inflate(R.layout.profile_view_holder,parent,false))
        return ViewHolder(tempView) }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: profileAdapter.ViewHolder, position: Int) {
        Glide.with(context).asBitmap().load(imageURLs).into(holder.image)
        holder.text.text="${position}"}


}
