package com.lyhoangvinh.simple.utils

import androidx.lifecycle.MutableLiveData

/**
 * Thread-safe live data to resolve this issue: when perform  {@link LiveData#setValue(Object)}
 * not in main Thread. (almost case is in testing)
 */

class SafeMutableLiveData<T> : MutableLiveData<T>() {
    override fun setValue(value: T) {
        try {
            super.setValue(value)
        } catch (e: Exception) {
            // if we can't set value due to not in main thread, must call post value instead
            super.postValue(value)
        }
    }

    override fun getValue(): T? {
        return super.getValue()
    }
}