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
import com.henrylearns.adapterpractice.R


class EventFragment : Fragment() {

    lateinit var eventTitle:ArrayList<String>
    lateinit var eventDescrip:ArrayList<String>
    lateinit var eventImage:ArrayList<String>
    lateinit var eventCode:ArrayList<String>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_main,container,false)
        createArray()
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
            val myAdapter = EventAdapterforRecylerView(view.context, eventImage, eventTitle, eventCode, eventDescrip, parentFrag.myClickListener)
            recyclerView.adapter = myAdapter
        }
    }

    fun createArray(){
        eventCode= ArrayList()
        eventTitle=ArrayList()
        eventDescrip= ArrayList()
        eventImage=ArrayList()
        eventTitle.add("How to succeed as a woman in the workforce")
        eventTitle.add("How to make a REAL NICE pivot table")
        eventTitle.add("What does success mean")
        eventTitle.add("How to make your boss think you are better than 'Fred'")
        eventDescrip.add("In this seminar we explore some of the unique challenges that you may face" +
                "as a woman in the workplace and some strategies to overcome them.")
        eventDescrip.add("Learn how to display data in a way that is so intuitive, so ATTRACTIVE" +
                "that you will never get promoted out of data entry")
        eventDescrip.add("In this we do some yoga and talk about how to find your inner success")
        eventDescrip.add("Learn how to consistently make your boss think that all of the mistakes are" +
                "'Fred`s' mistakes and how all of his successes are yours" )
        eventImage.add("https://46yuuj40q81w3ijifr45fvbe165m-wpengine.netdna-ssl.com/wp-content/uploads/2018/08/horseshoe-bend-600x370.jpg")
        eventImage.add("https://www.nationalgeographic.com/content/dam/travel/2019-digital/yosemite-guide/yosemite-national-park-california.jpg")
        eventImage.add("https://yt3.ggpht.com/a/AGF-l7_v3YS5U2yeCA-JaS4Yos54xjf3TFE6U5eeRA=s900-mo-c-c0xffffffff-rj-k-no")
        eventImage.add("https://coda.newjobs.com/api/imagesproxy/ms/cms/content30/images/liar-boss.jpg")
        eventCode.add("gettitLADS")
        eventCode.add("gettitLADS")
        eventCode.add("gettitLADS")
        eventCode.add("gettitLADS")
        eventCode.add("gettitLADS")
}

    val myClickListener = {eventNames: ArrayList<String> -> val trans = childFragmentManager
            .beginTransaction();
        /*
         * IMPORTANT: We use the "root frame" defined in
         * "root_fragment.xml" as the reference to replace fragment
         */
        Log.d("Henry","root frame is ${R.id.root_frame}")
        trans.replace(R.id.root_frame, SponsorInfoFragment())
        Log.d("Henry","made it past the replace")

        /*
         * IMPORTANT: The following lines allow us to add the fragment
         * to the stack and return to it later, by pressing back
         */
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Log.d("Henry","made it past the transition")

        trans.addToBackStack(null)
        Log.d("Henry","made it past the backstack")

        trans.commit();
        Log.d("Henry","made it past the commit")
    }
    /*
    data class Event(
        var ID:Float,
        var eventCode:String,
        var name:String,
        var description:String,
        var about:String,
        var imageURL:String,
        var location:String,
        var time:Time,
        var mapbuttoninfo: String,
        var assocSponsors:Array<Float>
    )*/

}
