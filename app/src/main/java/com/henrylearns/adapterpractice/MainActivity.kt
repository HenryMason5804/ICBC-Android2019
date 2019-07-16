package com.henrylearns.adapterpractice

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.henrylearns.adapterpractice.SponsorFragment
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    val firstFragment=rootFrameLayout()
    val secondFragment=MapFragment()
    val thirdFragment=SponsorInfoFragment()
    var current:Fragment=SponsorInfoFragment()
    lateinit var selectedItemStack: Stack<Int>


    private lateinit var textMessage: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Henry","entered mainactivyt")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_navigation_view)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        openFragment(rootFrameLayout())
        selectedItemStack=Stack()
        selectedItemStack.push(0)
    }

    override fun onBackPressed() {
        if (selectedItemStack.size <=1){
            val alertDialog= AlertDialog.Builder(this)
            alertDialog.setTitle("Ar' ya exitin'?")
            alertDialog.setPositiveButton("GET ME OUT",{dialog,which->
                Toast.makeText(this,"GOODBYE FOOL",Toast.LENGTH_SHORT).show()
                Thread.sleep(200)
                finishAffinity()
            })
            alertDialog.setNegativeButton("We OK",{dialog,which->return@setNegativeButton})
            alertDialog.show()

            return
        }
        when  (current){
            is rootFrameLayout->{
                Log.d("Henry","enteredfirstFragment")
                if (!(current as rootFrameLayout).doOnBackPressed())
                {updateIcon()
                    super.onBackPressed()}
                else return }
            else -> {
            super.onBackPressed()}
        }
        updateIcon()

        }
fun updateIcon(){
    selectedItemStack.pop()
    val recent=selectedItemStack.pop()
    when (recent){
        0->
        {findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.nav_view).selectedItemId=R.id.navigation_home}
        1->{
            findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.nav_view).selectedItemId=R.id.navigation_favourites}
        2 ->
        {findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.nav_view).selectedItemId=R.id.navigation_map}
    }
}
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if (selectedItemStack.size>3){
        val recent=selectedItemStack.pop()
        val semirecent=selectedItemStack.pop()
        selectedItemStack.empty()
        selectedItemStack.push(semirecent)
        selectedItemStack.push(recent)}
        when (item.itemId) {
            R.id.navigation_home -> {
                openFragment(firstFragment)
                current=firstFragment
                selectedItemStack.push(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favourites -> {
                openFragment(secondFragment)
                current=secondFragment
                selectedItemStack.push(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                openFragment(thirdFragment)
                selectedItemStack.push(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        current=fragment
    }
}
