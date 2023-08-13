package com.space.aviatory

import android.content.Context
import com.onesignal.OneSignal

object ро {
     fun initOneConfig(ONESIGNAL_APP_ID:String,context:Context) {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(context)

        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}