package com.henrylearns.adapterpractice

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class Alarm_receiver: BroadcastReceiver() {



    override fun onReceive(context: Context?, intent: Intent?) {
        var time=intent!!.getLongExtra("Time",0L)
        var mcalendar= Calendar.getInstance()
        var eventName=intent.getStringExtra("eventName")
        var id =intent.getLongExtra("ID",0).toInt()
        mcalendar.timeInMillis=time
        var builder = NotificationCompat.Builder(context!!, "Channel1")
                .setSmallIcon(R.drawable.main_loading_icon)
                .setContentTitle("Upcoming Event")
                .setContentText("$eventName: ${mcalendar.time}")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val startUpIntent=Intent(context,SignIn::class.java)
        startUpIntent.putExtra("eventNotifID",id.toLong())
        var nPI=PendingIntent.getActivity(context,id,startUpIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(nPI)
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(6969, builder.build())
        }

        Log.d("CheckOnRecieve","WE entered onREceive time is $time")
    }

}