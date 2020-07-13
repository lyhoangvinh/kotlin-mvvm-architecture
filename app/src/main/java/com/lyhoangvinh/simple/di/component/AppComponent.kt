package com.lyhoangvinh.simple.di.component

import com.lyhoangvinh.simple.MyApplication
import com.lyhoangvinh.simple.di.module.*
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by lyhoangvinh on 05/01/18.
 */
@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        DataModule::class,
        BuildersModule::class,
        RepositoryModule::class]
)
interface AppComponent : AndroidInjector<MyApplication> {
    @Suppress("DEPRECATION")
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>()
}
