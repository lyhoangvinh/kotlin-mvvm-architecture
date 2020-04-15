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

class NavigatorHelper {
    private var navigator: Navigator
    constructor(navigator: Navigator) { this.navigator = navigator }
    constructor(navigator: FragmentNavigator) { this.navigator = navigator }
    constructor(navigator: ActivityNavigator) { this.navigator = navigator }

    fun navigateIssusActivity() {
        navigator.startActivity(ComicPagingActivity::class.java)
        navigator.finishActivity()
    }

    fun navigateAvgleActivity() {
        Handler().postDelayed({
            navigator.startActivity(AvgActivity::class.java)
            navigator.finishActivity()
        }, 500L)
    }

    fun navigateDetailActivity(url: String) {
        navigator.startActivity(DetailActivity::class.java) { intent ->
            intent.putExtra(
                Constants.EXTRA_DATA,
                url
            )
        }
    }

    fun navigateFromType(type: String, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), type)
        navigator.startActivity(intent)
    }

    fun navigateVideosFragment() {
        navigator.replaceFragmentAndAddToBackStack(
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
            navigator.replaceFragmentAndAddToBackStack(
                R.id.container,
                collectionFragment,
                bundle,
                null
            )
        } else {
            navigator.replaceFragmentAndAddToBackStack(
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
            navigator.replaceFragmentAndAddToBackStack(
                R.id.container,
                collectionFragment,
                bundle,
                null
            )
        } else {
            navigator.replaceFragmentAndAddToBackStack(
                R.id.container,
                collectionFragment,
                null,
                null
            )
        }
    }

    fun navigateCollectionFragment() {
        navigator.replaceFragmentAndAddToBackStack(
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
        navigator.startActivity(ComicAvgActivity::class.java)
        navigator.finishActivity()
    }

    fun navigateSettingActivity() {
        navigator.startActivity(SettingActivity::class.java)
    }

    fun navigateImageDetailActivity(imageAll: ImageAll) {
        navigator.startActivity(ImageDetailActivity::class.java) {
            it.putExtra(Constants.EXTRA_DATA, imageAll)
        }
    }

    fun navigateSplashActivity(activity: Activity) {
        navigator.startActivity(SplashActivity::class.java){
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
        }
        activity.finishAffinity()
    }
}