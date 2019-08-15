package com.henrylearns.adapterpractice.favourites

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.google.android.gms.maps.MapFragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.henrylearns.adapterpractice.MainActivity
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullEventObject
import kotlinx.android.synthetic.main.fragment_event_info.*
import kotlinx.android.synthetic.main.fragment_event_info.view.*
import kotlinx.android.synthetic.main.fragment_event_info.view.eventInfoRecyclerView

import kotlinx.android.synthetic.main.fragment_sponsor_info.view.aboutParagraph
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.companyName
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.imageView
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.introduction


class EventInfoFragment : Fragment() {

    lateinit var adapter:EventInfoAdapter
    lateinit var locName:String

    private fun createNotificationChannel(context:Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Event Upcoming"
            val descriptionText = "Test Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Channel1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    context.getSystemService( Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_event_info, container, false)
        createNotificationChannel(this.context!!)
        val mybundle=arguments
        var myList: ArrayList<Long>
        lateinit var clickListener:(Long,Int)->Unit
        val ppFrag=parentFragment?.parentFragment
        val pFrag=parentFragment
        if (ppFrag is rootFrameLayout){
            clickListener = ppFrag.myClickListener}
        else if(pFrag is rootFrameLayout){
            clickListener=pFrag.myClickListener
        }
        var eventColRef: CollectionReference = FirebaseFirestore.getInstance().collection("Events")
        val thisEvent=eventColRef.whereEqualTo("id",mybundle!!.getLong("ID")).get()
        thisEvent.addOnSuccessListener { documents ->
            for (document in documents) {
                val thisEventObject = document.toObject(FullEventObject::class.java)
                Glide.with(view).asBitmap().load(thisEventObject.image).into(view.imageView)
                view.eventName.setText(thisEventObject.title)
                view.introduction.setText(thisEventObject.description)
                view.aboutParagraph.setText(thisEventObject.about)
                view.location.setText(thisEventObject.location)
                view.time.text="${thisEventObject.startDate}"
                view.notifyButton.setText(" Notify Me")
                view.mapButton.setText("Find on Map")
                myList=thisEventObject.associatedSponsors
                initiateAdapter(view,myList,clickListener)
                locName=thisEventObject.location
            }
            thisEvent.addOnFailureListener{exception->
                Log.d("fuck","exception")
            }

        }
        view.notifyButton.setOnClickListener{
            val someValue=context?.let { it1 -> NotificationCompat.Builder(it1,"Channel1") .setSmallIcon(R.drawable.ic_priority_high_black_24dp).setContentTitle("Event Upcoming").setContentText("You requested to be notified for an event starting in 10 minutes").setPriority(NotificationCompat.PRIORITY_DEFAULT)}
            with (NotificationManagerCompat.from(context!!)){
                notify(1,someValue!!.build())
            }

        }
        view.mapButton.setOnClickListener{
           val i= Intent(context,MainActivity::class.java)
            i.putExtra("Location",locName)
            i.flags=(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)

        }
        return view



    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.unregister()
    }
   /* fun createArray(){
        eventName= ArrayList()
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
        eventName.add("Butterfly event")
        eventName.add("Car event")
        eventName.add("Self Help Sem")
        eventName.add("Head first Design Patterns")
        eventName.add("whutwhutwhut")}*/

    fun initiateAdapter(view:View,myList:ArrayList<Long>,clickListener:(Long,Int)->Unit){
        val recView=view.findViewById<RecyclerView>(R.id.eventInfoRecyclerView)
        val manager=LinearLayoutManager(view.context)
        recView.setHasFixedSize(true)
        recView.layoutManager=manager
        val sponsColRef = FirebaseFirestore.getInstance().collection("Sponsors")
        adapter=EventInfoAdapter(view.context,myList,sponsColRef,clickListener)
        recView.adapter=adapter
    }
}