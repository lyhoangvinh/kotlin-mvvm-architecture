package com.lyhoangvinh.simple.di.qualifier

import javax.inject.Qualifier


/**
 * Authorization header [okhttp3.OkHttpClient]
 */
@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpAuth