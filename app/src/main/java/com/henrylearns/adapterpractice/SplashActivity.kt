package com.henrylearns.adapterpractice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SplashActivity : AppCompatActivity() {
    private fun createNotificationChannel(context: Context) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Event Upcoming"
            val descriptionText = "PushNotifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("PushNotificationChannel", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService( Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.extras!=null){
//            if (intent.extras["Body"]!=null){
//                Log.d("debuggingStartingIntent",intent.extras["Body"].toString())
                val i= Intent(Intent.ACTION_VIEW)
                i.data= Uri.parse("https://www.facebook.com/QueensICBC/")
                startActivity(i)
            }//}
        createNotificationChannel(this)
        var i: Intent =Intent(this,SignIn::class.java)
        startActivity(i)
        finish()
    }
}
