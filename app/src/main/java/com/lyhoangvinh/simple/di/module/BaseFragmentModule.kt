package com.lyhoangvinh.simple.di.module

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.di.qualifier.ActivityFragmentManager
import com.lyhoangvinh.simple.di.qualifier.ChildFragmentManager
import com.lyhoangvinh.simple.utils.NavigatorHelper
import dagger.Module
import dagger.Provides
import lyhoangvinh.com.myutil.navigation.ChildFragmentNavigator
import lyhoangvinh.com.myutil.navigation.FragmentNavigator

/**
 * Module for fragment component, modified (reference: Patrick Löwenstein)
 *
 * NOTE: all method must be public (since children module might not in same package,
 * thus dagger can't generate inherit method
 *
 */
@Module
abstract class BaseFragmentModule<T : Fragment> {

    @Provides
    @ActivityContext
    fun provideContext(fragment: T): Context {
        return fragment.context!!
    }

    @Provides
    @ChildFragmentManager
    fun provideChildFragmentManager(fragment: T): FragmentManager {
        return fragment.childFragmentManager
    }

    @Provides
    @ActivityFragmentManager
    fun provideActivityFragmentManager(activity: FragmentActivity): FragmentManager {
        return activity.supportFragmentManager
    }

    @Provides
    fun provideFragmentNavigator(fragment: T): FragmentNavigator {
        return ChildFragmentNavigator(fragment)
    }

    @Provides
    fun provideActivity(fragment: T): FragmentActivity? {
        return fragment.activity
    }

    @Provides
    fun provideLifeCycleOwner(fragment: T): LifecycleOwner {
        return fragment
    }

    @Provides
    fun navigatorHelper(navigator: FragmentNavigator): NavigatorHelper {
        return NavigatorHelper(navigator)
    }
}
