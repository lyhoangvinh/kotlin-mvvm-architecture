package com.lyhoangvinh.simple.utils

import com.lyhoangvinh.simple.ui.features.avg.AgvActivity
import lyhoangvinh.com.myutil.navigation.ActivityNavigator
import lyhoangvinh.com.myutil.navigation.FragmentNavigator
import lyhoangvinh.com.myutil.navigation.Navigator

class NavigatorHelper(private var mNavigator: Navigator) {

    fun NavigatorHelper(mNavigator: FragmentNavigator) {
        this.mNavigator = mNavigator
    }

    fun NavigatorHelper(mNavigator: ActivityNavigator) {
        this.mNavigator = mNavigator
    }

    fun NavigatorHelper(mNavigator: Navigator) {
        this.mNavigator = mNavigator
    }

    fun navigateAvgleActivity() {
//        mNavigator.startActivityWithTransition(AgvActivity::class.java, PlainConsumer { }, true, true)
        mNavigator.startActivity(AgvActivity::class.java)
        mNavigator.finishActivity()
    }
}