package com.ferrero.a

import android.app.Application
import android.content.Context
import com.ferrero.a.util.Util.ONESIGNAL_APP_ID
import com.ferrero.a.util.Util.instId
import com.ferrero.a.util.Util.mtKey
import com.ferrero.a.util.Util.myId
import com.kochava.tracker.Tracker
import com.my.tracker.MyTracker
import com.onesignal.OneSignal
import io.branch.referral.Branch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level
import java.util.*

class MonClass: Application() {

    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        Branch.enableTestMode()
        // Branch object initialization
        Branch.getAutoInstance(this)

        val shP = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val settings = getSharedPreferences("PREFS_NAME", 0)

        val trackerParams = MyTracker.getTrackerParams()
        val trackerConfig = MyTracker.getTrackerConfig()
        val instID = MyTracker.getInstanceId(this)
        trackerConfig.isTrackingLaunchEnabled = true
        val IDIN = UUID.randomUUID().toString()

        if (settings.getBoolean("my_first_time", true)) {
            trackerParams.setCustomUserId(IDIN)
            shP.edit().putString(myId, IDIN).apply()
            shP.edit().putString(instId, instID).apply()
            settings.edit().putBoolean("my_first_time", false).apply()
        } else {
            val shIDIN = shP.getString(myId, IDIN)
            trackerParams.setCustomUserId(shIDIN)
        }
        MyTracker.initTracker(mtKey, this)

        GlobalContext.startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MonClass)
            modules(
                listOf(
                    viewModelModule, appModule
                )
            )
        }
    }
}