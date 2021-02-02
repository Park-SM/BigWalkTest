package com.smparkworld.bigwalktest

import android.app.Application
import com.smparkworld.bigwalktest.di.AppComponent
import com.smparkworld.bigwalktest.di.DaggerAppComponent

class BigWalkApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
    }

}