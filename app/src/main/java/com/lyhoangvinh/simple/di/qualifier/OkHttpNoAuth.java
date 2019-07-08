package com.lyhoangvinh.simple.di.qualifier;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * NO Authorization header {@link okhttp3.OkHttpClient}
 */

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface OkHttpNoAuth {
}