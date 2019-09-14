package com.henrylearns.adapterpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var i: Intent =Intent(this,SignIn::class.java)
        startActivity(i)
        finish()
    }
}
