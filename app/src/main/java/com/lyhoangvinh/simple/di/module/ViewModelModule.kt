package com.lyhoangvinh.simple.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lyhoangvinh.simple.di.ViewModelFactory
import com.lyhoangvinh.simple.di.qualifier.ViewModelKey
import com.lyhoangvinh.simple.ui.features.test.MainViewModel
import com.lyhoangvinh.simple.ui.features.test2.TestViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel::class)
    internal abstract fun testViewModel(viewModel: TestViewModel): ViewModel
}