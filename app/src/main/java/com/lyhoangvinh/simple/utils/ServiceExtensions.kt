package com.lyhoangvinh.simple.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.gson.*
import com.lyhoangvinh.simple.BuildConfig
import com.lyhoangvinh.simple.data.entities.Entities
import com.lyhoangvinh.simple.data.entities.ErrorEntity
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.source.base.PlainResponseZipFourConsumer
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.response.ResponseFourZip
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.interfaces.PlainEntitiesPagingConsumer
import com.lyhoangvinh.simple.ui.base.interfaces.PlainPagingConsumer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import lyhoangvinh.com.myutil.network.Tls12SocketFactory
import okhttp3.Cache
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

fun <T> makeRequest(
    request: Single<T>,
    shouldUpdateUi: Boolean,
    @NonNull responseConsumer: PlainConsumer<T>,
    @Nullable errorConsumer: PlainConsumer<ErrorEntity>?
): Disposable {

    var single = request.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
    if (shouldUpdateUi) {
        single = single.observeOn(AndroidSchedulers.mainThread())
    }

    return single.subscribe(responseConsumer, Consumer {
        // handle error
        it.printStackTrace()
        errorConsumer?.accept(ErrorEntity(getPrettifiedErrorMessage(it), getErrorCode(it)))
    })
}

fun <E, T : Entities<E>> makeRequestAvg(
    request: Single<BaseResponseAvgle<T>>,
    @NonNull responseConsumer: PlainEntitiesPagingConsumer<E, T>,
    @Nullable errorConsumer: PlainConsumer<ErrorEntity>?
): Disposable {

    var single = request.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
    single = single.observeOn(AndroidSchedulers.mainThread())
    return single.subscribe({
        if (it != null && it.success)
            responseConsumer.accept(it.response.list)
    }, {
        // handle error
        it.printStackTrace()
        errorConsumer?.accept(ErrorEntity(getPrettifiedErrorMessage(it), getErrorCode(it)))
    })
}

fun <T> makeRequest(
    request: Single<BaseResponseComic<T>>,
    @NonNull responseConsumer: PlainPagingConsumer<T>,
    @Nullable errorConsumer: PlainConsumer<ErrorEntity>?
): Disposable {

    var single = request.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
    single = single.observeOn(AndroidSchedulers.mainThread())
    return single.subscribe({
        if (it != null && it.results.isNotEmpty())
            responseConsumer.accept(it.results)
    }, {
        // handle error
        it.printStackTrace()
        errorConsumer?.accept(ErrorEntity(getPrettifiedErrorMessage(it), getErrorCode(it)))
    })
}


/**
 * Get http error code from [Throwable] if it is instance of [HttpException]
 * @param throwable input throwable
 * @return http code or -1 if throwable isn't a instance of [HttpException]
 */
fun getErrorCode(throwable: Throwable): Int {
    return (throwable as? HttpException)?.code() ?: -1
}

/**
 * Get a error message from retrofit response throwable
 * @param throwable retrofit rx throwable
 * @return error message
 */
fun getPrettifiedErrorMessage(throwable: Throwable?): String {
    if (throwable is HttpException) {
        return ErrorEntity.NETWORK_UNAVAILABLE
    } else if (throwable is IOException) {
        return ErrorEntity.NETWORK_UNAVAILABLE
    }
    return ErrorEntity.OOPS
}

fun <T> makeService(serviceClass: Class<T>, gson: Gson, okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
    return retrofit.create(serviceClass)
}

/**
 * Make 4 Request
 *
 */

fun <T1, T2, T3, T4> makeRequest(
    request1: Single<T1>,
    request2: Single<T2>,
    request3: Single<T3>,
    request4: Single<T4>,
    shouldUpdateUi: Boolean,
    @NonNull responseConsumer: PlainResponseZipFourConsumer<T1, T2, T3, T4>,
    @Nullable errorConsumer: PlainConsumer<ErrorEntity>?
): Disposable {
    var single1 = request1.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
    var single2 = request2.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
    var single3 = request3.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
    var single4 = request4.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())

    if (shouldUpdateUi) {
        single1 = single1.observeOn(AndroidSchedulers.mainThread())
        single2 = single2.observeOn(AndroidSchedulers.mainThread())
        single3 = single3.observeOn(AndroidSchedulers.mainThread())
        single4 = single4.observeOn(AndroidSchedulers.mainThread())
    }

    return Single.zip(single1, single2, single3, single4,
        Function4<T1, T2, T3, T4, ResponseFourZip<T1, T2, T3, T4>> { t1, t2, t3, t4 ->
            ResponseFourZip(t1, t2, t3, t4)
        })
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ o ->
            responseConsumer.accept(o)

        }, { throwable ->
            // handle error
//                throwable.printStackTrace()
            errorConsumer?.accept(ErrorEntity.getError(throwable))
        })
}


fun makeOkHttpClientBuilder(context: Context): OkHttpClient.Builder {
    val logging = HttpLoggingInterceptor()

    if (BuildConfig.DEBUG) {
        logging.level = HttpLoggingInterceptor.Level.BODY
    }

    // : 4/26/2017 add the UnauthorisedInterceptor to this retrofit, or 401
    val builder = OkHttpClient.Builder()
//        .addInterceptor(UnauthorisedInterceptor(context))
        .addInterceptor(logging)
        .followRedirects(true)
        .followSslRedirects(true)
        .retryOnConnectionFailure(true)
        .cache(null)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)

    // add cache to client
    val baseDir = context.cacheDir
    if (baseDir != null) {
        val cacheDir = File(baseDir, "HttpResponseCache")
        builder!!.cache(Cache(cacheDir, (10 * 1024 * 1024).toLong())) // 10 MB
    }

    return enableTls12OnPreLollipop(builder)
}

/**
 * Enable TLS 1.2 on Pre Lollipop android versions
 * @param client OkHttpClient builder
 * @return builder with SSL Socket Factory set
 * according to [OkHttpClient.Builder.sslSocketFactory] deprecation,
 * Please add config SSL with [X509TrustManager] by using [CustomTrustManager]
 * * how to enable tls on android 4.4
 * [](https://github.com/square/okhttp/issues/2372)
 */
@Suppress("DEPRECATION")
@SuppressLint("ObsoleteSdkInt")
fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
    if (Build.VERSION.SDK_INT in 16..21) {
        try {
            val sc = SSLContext.getInstance("TLSv1.2")
            sc.init(null, null, null)
            // TODO: 9/7/17 set SSL socket factory with X509TrustManager
            client.sslSocketFactory(Tls12SocketFactory(sc.socketFactory))

            val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .build()

            val specs = ArrayList<ConnectionSpec>()
            specs.add(cs)
            specs.add(ConnectionSpec.COMPATIBLE_TLS)
            specs.add(ConnectionSpec.CLEARTEXT)

            client.connectionSpecs(specs)
        } catch (exc: Exception) {
            Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
        }

    }

    return client
}

class DateDeserializer : JsonDeserializer<Date> {
    @SuppressLint("SimpleDateFormat")
    @Throws(JsonParseException::class)
    override fun deserialize(element: JsonElement, arg1: Type, arg2: JsonDeserializationContext): Date? {
        val date = element.asString
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return try {
            formatter.parse(date)
        } catch (e: ParseException) {
            null
        }

    }
}