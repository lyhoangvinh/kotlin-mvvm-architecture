package com.lyhoangvinh.simple.di.module

import android.arch.lifecycle.ViewModelProvider
import com.lyhoangvinh.simple.di.ServiceViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ServiceViewModelFactory): ViewModelProvider.Factory
}