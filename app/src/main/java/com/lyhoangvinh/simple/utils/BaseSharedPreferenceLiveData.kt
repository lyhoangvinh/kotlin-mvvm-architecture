package com.lyhoangvinh.simple.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.lyhoangvinh.simple.data.SharedPrefs

abstract class BaseSharedPreferenceLiveData<T>(val sharedPrefs: SharedPrefs?, val key: String, private val defValue: T) : LiveData<T>() {

    abstract fun getValueFromPreferences(key: String, defValue: T): T

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener{
        _, key ->
        if (this.key == key){
            value = getValueFromPreferences(key, defValue)
        }
    }
    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs?.mSharedPreferences?.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs?.mSharedPreferences?.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}