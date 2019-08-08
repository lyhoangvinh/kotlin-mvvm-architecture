package com.lyhoangvinh.simple.utils

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.avgle.Category
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.ui.features.avg.main.AgvActivity
import com.lyhoangvinh.simple.ui.features.avg.detail.DetailActivity
import com.lyhoangvinh.simple.ui.features.avg.main.collection.CollectionFragment
import com.lyhoangvinh.simple.ui.features.avg.main.video.VideoFragment
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

    fun navigateVideosFragment() {
        mNavigator.replaceFragmentAndAddToBackStack(R.id.container, VideoFragment().addAnimations(), null, null)
    }

    fun navigateVideosFragment(category: Category?) {
        val collectionFragment = VideoFragment().addAnimations()
        if (category != null) {
            val bundle = Bundle()
            bundle.putParcelable(Constants.EXTRA_DATA, category)
            collectionFragment.arguments = bundle
            mNavigator.replaceFragmentAndAddToBackStack(R.id.container, collectionFragment, bundle, null)
        } else {
            mNavigator.replaceFragmentAndAddToBackStack(R.id.container, collectionFragment, null, null)
        }
    }

    fun navigateVideosFragment(collection: Collection?) {
        val collectionFragment = VideoFragment().addAnimations()
        if (collection != null) {
            val bundle = Bundle()
            bundle.putParcelable(Constants.EXTRA_DATA, collection)
            collectionFragment.arguments = bundle
            mNavigator.replaceFragmentAndAddToBackStack(R.id.container, collectionFragment, bundle, null)
        } else {
            mNavigator.replaceFragmentAndAddToBackStack(R.id.container, collectionFragment, null, null)
        }
    }

    fun navigateCollectionFragment() {
        mNavigator.replaceFragmentAndAddToBackStack(R.id.container, CollectionFragment().addAnimations(), null, null)
    }
}