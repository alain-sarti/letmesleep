package de.technophilia.letmesleep

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.text.format.DateFormat
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import java.util.*

class MainActivity : AppCompatActivity() {
    @BindView(R.id.etHours)
    lateinit var etHours: EditText

    @BindView(R.id.etOffset)
    lateinit var etOffset: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
    }

    @OnClick(R.id.btnAddAlarm)
    fun addAlarm() {
        var time = (1000 * 60 * 60 * 8).toLong()
        var offset = (1000 * 60 * 15).toLong()
        if (!TextUtils.isEmpty(etHours.text.toString())) {
            time = java.lang.Long.parseLong(etHours.text.toString()) * 1000 * 60 * 60
        }
        if (!TextUtils.isEmpty(etOffset.text.toString())) {
            offset = java.lang.Long.parseLong(etOffset.text.toString()) * 1000 * 60
        }
        scheduleNotification(offset, time)
    }

    private fun scheduleNotification(offset: Long, time: Long) {
        val notificationPendingIntent = prepareAlarmIntent(offset, time)

        val notification = getNotification(String.format(getString(R.string.notification_alarm_time), DateFormat.format("hh:mm", getAlarmTime(offset, time))), notificationPendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(Settings.Settings.NOTIFICATION_ID, notification)

        Toast.makeText(this, this.getString(R.string.toast_alarm_set, DateFormat.format("hh:mm", getAlarmTime(offset, time))), Toast.LENGTH_LONG).show()
    }

    private fun getNotification(content: String, pendingIntent: PendingIntent): Notification {
        val builder = Notification.Builder(this)
        builder.setContentTitle("Let Me Sleep Notification")
        builder.setContentIntent(pendingIntent)
        builder.setContentText(content)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        return builder.build()
    }

    private fun getAlarmTime(delay: Long, time: Long): Date {
        return Date(System.currentTimeMillis() + delay + time)
    }

    private fun prepareAlarmIntent(delay: Long, time: Long): PendingIntent {
        val alarmIntent = Intent(this, AlarmPlaySoundPublisher::class.java)
        val notificationIntent = Intent(this, AlarmNotificationReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(applicationContext, Settings.Settings.ALARM_ID, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationPendingIntent = PendingIntent.getBroadcast(applicationContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val futureInMillis = SystemClock.elapsedRealtime() + delay + time
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, alarmPendingIntent)

        return notificationPendingIntent
    }
}
