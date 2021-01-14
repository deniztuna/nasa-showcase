package com.appcent.nasashowcase.manager

import android.content.Context
import com.appcent.nasashowcase.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class NasaRemoteConfigManager {
    var firebaseRemoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    var solDay: Long = 1000
    var apiKey: String = "KvbI8LP5aTG8TYyJqtSk7IDxNYakXCjJdUh5PdMw"

    fun initConfig() {
        // Try to get 2 fetch req in each hour, https://firebase.google.com/docs/remote-config/use-config-android#throttling and https://medium.com/@elye.project/least-firebase-remote-config-cache-duration-an-experiment-644c0b836ca
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 1 // For this showcase purpose, 1800 otherwise
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        setSolDayConfig()
        setApiKeyConfig()

        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                try {
                    Timber.e("NasaRemoteConfigManager::remote config fetched value: ${firebaseRemoteConfig.getString(SOL_DAY)}")
                    setSolDayConfig()
                    setApiKeyConfig()
                } catch (exception: Exception) {
                    Timber.e("NasaRemoteConfigManager:: exception: ${exception.message}")
                }
            } else {
                Timber.e("Task failed: ${task.exception?.message}")
            }
        }
    }

    private fun setSolDayConfig() {
        try {
            solDay = firebaseRemoteConfig.getLong(SOL_DAY)
            Timber.d("NasaRemoteConfigManager:: solDay $solDay")
        } catch (e: Exception) {
            Timber.e("Exception occured: ${e.message}")
        }
    }

    private fun setApiKeyConfig() {
        try {
            apiKey = firebaseRemoteConfig.getString(API_KEY)
            Timber.e("NasaRemoteConfigManager:: apiKey $apiKey")
        } catch (e: Exception) {
            Timber.e("Exception occured: ${e.message}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NasaRemoteConfigManager? = null
        fun getInstance(): NasaRemoteConfigManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildConfigManager().also { INSTANCE = it }
            }

        private fun buildConfigManager() = NasaRemoteConfigManager()

        private const val SOL_DAY = "solDay"
        private const val API_KEY = "apiKey"
    }
}