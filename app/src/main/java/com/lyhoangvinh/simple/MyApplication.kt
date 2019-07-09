package com.lyhoangvinh.simple

import android.app.Activity
import android.app.Application
import com.lyhoangvinh.simple.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector {

    companion object {
        private lateinit var instance: MyApplication

        fun getInstance() = instance
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        instance = this
        //initAppComponent
         AppInjector.init(this)
     }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

}