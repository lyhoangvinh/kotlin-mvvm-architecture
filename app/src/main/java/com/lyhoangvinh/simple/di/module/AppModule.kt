package com.lyhoangvinh.simple.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lyhoangvinh.simple.MyApplication
import com.lyhoangvinh.simple.di.qualifier.ApplicationContext
import com.lyhoangvinh.simple.utils.DateDeserializer
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.GlobalScope
import lyhoangvinh.com.myutil.thread.BackgroundThreadExecutor
import lyhoangvinh.com.myutil.thread.UIThreadExecutor
import java.lang.reflect.Modifier
import java.util.*
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideContext(application: MyApplication) = application

    @Provides
    @Singleton
    fun provideGSon(): Gson = GsonBuilder()
//        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .registerTypeAdapter(Date::class.java, DateDeserializer())
        .create()

    @Provides
    @Singleton
    fun provideBackgroundThreadExecutor(): BackgroundThreadExecutor = BackgroundThreadExecutor.getInstance()

    @Provides
    @Singleton
    fun provideUIThreadExecutor(): UIThreadExecutor = UIThreadExecutor.getInstance()

    @Provides
    @Singleton
    fun providerGlobalScope() : GlobalScope = GlobalScope
}