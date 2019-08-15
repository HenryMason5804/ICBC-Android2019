package com.henrylearns.adapterpractice.favourites
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullSponsorObject
import com.henrylearns.adapterpractice.dataobjects.SponsorViewHolderObject


class SponsorAdapterforRecyclerView(val request:RequestManager, val sponsColRef:CollectionReference,val listener: (eventID: Long,fragType:Int) -> Unit) : RecyclerView.Adapter<SponsorAdapterforRecyclerView.ViewHolder>(){
private var adapterList=ArrayList<FullSponsorObject>()
    var registration: ListenerRegistration? = null

    fun unregister(){
        adapterList.clear()
        registration?.remove()
        registration=null
    }

    init {
    sponsColRef.addSnapshotListener{snapshot,error->
        adapterList.clear()
            if (error!= null){
                Log.d("DatabaseReferenceFail","Database reference error $error")
            }
            if (snapshot!=null){
                for (document in snapshot){
                    val infoObject=document.toObject(FullSponsorObject::class.java)
                    adapterList.add(infoObject)

                }
            }
        notifyDataSetChanged()
        }

    }
    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
           val sponsorImageView: ImageView =itemView.findViewById(R.id.product_image)
            val sponsorLevelView:TextView =itemView.findViewById(R.id.product_title)
            val sponsorNameView:TextView=itemView.findViewById(R.id.product_price)
            val sponsorDescrView:TextView=itemView.findViewById(R.id.product_Brief)
    }

    override fun getItemCount(): Int {
            return adapterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        request.asBitmap().load(adapterList.get(position).image).into(holder.sponsorImageView)
        holder.sponsorLevelView.text = adapterList.get(position).sponsorLevel
        holder.sponsorNameView.text = adapterList.get(position).name
        holder.sponsorDescrView.text=adapterList.get(position).about
        holder.itemView.setOnClickListener { listener(adapterList[position].id, 2)}
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view=(LayoutInflater.from(parent.context).inflate(R.layout.adapterviewlayou, parent, false))
        Log.d("DebugNoAdapter", "onCreateViewHolder called")
        return ViewHolder(view)
}
}
