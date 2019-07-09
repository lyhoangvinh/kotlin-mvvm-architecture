package com.lyhoangvinh.simple.di.module

import com.lyhoangvinh.simple.ui.features.test.MainActivity
import com.lyhoangvinh.simple.ui.features.test.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule{

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity
}