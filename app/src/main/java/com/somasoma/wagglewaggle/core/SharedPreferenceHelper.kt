package com.somasoma.wagglewaggle.core

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {
    fun getString(key: String, defaultValue: String? = null) =
        sharedPreferences.getString(key, defaultValue)

    fun getInt(key: String, defaultValue: Int = -1) = sharedPreferences.getInt(key, defaultValue)

    fun getLong(key: String, defaultValue: Long = -1) = sharedPreferences.getLong(key, defaultValue)

    fun getBoolean(key: String, defaultValue: Boolean = false) =
        sharedPreferences.getBoolean(key, defaultValue)

    fun getFloat(key: String, defaultValue: Float = -1f) =
        sharedPreferences.getFloat(key, defaultValue)

    fun getStringSet(key: String, defaultValue: Set<String>? = null) =
        sharedPreferences.getStringSet(key, defaultValue)

    fun putString(key: String, value: String) =
        sharedPreferences.edit().putString(key, value).apply()

    fun putInt(key: String, value: Int) = sharedPreferences.edit().putInt(key, value).apply()

    fun putLong(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).apply()

    fun putBoolean(key: String, value: Boolean) =
        sharedPreferences.edit().putBoolean(key, value).apply()

    fun putFloat(key: String, value: Float) = sharedPreferences.edit().putFloat(key, value).apply()

    fun putStringSet(key: String, value: Set<String>) =
        sharedPreferences.edit().putStringSet(key, value).apply()
}