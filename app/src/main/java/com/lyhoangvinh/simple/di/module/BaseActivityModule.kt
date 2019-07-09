package com.lyhoangvinh.simple.di.module

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.di.qualifier.ActivityFragmentManager
import com.lyhoangvinh.simple.utils.NavigatorHelper
import dagger.Module
import dagger.Provides
import lyhoangvinh.com.myutil.glide.loader.SimpleGlideLoader
import lyhoangvinh.com.myutil.navigation.ActivityNavigator
import lyhoangvinh.com.myutil.navigation.Navigator

@Module
abstract class BaseActivityModule<T : AppCompatActivity> {

    @Provides
    @ActivityContext
    fun provideContext(activity: T): Context {
        return activity
    }

    @Provides
    fun provideActivity(activity: T): Activity {
        return activity
    }

    @Provides
    @ActivityFragmentManager
    fun provideFragmentManager(activity: T): FragmentManager {
        return activity.supportFragmentManager
    }

    @Provides
    fun provideNavigator(activity: T): Navigator {
        return ActivityNavigator(activity)
    }

    @Provides
    fun provideNavigatorHelper(navigator: Navigator): NavigatorHelper {
        return NavigatorHelper(navigator)
    }

    @Provides
    fun provideDefaultGlide(activity: T): SimpleGlideLoader {
        return SimpleGlideLoader(activity)
    }

    @Provides
    fun provideLifeCycleOwner(activity: T): LifecycleOwner {
        return activity
    }
}