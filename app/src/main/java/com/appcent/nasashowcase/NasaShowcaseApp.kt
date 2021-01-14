package com.appcent.nasashowcase

import android.app.Application
import com.appcent.nasashowcase.manager.NasaRemoteConfigManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class NasaShowcaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        NasaRemoteConfigManager.getInstance().initConfig()
    }
}