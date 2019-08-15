package com.henrylearns.adapterpractice.favourites


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.henrylearns.adapterpractice.R
import kotlinx.android.synthetic.main.fragment_event_info.view.*
import java.sql.Time


class EventFragment : Fragment() {

    lateinit var eventTitle:ArrayList<String>
    lateinit var eventDescrip:ArrayList<String>
    lateinit var eventImage:ArrayList<String>
    lateinit var eventCode:ArrayList<String>
    lateinit var adapter:EventAdapterforRecylerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_main,container,false)
        initiateRecyclerViewAdapter(view)

        return view
    }
    fun initiateRecyclerViewAdapter(view:View) {
        val parentFrag = parentFragment?.parentFragment
        if (parentFrag is rootFrameLayout) {
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
            val manager = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = manager
            var eventRef: CollectionReference = FirebaseFirestore.getInstance().collection("Events")
            adapter = EventAdapterforRecylerView(view.context,eventRef, parentFrag.myClickListener)
            recyclerView.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.unregister()
    }

}
