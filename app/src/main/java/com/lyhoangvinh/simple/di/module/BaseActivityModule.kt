package com.lyhoangvinh.simple.di.module

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.di.qualifier.ActivityFragmentManager
import com.lyhoangvinh.simple.utils.NavigatorHelper
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerAppCompatActivity
import lyhoangvinh.com.myutil.glide.loader.SimpleGlideLoader
import lyhoangvinh.com.myutil.navigation.ActivityNavigator
import lyhoangvinh.com.myutil.navigation.Navigator

@Module
abstract class BaseActivityModule<T : DaggerAppCompatActivity> {

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