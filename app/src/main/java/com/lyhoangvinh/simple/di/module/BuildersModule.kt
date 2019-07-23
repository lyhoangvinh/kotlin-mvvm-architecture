package com.lyhoangvinh.simple.di.module

import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicActivity
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicActivityModule
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicSingleActivity
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicSingleActivityModule
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicFragment
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [ComicActivityModule::class])
    abstract fun mainActivity(): ComicActivity

    @ContributesAndroidInjector(modules = [ComicSingleActivityModule::class])
    abstract fun testActivity(): ComicSingleActivity

    @ContributesAndroidInjector(modules = [ComicFragmentModule::class])
    abstract fun testFragment(): ComicFragment
}