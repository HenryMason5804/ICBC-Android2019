package com.henrylearns.adapterpractice.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.henrylearns.adapterpractice.R


// TODO: Rename parameter arguments, choose names that match

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_profile,container,false)
        initRecyclerAdapter(view)
        return view}

fun initRecyclerAdapter(view:View){
    val recyclerView=view.findViewById<RecyclerView>(R.id.execTeamRecycler)
    val manager = LinearLayoutManager(view.context)
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = manager
    val myAdapter= profileAdapter(view.context)
    recyclerView.adapter=myAdapter
}
}
