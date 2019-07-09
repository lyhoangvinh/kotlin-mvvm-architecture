package com.lyhoangvinh.simple.di.component

import android.app.Application
import com.lyhoangvinh.simple.MyApplication
import com.lyhoangvinh.simple.di.module.AppModule
import com.lyhoangvinh.simple.di.module.BuildersModule
import com.lyhoangvinh.simple.di.module.DataModule
import com.lyhoangvinh.simple.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
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
        BuildersModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: MyApplication)
}