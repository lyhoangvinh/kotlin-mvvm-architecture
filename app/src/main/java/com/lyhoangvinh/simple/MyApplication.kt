package com.lyhoangvinh.simple

import android.app.Activity
import android.app.Application
import com.lyhoangvinh.simple.di.AppInjector
import com.lyhoangvinh.simple.di.component.AppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector {

    companion object {
        private lateinit var instance: MyApplication

        fun getInstance() = instance
    }

    lateinit var sAppComponent: AppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        instance = this
        //initAppComponent
        sAppComponent = AppInjector(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}