package de.technophilia.letmesleep

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Created by alainsarti on 25/03/2017.
 */

class AlarmNotificationReceiver(): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        cancelAlarm(context)
        cancelNotification(context)

        Toast.makeText(context, "Alarm cancelled!", Toast.LENGTH_LONG).show()
    }

    private fun getAlarmPendingIntent(context: Context?): PendingIntent {
        val alarmIntent = Intent(context, AlarmPlaySoundPublisher::class.java)
        return PendingIntent.getBroadcast(context!!.applicationContext, Settings.Settings.ALARM_ID, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun cancelAlarm(context: Context?) {
        var alarmPendingIntent: PendingIntent = getAlarmPendingIntent(context)
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(alarmPendingIntent)
        alarmPendingIntent.cancel()
    }

    private fun cancelNotification(context: Context?) {
        val manager: NotificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(Settings.Settings.NOTIFICATION_ID)
    }
}