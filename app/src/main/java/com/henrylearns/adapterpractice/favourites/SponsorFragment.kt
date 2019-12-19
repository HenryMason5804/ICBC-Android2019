package com.henrylearns.adapterpractice.favourites


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.henrylearns.adapterpractice.R

class SponsorFragment : Fragment() {
    lateinit var sponsorNames: ArrayList<String>
    lateinit var brief: ArrayList<String>
    lateinit var description: ArrayList<String>
    private lateinit var imgurl: ArrayList<String>
    lateinit var adapter:SponsorAdapterforRecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.activity_main, container, false)
        initRecyclerView(view)
        return view
    }


    private fun initRecyclerView(view: View) {
        val parentFragment = parentFragment?.parentFragment
        if (parentFragment is rootFrameLayout) {
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
            val manager = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = manager
            var sponsorRef: CollectionReference=FirebaseFirestore.getInstance().collection("Sponsors")

            adapter = SponsorAdapterforRecyclerView(
                    Glide.with(context!!),
                    sponsorRef,
                    parentFragment.myClickListener
            )
            Log.d("DebugNoAdapter", "instantiateadapter called")
            recyclerView.adapter = adapter
            Log.d("DebugNoAdapter", "Attached adapter called")
        } else {
            throw Exception("Parent of parent fragment must be of type rootFrameLayout")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.unregister()
    }

    class Sponsor(
            var id: Float,
            var name: String,
            var sponsorLevel: String,
            var description: String,
            var imageName: String,
            var about: String,
            var linkedInURL: String,
            var companyURL: String,
            var assocEvents: Array<Float>
            )
}