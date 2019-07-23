package com.lyhoangvinh.simple.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lyhoangvinh.simple.di.ViewModelFactory
import com.lyhoangvinh.simple.di.qualifier.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ComicViewModel::class)
    internal abstract fun mainViewModel(viewModel: ComicViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComicViewModel::class)
    internal abstract fun testViewModel(viewModel: com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicViewModel): ViewModel
}