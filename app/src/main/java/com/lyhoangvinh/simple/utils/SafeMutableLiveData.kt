package com.lyhoangvinh.simple.utils

import android.arch.lifecycle.LiveData

/**
 * Thread-safe live data to resolve this issue: when perform  {@link LiveData#setValue(Object)}
 * not in main Thread. (almost case is in testing)
 */

class SafeMutableLiveData<T> : LiveData<T>() {
    public override fun setValue(value: T) {
        try {
            super.setValue(value)
        } catch (e: Exception) {
            // if we can't set value due to not in main thread, must call post value instead
            super.postValue(value)
        }
    }
}