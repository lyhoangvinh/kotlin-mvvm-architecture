package com.lyhoangvinh.simple.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lyhoangvinh.simple.di.ViewModelFactory
import com.lyhoangvinh.simple.di.qualifier.ViewModelKey
import com.lyhoangvinh.simple.ui.features.avg.main.home.HomeViewModel
import com.lyhoangvinh.simple.ui.features.avg.splash.SplashViewModel
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicViewModel
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicSingleViewModel
import com.lyhoangvinh.simple.ui.features.comic.testpaging.ComicPagingViewModel
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
    internal abstract fun comicViewModel(viewModel: ComicViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComicSingleViewModel::class)
    internal abstract fun comicSingleViewModel(viewModel: ComicSingleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun splashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComicPagingViewModel::class)
    internal abstract fun comicPagingViewModel(viewModel: ComicPagingViewModel): ViewModel
}