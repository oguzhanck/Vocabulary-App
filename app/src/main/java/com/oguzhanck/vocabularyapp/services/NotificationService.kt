package com.oguzhanck.vocabularyapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.oguzhanck.vocabularyapp.R
import com.oguzhanck.vocabularyapp.view.activities.NotificationActivity
import java.util.*


class NotificationService : Service() {
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private val notificationChannelID = "101"
    private val defaultNotificationChannelID = "default"

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val word = intent?.extras?.getString("word")

        if (word != null) {
            startTimer(word)
        }

        return START_STICKY
    }

    override fun onCreate() {
    }

    override fun onDestroy() {
        stopTimerTask()
        super.onDestroy()
    }

    //we are going to use a handler to be able to run in our TimerTask
    val handler: Handler = Handler()

    private fun startTimer(word: String) {
        timer = Timer()
        initializeTimerTask(word)
        timer!!.schedule(timerTask, 5000, 43200000) //

    }

    private fun stopTimerTask() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    private fun initializeTimerTask(word: String) {
        timerTask = object : TimerTask() {
            override fun run() {
                handler.post(Runnable { createNotification(word) })
            }
        }
    }

    private fun createNotification(word: String) {
        // FOR MEMORIZED //
        val intentMemorized = Intent(applicationContext, NotificationActivity::class.java)
        intentMemorized.putExtra("word", word)
        intentMemorized.putExtra("status", "memorized")
        val intentTransitionMemorized =
            PendingIntent.getActivity(this, 0, intentMemorized, PendingIntent.FLAG_UPDATE_CURRENT)

        // FOR REMIND LATER //
        val intentRemind = Intent(applicationContext, NotificationActivity::class.java)
        intentRemind.putExtra("word", word)
        intentRemind.putExtra("status", "remind")
        val intentTransitionRemind =
            PendingIntent.getActivity(this, 1, intentRemind, PendingIntent.FLAG_UPDATE_CURRENT)

        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(applicationContext, defaultNotificationChannelID)
        mBuilder.setContentTitle(word)
        mBuilder.setContentText("Hey! You Should See This Word.")
        mBuilder.setSmallIcon(R.drawable.ic_baseline_article_24)
        mBuilder.addAction(
            R.drawable.ic_baseline_article_24,
            "Memorized",
            intentTransitionMemorized
        )
        mBuilder.addAction(
            R.drawable.ic_launcher_foreground,
            "Remind Later",
            intentTransitionRemind
        )
        mBuilder.setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                notificationChannelID,
                "NOTIFICATION_CHANNEL_NAME",
                importance
            )
            mBuilder.setChannelId(notificationChannelID)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        mNotificationManager.notify(System.currentTimeMillis().toInt(), mBuilder.build())
    }
}