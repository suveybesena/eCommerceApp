package com.example.capstoneproject.presentation.basket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.capstoneproject.R
import com.example.capstoneproject.common.Constant.BASKET_REMINDER_DESC
import com.example.capstoneproject.common.Constant.BASKET_REMINDER_TITLE
import com.example.capstoneproject.common.Constant.CHANNEL_ID
import com.example.capstoneproject.common.Constant.CHANNEL_INTRO
import com.example.capstoneproject.common.Constant.CHANNEL_NAME

class RunWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        createNotification()
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification() {
        val builder: NotificationCompat.Builder
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = CHANNEL_ID
        val channelName = CHANNEL_NAME
        val channelIntroduction = CHANNEL_INTRO
        val channelPriority = NotificationManager.IMPORTANCE_HIGH

        var notificationChannel: NotificationChannel? = notificationManager
            .getNotificationChannel(channelId)

        if (notificationChannel == null) {
            notificationChannel = NotificationChannel(channelId, channelName, channelPriority)
            notificationChannel.description = channelIntroduction
            notificationManager.createNotificationChannel(notificationChannel)
        }

        builder = NotificationCompat.Builder(applicationContext, channelId)
        builder
            .setContentTitle(BASKET_REMINDER_TITLE)
            .setContentText(BASKET_REMINDER_DESC)
            .setSmallIcon(R.drawable.ic_notifications)
            .setAutoCancel(true)
        notificationManager.notify(1, builder.build())
    }
}