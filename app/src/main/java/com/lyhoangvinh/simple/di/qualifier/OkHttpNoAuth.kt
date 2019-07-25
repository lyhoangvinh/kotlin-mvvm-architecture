package com.lyhoangvinh.simple.di.qualifier

import javax.inject.Qualifier


/**
 * NO Authorization header [okhttp3.OkHttpClient]
 */

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class OkHttpNoAuth
