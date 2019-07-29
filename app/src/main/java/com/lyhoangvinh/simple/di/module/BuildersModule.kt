package com.lyhoangvinh.simple.di.module

import com.lyhoangvinh.simple.ui.features.avg.home.HomeActivity
import com.lyhoangvinh.simple.ui.features.avg.home.HomeActivityModule
import com.lyhoangvinh.simple.ui.features.avg.home.HomeFragment
import com.lyhoangvinh.simple.ui.features.avg.home.HomeFragmentModule
import com.lyhoangvinh.simple.ui.features.avg.splash.SplashActivity
import com.lyhoangvinh.simple.ui.features.avg.splash.SplashModule
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicActivity
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicActivityModule
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicFragment
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicFragmentModule
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicSingleActivity
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicSingleActivityModule
import com.lyhoangvinh.simple.ui.features.comic.testpaging.ComicPagingActivity
import com.lyhoangvinh.simple.ui.features.comic.testpaging.ComicPagingActivityModule
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

    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun homeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [ComicPagingActivityModule::class])
    abstract fun comicPagingActivity(): ComicPagingActivity
}