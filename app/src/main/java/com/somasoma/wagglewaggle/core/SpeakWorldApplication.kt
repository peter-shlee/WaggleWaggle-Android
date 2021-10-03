package com.somasoma.wagglewaggle.core

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.somasoma.wagglewaggle.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SpeakWorldApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}