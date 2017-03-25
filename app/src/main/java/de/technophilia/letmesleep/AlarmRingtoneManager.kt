package de.technophilia.letmesleep

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build

/**
 * Created by alainsarti on 25/03/2017.
 */

class AlarmRingtoneManager(val context: Context) {
    private var alarmRingtone: Ringtone? = null

    fun getAlarmUri(): Uri {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    }

    fun getAlarmRingtone(): Ringtone {
        return RingtoneManager.getRingtone(context.applicationContext, getAlarmUri())
    }

    fun playAlarm() {
        alarmRingtone = getAlarmRingtone()
        if(alarmRingtone != null) {
            setAlarmType()
            alarmRingtone!!.play()
        }
    }

    fun stopAlarm() {
        if(alarmRingtone != null && alarmRingtone!!.isPlaying) {
            alarmRingtone!!.stop()
        }
    }

    private fun setAlarmType() {
        if (Build.VERSION.SDK_INT >= 21)
            alarmRingtone!!.audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
        else
            alarmRingtone!!.streamType = AudioManager.STREAM_ALARM
    }
}