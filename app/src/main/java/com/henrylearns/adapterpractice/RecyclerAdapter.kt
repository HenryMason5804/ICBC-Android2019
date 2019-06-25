package com.henrylearns.adapterpractice
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager


class RecyclerAdapter(val Request:RequestManager, val photos:ArrayList<String>, val names:ArrayList<String>, val sponsorLevel:ArrayList<String>, val sponsorDescr:ArrayList<String>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
           val sponsorImageView: ImageView =itemView.findViewById(R.id.product_image)
            val sponsorLevelView:TextView =itemView.findViewById(R.id.product_title)
            val sponsorNameView:TextView=itemView.findViewById(R.id.product_price)
            val sponsorDescrView:TextView=itemView.findViewById(R.id.product_Brief)
    }

    override fun getItemCount(): Int {
            return 5
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        Log.d("bvhdebug", "onbindviewholder called")
        Request.asBitmap().load(photos.get(position)).into(holder.sponsorImageView)
        holder.sponsorLevelView.text = names.get(position)
        holder.sponsorNameView.text = sponsorLevel.get(position)
        holder.sponsorDescrView.text=sponsorDescr.get(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder{
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapterviewlayou, parent, false))

}
}
