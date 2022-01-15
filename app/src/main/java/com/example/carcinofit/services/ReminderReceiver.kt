package com.example.carcinofit.services

import android.app.*
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import com.example.carcinofit.R
import com.example.carcinofit.ui.MainActivity

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        val id = intent.extras?.getInt(NOTIFICATION_ID) ?: 0
        val intent1 = Intent(context, MainActivity::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val taskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addParentStack(MainActivity::class.java)
        taskStackBuilder.addNextIntent(intent1)
        val intent2 = taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationChannel = NotificationChannel(
            "carcinofit_channel",
            "Reminders",
            IMPORTANCE_HIGH
        )
        val notification = Notification.Builder(context, notificationChannel.id)
            .setContentTitle("Your Fitness Coach")
            .setContentText("It is time to get Moving!")
            .setSmallIcon(Icon.createWithResource(context, R.drawable.ic_icon))
            .setLargeIcon(Icon.createWithResource(context, R.drawable.ic_exercise_history_icon))
            .setChannelId(notificationChannel.id)
            .setContentIntent(intent2)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(id, notification)
    }

    companion object {
        const val NOTIFICATION_ID = "notification_id"
    }
}