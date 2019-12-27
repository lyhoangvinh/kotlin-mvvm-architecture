package com.lyhoangvinh.simple.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.avgle.Category
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.data.entities.comic.ImageAll
import com.lyhoangvinh.simple.ui.features.avg.main.AvgActivity
import com.lyhoangvinh.simple.ui.features.avg.detail.DetailActivity
import com.lyhoangvinh.simple.ui.features.avg.main.collection.CollectionFragment
import com.lyhoangvinh.simple.ui.features.avg.search.paging.SearchPagedActivity
import com.lyhoangvinh.simple.ui.features.avg.main.video.VideoFragment
import com.lyhoangvinh.simple.ui.features.splash.SplashActivity
import com.lyhoangvinh.simple.ui.features.comic.detail.ImageDetailActivity
import com.lyhoangvinh.simple.ui.features.comic.testpaging.ComicPagingActivity
import com.lyhoangvinh.simple.ui.features.comicavg.ComicAvgActivity
import com.lyhoangvinh.simple.ui.features.setting.SettingActivity
import lyhoangvinh.com.myutil.navigation.ActivityNavigator
import lyhoangvinh.com.myutil.navigation.FragmentNavigator
import lyhoangvinh.com.myutil.navigation.Navigator

class NavigatorHelper(private var mNavigator: Navigator) {

    fun NavigatorHelper(mNavigator: FragmentNavigator): NavigatorHelper {
        this.mNavigator = mNavigator
        return this
    }

    fun NavigatorHelper(mNavigator: ActivityNavigator): NavigatorHelper {
        this.mNavigator = mNavigator
        return this
    }

    fun navigateIssusActivity() {
        mNavigator.startActivity(ComicPagingActivity::class.java)
        mNavigator.finishActivity()
    }

    fun navigateAvgleActivity() {
        Handler().postDelayed({
            mNavigator.startActivity(AvgActivity::class.java)
            mNavigator.finishActivity()
        }, 500L)
    }

    fun navigateDetailActivity(url: String) {
        mNavigator.startActivity(DetailActivity::class.java) { intent ->
            intent.putExtra(
                Constants.EXTRA_DATA,
                url
            )
        }
    }

    fun navigateFromType(type: String, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), type)
        mNavigator.startActivity(intent)
    }

    fun navigateVideosFragment() {
        mNavigator.replaceFragmentAndAddToBackStack(
            R.id.container,
            VideoFragment().addAnimations(),
            null,
            null
        )
    }

    fun navigateVideosFragment(category: Category?) {
        val collectionFragment = VideoFragment().addAnimations()
        if (category != null) {
            val bundle = Bundle()
            bundle.putParcelable(Constants.EXTRA_DATA, category)
            collectionFragment.arguments = bundle
            mNavigator.replaceFragmentAndAddToBackStack(
                R.id.container,
                collectionFragment,
                bundle,
                null
            )
        } else {
            mNavigator.replaceFragmentAndAddToBackStack(
                R.id.container,
                collectionFragment,
                null,
                null
            )
        }
    }

    fun navigateVideosFragment(collection: Collection?) {
        val collectionFragment = VideoFragment().addAnimations()
        if (collection != null) {
            val bundle = Bundle()
            bundle.putParcelable(Constants.EXTRA_DATA, collection)
            collectionFragment.arguments = bundle
            mNavigator.replaceFragmentAndAddToBackStack(
                R.id.container,
                collectionFragment,
                bundle,
                null
            )
        } else {
            mNavigator.replaceFragmentAndAddToBackStack(
                R.id.container,
                collectionFragment,
                null,
                null
            )
        }
    }

    fun navigateCollectionFragment() {
        mNavigator.replaceFragmentAndAddToBackStack(
            R.id.container,
            CollectionFragment().addAnimations(),
            null,
            null
        )
    }

    fun navigateSearchActivity(activity: Activity) {
        //live database
//        activity.startActivityTransition(SearchActivity::class.java, false)

        //live data api
        activity.startActivityTransition(SearchPagedActivity::class.java, false)
    }

    fun navigateComicAvgActivity() {
        mNavigator.startActivity(ComicAvgActivity::class.java)
        mNavigator.finishActivity()
    }

    fun navigateSettingActivity() {
        mNavigator.startActivity(SettingActivity::class.java)
    }

    fun navigateImageDetailActivity(imageAll: ImageAll) {
        mNavigator.startActivity(ImageDetailActivity::class.java) {
            it.putExtra(Constants.EXTRA_DATA, imageAll)
        }
    }

    fun navigateSplashActivity(activity: Activity) {
        mNavigator.startActivity(SplashActivity::class.java){
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
        }
        activity.finishAffinity()
    }
}