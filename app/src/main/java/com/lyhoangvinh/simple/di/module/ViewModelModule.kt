package com.lyhoangvinh.simple.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lyhoangvinh.simple.di.ViewModelFactory
import com.lyhoangvinh.simple.di.qualifier.ViewModelKey
import com.lyhoangvinh.simple.ui.features.avg.detail.DetailViewModel
import com.lyhoangvinh.simple.ui.features.avg.main.collection.CollectionViewModel
import com.lyhoangvinh.simple.ui.features.avg.main.home.HomeViewModel
import com.lyhoangvinh.simple.ui.features.avg.search.local.SearchViewModel
import com.lyhoangvinh.simple.ui.features.avg.search.paging.SearchPagedViewModel
import com.lyhoangvinh.simple.ui.features.avg.main.video.VideoViewModel
import com.lyhoangvinh.simple.ui.features.avg.splash.SplashViewModel
import com.lyhoangvinh.simple.ui.features.comic.detail.ImageDetailViewModel
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicViewModel
import com.lyhoangvinh.simple.ui.features.comic.testfragment.ComicSingleViewModel
import com.lyhoangvinh.simple.ui.features.comic.testpaging.ComicPagingViewModel
import com.lyhoangvinh.simple.ui.features.comicavg.ComicAvgViewModel
import com.lyhoangvinh.simple.ui.features.comicavg.portal.PortalViewModel
import com.lyhoangvinh.simple.ui.features.setting.SettingViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun detailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel::class)
    internal abstract fun videoViewModel(viewModel: VideoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CollectionViewModel::class)
    internal abstract fun collectionViewModel(viewModel: CollectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun searchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchPagedViewModel::class)
    internal abstract fun searchPagedViewModel(viewModel: SearchPagedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComicAvgViewModel::class)
    internal abstract fun comicAvgViewModel(viewModel: ComicAvgViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PortalViewModel::class)
    internal abstract fun portalViewModel(viewModel: PortalViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    internal abstract fun settingViewModel(viewModel: SettingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ImageDetailViewModel::class)
    internal abstract fun imageDetailViewModel(viewModel: ImageDetailViewModel): ViewModel
}