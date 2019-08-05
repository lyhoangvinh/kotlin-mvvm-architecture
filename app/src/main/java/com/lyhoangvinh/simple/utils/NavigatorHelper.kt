package com.lyhoangvinh.simple.utils

import android.content.Intent
import android.net.Uri
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.ui.features.avg.main.AgvActivity
import com.lyhoangvinh.simple.ui.features.avg.detail.DetailActivity
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicActivity
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

    fun navigateIssusActivity(){
        mNavigator.startActivity(ComicActivity::class.java)
    }

    fun navigateAvgleActivity() {
//        mNavigator.startActivityWithTransition(AgvActivity::class.java, PlainConsumer { }, true, true)
        mNavigator.startActivity(AgvActivity::class.java)
        mNavigator.finishActivity()
    }

    fun navigateDetailActivity(url: String) {
        mNavigator.startActivity(DetailActivity::class.java) { intent -> intent.putExtra(Constants.EXTRA_DATA, url) }
    }

    fun navigateFromType(type: String, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), type)
        mNavigator.startActivity(intent)
    }
}