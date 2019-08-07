package com.henrylearns.adapterpractice.favourites


import android.net.sip.SipSession
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import com.google.firebase.firestore.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullSponsorObject
import com.henrylearns.adapterpractice.dataobjects.SponsorInfoViewHolderObject
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.*
import java.lang.reflect.Array
import java.util.*


class SponsorInfoFragment : Fragment() {

lateinit var adapter:SponsorInfoAdapter
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        lateinit var clickListener:(Long,Int)->Unit
        val mybundle = arguments
        val ppFrag=parentFragment?.parentFragment
        val pFrag=parentFragment
        if (ppFrag is rootFrameLayout){
            clickListener = ppFrag.myClickListener}
        else if(pFrag is rootFrameLayout){
            clickListener=pFrag.myClickListener
        }
        val view = inflater.inflate(R.layout.fragment_sponsor_info, container, false)
        var sponsorColRef: CollectionReference = FirebaseFirestore.getInstance().collection("Sponsors")
        val thisSpons = sponsorColRef.whereEqualTo("id", mybundle!!.getLong("ID")).get()
        var array: (ArrayList<Long>) = ArrayList<Long>()
        thisSpons.addOnSuccessListener { documents ->
            for (document in documents) {
                val documentObj = document.toObject(FullSponsorObject::class.java)
                Glide.with(view).asBitmap().load(document["image"].toString()).into(view.imageView)
                view.companyName.text = document["name"].toString()
                view.introduction.setText(document["introduction"].toString())
                view.aboutParagraph.setText(document["description"].toString())
                view.linkedInButton.setText("LinkedIn")
                view.companyButton.setText("Company Website")
                array = documentObj.assocEvents
            }
            initiateAdapter(view, array,clickListener)
        }

        return view
    }
    override fun onDestroy() {
        super.onDestroy()
        adapter.unregister()
    }

    fun initiateAdapter(view: View, myList: MutableList<Long>,myclickListener:(Long,Int)->Unit) {
        val recView = view.findViewById<RecyclerView>(R.id.sponsorInfoRecyclerView)
        val manager = LinearLayoutManager(view.context)
        recView.setHasFixedSize(true)
        recView.layoutManager = manager
        val eventColRef = FirebaseFirestore.getInstance().collection("Events")
        val adapter = SponsorInfoAdapter(view.context, myList, eventColRef,myclickListener)
        recView.adapter = adapter
    }

}