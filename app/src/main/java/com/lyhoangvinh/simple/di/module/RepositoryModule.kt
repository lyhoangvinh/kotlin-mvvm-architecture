package com.lyhoangvinh.simple.di.module

import com.lyhoangvinh.simple.data.repo.*
import com.lyhoangvinh.simple.data.repo.impl.*
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun collectionsRepo(repoImpl: CollectionsRepoImpl): CollectionsRepo

    @Binds
    abstract fun homeRepo(repoImpl: HomeRepoImpl): HomeRepo

    @Binds
    abstract fun imageRepo(repoImpl: ImageRepoImpl): ImageRepo

    @Binds
    abstract fun issuesRepo(repoImpl: IssuesRepoImpl): IssuesRepo

    @Binds
    abstract fun searchPagedRepo(repoImpl: SearchPagedRepoImpl): SearchPagedRepo

    @Binds
    abstract fun searchRepo(repoImpl: SearchRepoImpl): SearchRepo

    @Binds
    abstract fun videoRepo(repoImpl: VideoRepoImpl): VideoRepo
}