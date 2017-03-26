package de.technophilia.letmesleep

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by alainsarti on 25/03/2017.
 */

class AlarmPlaySoundPublisher(): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        playAlarm(context)
        showStopActivity(context)
    }

    private fun showStopActivity(context: Context) {
        val intent = Intent(context, StopActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private fun playAlarm(context: Context) {
        val alarmRingtoneManager = AlarmRingtoneManager(context)
        alarmRingtoneManager.playAlarm()
    }

}