package com.lyhoangvinh.simple
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.lyhoangvinh.simple.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this)

    private var mDeviceWidth = 0

    companion object {
        private lateinit var instance: MyApplication

        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val displayMetrics = DisplayMetrics()
        val windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay?.getMetrics(displayMetrics)
        mDeviceWidth = displayMetrics.widthPixels
    }

    fun getDeviceWidth(): Int {
        return mDeviceWidth
    }
}