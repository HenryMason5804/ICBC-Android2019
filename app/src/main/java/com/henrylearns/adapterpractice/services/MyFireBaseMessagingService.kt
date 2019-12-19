package com.henrylearns.adapterpractice.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.FirebaseMessagingService
import com.henrylearns.adapterpractice.MainActivity
import com.henrylearns.adapterpractice.R


public class MyFireBaseMessagingService:FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0);
        Log.d("hey","something")

    sendNotification(p0)

}

private fun sendNotification(remoteMessage: RemoteMessage) {
    val notification = remoteMessage.getNotification();
    val i= Intent(Intent.ACTION_VIEW)
    i.data=Uri.parse("https://www.facebook.com/QueensICBC/")
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    val pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
   val defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    val notificationBuilder =  NotificationCompat.Builder(this,"PushNotificationChannel")
        .setSmallIcon(R.drawable.ic_priority_high_black_24dp)
        .setColor(getColor(R.color.colorAccent))
        .setContentTitle(notification?.title)
        .setContentText(notification?.body)
        .setAutoCancel(false)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent)
    notificationBuilder.setContentIntent(pendingIntent)
    with(NotificationManagerCompat.from(this)) {
        // notificationId is a unique int for each notification that you must define
        notify(100151350, notificationBuilder.build())
    }

}


override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("refreshedIDToken",p0)
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", p0).apply();

    }

    companion object MyFireBaseMessagingService{
        public fun getToken(context: Context):String {
            return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
        }
    }
}
