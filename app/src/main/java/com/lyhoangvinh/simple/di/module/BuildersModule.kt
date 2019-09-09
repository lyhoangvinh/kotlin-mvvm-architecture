package com.lyhoangvinh.simple.di.module

import com.lyhoangvinh.simple.ui.features.avg.main.AgvActivity
import com.lyhoangvinh.simple.ui.features.avg.main.AgvActivityModule
import com.lyhoangvinh.simple.ui.features.avg.detail.DetailActivity
import com.lyhoangvinh.simple.ui.features.avg.detail.DetailModule
import com.lyhoangvinh.simple.ui.features.avg.main.collection.CollectionFragment
import com.lyhoangvinh.simple.ui.features.avg.main.collection.CollectionModule
import com.lyhoangvinh.simple.ui.features.avg.main.home.HomeFragment
import com.lyhoangvinh.simple.ui.features.avg.main.home.HomeFragmentModule
import com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.inside.BannerImagesFragment
import com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.inside.BannerImagesModule
import com.lyhoangvinh.simple.ui.features.avg.search.local.SearchActivity
import com.lyhoangvinh.simple.ui.features.avg.search.local.SearchModule
import com.lyhoangvinh.simple.ui.features.avg.search.paging.SearchPagedActivity
import com.lyhoangvinh.simple.ui.features.avg.search.paging.SearchPagedModule
import com.lyhoangvinh.simple.ui.features.avg.main.video.VideoFragment
import com.lyhoangvinh.simple.ui.features.avg.main.video.VideoFragmentModule
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicActivity
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicActivityModule
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicFragment
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicFragmentModule
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicSingleActivity
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicSingleActivityModule
import com.lyhoangvinh.simple.ui.features.comic.testpaging.ComicPagingActivity
import com.lyhoangvinh.simple.ui.features.comic.testpaging.ComicPagingActivityModule
import com.lyhoangvinh.simple.ui.features.avg.splash.SplashActivity
import com.lyhoangvinh.simple.ui.features.avg.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [ComicActivityModule::class])
    abstract fun comicActivity(): ComicActivity

    @ContributesAndroidInjector(modules = [ComicSingleActivityModule::class])
    abstract fun comicSingleActivity(): ComicSingleActivity

    @ContributesAndroidInjector(modules = [ComicFragmentModule::class])
    abstract fun comicFragmentFragment(): ComicFragment

    @ContributesAndroidInjector(modules = [AgvActivityModule::class])
    abstract fun avgActivity(): AgvActivity

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [ComicPagingActivityModule::class])
    abstract fun comicPagingActivity(): ComicPagingActivity

    @ContributesAndroidInjector(modules = [BannerImagesModule::class])
    abstract fun bannerImagesFragment(): BannerImagesFragment

    @ContributesAndroidInjector(modules = [DetailModule::class])
    abstract fun detailActivity(): DetailActivity

    @ContributesAndroidInjector(modules = [VideoFragmentModule::class])
    abstract fun videoFragment(): VideoFragment

    @ContributesAndroidInjector(modules = [CollectionModule::class])
    abstract fun collectionFragment(): CollectionFragment

    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun searchActivity(): SearchActivity

    @ContributesAndroidInjector(modules = [SearchPagedModule::class])
    abstract fun searchPagedActivity(): SearchPagedActivity
}