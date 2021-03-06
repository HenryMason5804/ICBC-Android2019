package com.henrylearns.adapterpractice

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.henrylearns.adapterpractice.dataobjects.FullEventObject
import com.henrylearns.adapterpractice.schedule.ScheduleFragment
import com.henrylearns.adapterpractice.schedule.ScheduleTabFragment
import com.henrylearns.adapterpractice.favourites.SponsorInfoFragment
import com.henrylearns.adapterpractice.favourites.rootFrameLayout
import com.henrylearns.adapterpractice.map.MapFragment
import com.henrylearns.adapterpractice.profile.ProfileFragment
import com.henrylearns.adapterpractice.services.MyFireBaseMessagingService
import java.util.*



class MainActivity : AppCompatActivity(), ScheduleFragment.OnListFragmentInteractionListener {
    public override fun onListFragmentInteraction(item: FullEventObject) {
        val bundle=Bundle()
        bundle.putLong("ID",item.id.toLong())
        bundle.putInt("fragType",3)
        var myFrag=rootFrameLayout()
        myFrag.arguments=bundle
        selectedItemStack.push(1)
        openFragment(myFrag)

    }


    val firstFragment = ScheduleTabFragment()
    val secondFragment = rootFrameLayout()
    var thirdFragment = MapFragment()
    val fourthFragment = ProfileFragment()
    var current: Fragment = SponsorInfoFragment()
    lateinit var selectedItemStack: Stack<Int>





    private lateinit var textMessage: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Henry", "entered mainactivyt")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_navigation_view)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if (intent.extras["Location"] != null) {
            val newBundle=Bundle()
            newBundle.putString("Location",intent.extras["Location"].toString())
            thirdFragment.arguments=newBundle
            selectedItemStack = Stack()
            selectedItemStack.push(2)
            findViewById<BottomNavigationView>(R.id.nav_view).selectedItemId = R.id.navigation_map
        }else if (intent.extras["eventNotifID"]!=null) {
            var newbundle = Bundle()
            newbundle.putInt("fragType",3)
            newbundle.putLong("ID",intent.extras["eventNotifID"] as Long)
            newbundle.putBoolean("fromEventNotif",true)
            secondFragment.arguments=newbundle
            selectedItemStack=Stack()
            selectedItemStack.push(1)
            //openFragment(secondFragment)
           findViewById<BottomNavigationView>(R.id.nav_view).selectedItemId=R.id.navigation_favourites
        }
        else {
            openFragment(firstFragment)
            selectedItemStack = Stack()
            selectedItemStack.push(0)
            findViewById<BottomNavigationView>(R.id.nav_view).selectedItemId = R.id.navigation_home
        }
    }

    override fun onBackPressed() {
        if (selectedItemStack.size <= 1) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Would You Like to Exit the App?'?")
            alertDialog.setPositiveButton("Yes Please", { dialog, which ->
                Thread.sleep(200)
                finishAffinity()
            })
            alertDialog.setNegativeButton("No Thank You", { dialog, which -> return@setNegativeButton })
            alertDialog.show()
            return
        }
        when (current) {
            is rootFrameLayout -> {
                if (intent.extras["eventNotifID"]!=null){
                    secondFragment.arguments=null
                    intent.removeExtra("eventNotifID")
                    openFragment(secondFragment)
                }
                if (!((current as rootFrameLayout).doOnBackPressed())) {
                    super.onBackPressed()
                } else return
            }
            else -> {
                super.onBackPressed()
            }
        }
        updateIcon()

    }

    fun updateIcon() {
        selectedItemStack.pop()
        val recent = selectedItemStack.pop()
        when (recent) {
            0 -> {
                findViewById<BottomNavigationView>(R.id.nav_view).selectedItemId = R.id.navigation_home
            }
            1 -> {
                findViewById<BottomNavigationView>(R.id.nav_view).selectedItemId = R.id.navigation_favourites
            }
            2 -> {
                findViewById<BottomNavigationView>(R.id.nav_view).selectedItemId = R.id.navigation_map
            }
            3->{
                findViewById<BottomNavigationView>(R.id.nav_view).selectedItemId = R.id.navigation_profile
            }
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if (selectedItemStack.size > 3) {
            val recent = selectedItemStack.pop()
            val semirecent = selectedItemStack.pop()
            selectedItemStack.empty()
            selectedItemStack.push(semirecent)
            selectedItemStack.push(recent)
        }
        when (item.itemId) {
            R.id.navigation_home -> {
                openFragment(firstFragment)
                current = firstFragment
                selectedItemStack.push(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favourites -> {

                openFragment(secondFragment)
                current = secondFragment
                selectedItemStack.push(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                openFragment(thirdFragment)
                selectedItemStack.push(2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                openFragment(fourthFragment)
                selectedItemStack.push(4)
            return@OnNavigationItemSelectedListener true}
        }

        false
    }

    public fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        current = fragment
    }
}