package com.lyhoangvinh.simple.utils


import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.SharedPrefs
import javax.inject.Inject

class OptionSharedPreferenceLiveData @Inject constructor(sharedPrefs: SharedPrefs) :
    BaseSharedPreferenceLiveData<Int>(
        sharedPrefs,
        Constants.OPTIONS, 0
    ) {
    override fun getValueFromPreferences(key: String, defValue: Int) =
        sharedPrefs!![Constants.OPTIONS, Int::class.java]

}