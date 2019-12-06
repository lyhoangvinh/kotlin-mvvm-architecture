package com.lyhoangvinh.simple.di.module

import com.lyhoangvinh.simple.ui.features.avg.main.AvgActivity
import com.lyhoangvinh.simple.ui.features.avg.main.AvgActivityModule
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
import com.lyhoangvinh.simple.ui.features.comic.detail.ImageDetailActivity
import com.lyhoangvinh.simple.ui.features.comic.detail.ImageDetailModule
import com.lyhoangvinh.simple.ui.features.comicavg.ComicAvgActivity
import com.lyhoangvinh.simple.ui.features.comicavg.ComicAvgModule
import com.lyhoangvinh.simple.ui.features.comicavg.portal.PortalFragment
import com.lyhoangvinh.simple.ui.features.comicavg.portal.PortalModule
import com.lyhoangvinh.simple.ui.features.setting.SettingActivity
import com.lyhoangvinh.simple.ui.features.setting.SettingModule
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

    @ContributesAndroidInjector(modules = [AvgActivityModule::class])
    abstract fun avgActivity(): AvgActivity

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

    @ContributesAndroidInjector(modules = [ComicAvgModule::class])
    abstract fun comicAvgActivity(): ComicAvgActivity

    @ContributesAndroidInjector(modules = [PortalModule::class])
    abstract fun portalFragment(): PortalFragment

    @ContributesAndroidInjector(modules = [SettingModule::class])
    abstract fun settingActivity(): SettingActivity

    @ContributesAndroidInjector(modules = [ImageDetailModule::class])
    abstract fun imageDetailActivity(): ImageDetailActivity
}