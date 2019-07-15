package com.lyhoangvinh.simple.di.module

import com.lyhoangvinh.simple.ui.features.test.MainActivity
import com.lyhoangvinh.simple.ui.features.test.MainActivityModule
import com.lyhoangvinh.simple.ui.features.test2.TestActivity
import com.lyhoangvinh.simple.ui.features.test2.TestActivityModule
import com.lyhoangvinh.simple.ui.features.test2.TestFragment
import com.lyhoangvinh.simple.ui.features.test2.TestFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [TestActivityModule::class])
    abstract fun testActivity(): TestActivity

    @ContributesAndroidInjector(modules = [TestFragmentModule::class])
    abstract fun testFragment(): TestFragment
}