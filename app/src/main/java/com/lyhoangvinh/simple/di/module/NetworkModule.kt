package com.lyhoangvinh.simple.di.module

import com.google.gson.Gson
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.MyApplication
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.di.qualifier.ApplicationContext
import com.lyhoangvinh.simple.di.qualifier.OkHttpNoAuth
import com.lyhoangvinh.simple.utils.ConnectionLiveData
import com.lyhoangvinh.simple.utils.makeOkHttpClientBuilder
import com.lyhoangvinh.simple.utils.makeService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @OkHttpNoAuth
    @Singleton
    internal fun provideOkHttpClientNoAuth(@ApplicationContext context: MyApplication): OkHttpClient = makeOkHttpClientBuilder(context).build()
//        .addInterceptor().build()
//todo: https://stackoverflow.com/questions/45284974/how-to-specify-get-request-encoding-retrofit-okhttp

    @Singleton
    @Provides
    internal fun providesConnectionLiveData(context: MyApplication): ConnectionLiveData = ConnectionLiveData(context)

    @Provides
    @Singleton
    internal fun provideComicVineService(gSon: Gson, @OkHttpNoAuth okHttpClient: OkHttpClient): ComicVineService =
        makeService(ComicVineService::class.java, gSon, okHttpClient, Constants.COMIC_ENDPOINT)

    @Provides
    @Singleton
    internal fun provideAvgleService(gson: Gson, @OkHttpNoAuth okHttpClient: OkHttpClient): AvgleService =
        makeService(AvgleService::class.java, gson, okHttpClient, Constants.AVGLE_ENDPOINT)
}
