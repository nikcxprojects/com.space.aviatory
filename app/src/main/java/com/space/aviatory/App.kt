package com.space.aviatory

import android.app.Application
import com.space.aviatory.ро.initOneConfig

class App : Application()  {

    private val ONESIGNAL_APP_ID = "cf4ec57d-b7fc-4aef-98d5-9f1bc0a13c70"

    override fun onCreate() {
        super.onCreate()
        initOneConfig(ONESIGNAL_APP_ID,this)
    }

}