package com.henrylearns.adapterpractice


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class SponsorFragment : Fragment() {
    lateinit var sponsorNames: ArrayList<String>
    lateinit var brief: ArrayList<String>
    lateinit var description: ArrayList<String>
    private lateinit var imgurl: ArrayList<String>

    companion object{
        fun newInstance():SponsorFragment{
            return SponsorFragment()
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        Log.d("DebugNoAdapter", "onCreateView called")
        val view:View =inflater.inflate(R.layout.activity_main,container,false)
        createArrays()
        initRecyclerView(view)
        Log.d("DebugNoAdapter", "initRecyclerView() called")
        return view
    }

    private fun initRecyclerView(view:View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val manager= LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager
        val myAdapter = RecyclerAdapter(Glide.with(context!!), imgurl, sponsorNames, description,brief)
        Log.d("DebugNoAdapter", "instantiateadapter called")
        recyclerView.adapter = myAdapter
        Log.d("DebugNoAdapter", "Attached adapter called")

    }

    private fun createArrays() {
        brief=ArrayList<String>()
        imgurl = ArrayList<String>()
        sponsorNames = ArrayList<String>()
        description = ArrayList<String>()
        imgurl.add("https://image.shutterstock.com/image-photo/monarch-buttefly-260nw-525765136.jpg")
        imgurl.add("https://www.pixoto.com/images-photography/animals/other/butteflies-5742287486189568.jpg")
        imgurl.add("https://i.pinimg.com/originals/ed/d6/16/edd616ec9a7881697b0f2c409c77e9ff.jpg")
        imgurl.add("https://images.fineartamerica.com/images-medium-large/purple-landscape-bogdan-floridana-oana.jpg")
        imgurl.add("https://images.csmonitor.com/csm/2015/10/944693_1_1029%20panda%20diplomacy_standard.jpg?alias=standard_900x600")
        imgurl.add("https://i.imgur.com/xlV8wmM.jpg")
        sponsorNames.add("ButterFly1")
        sponsorNames.add("ButterFly2")
        sponsorNames.add("PrettyPurple1")
        sponsorNames.add("PrettyPurple2")
        sponsorNames.add("Panda")
        sponsorNames.add("Rock")
        description.add("Gold Sponsor")
        description.add("Gold Sponsor")
        description.add("Silver Sponsor")
        description.add("Silver Sponsor")
        description.add("Silver Sponsor")
        description.add("Silver Sponsor")
        brief.add("this is where I would put my description but I am too lazy to change them for each")
        brief.add("this is where I would put my description but I am too lazy to change them for each")
        brief.add("this is where I would put my description but I am too lazy to change them for each")
        brief.add("this is where I would put my description but I am too lazy to change them for each")
        brief.add("this is where I would put my description but I am too lazy to change them for each")
        brief.add("this is where I would put my description but I am too lazy to change them for each")
    }
}