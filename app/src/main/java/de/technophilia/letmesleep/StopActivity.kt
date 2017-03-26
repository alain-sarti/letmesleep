package de.technophilia.letmesleep

import android.os.Bundle
import android.support.v4.view.MotionEventCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.WindowManager
import butterknife.ButterKnife
import butterknife.OnClick

class StopActivity : AppCompatActivity() {
    @OnClick(R.id.btnStopAlarm)
    fun stopAlarm() {
        val alarmRingtoneManager = AlarmRingtoneManager(applicationContext)
        alarmRingtoneManager.stopAlarm()

        this.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop)

        ButterKnife.bind(this)

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = MotionEventCompat.getActionMasked(event)
        when (action) {
            MotionEvent.ACTION_MOVE -> {
                Log.d("TOUCH EVENT", "Action was MOVE")
                stopAlarm()
                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }
}
