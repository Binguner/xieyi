package com.binguner.xieyi.utils

import android.content.Context

import com.binguner.xieyi.BuildConfig

import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import android.R.id.edit
import android.content.SharedPreferences
import com.binguner.xieyi.RxUtils.ApiServices
import com.binguner.xieyi.beans.DoRegisterBean
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Scheduler
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class test (context: Context){
    private fun getNewClient(mContext: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
        val path = "data/user/0/com.nenguou.dayuandaily/cache"
        val cacheFile = File(path, "DayuanCache")
        val cache = Cache(cacheFile, (10 * 1024 * 1024).toLong())
        // 缓存拦截器
        val cacheInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isAvailable(mContext)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }

            val response = chain.proceed(request)

            if (NetworkUtil.isAvailable(mContext)) {
                val maxAge = 0
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxAge")
                        .build()
            }
            response
        }
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(loggingInterceptor)
                .addInterceptor(cacheInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .cache(cache)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
        return builder.build()
    }

    val gson: Gson = GsonBuilder()
            .setLenient()
            .create()
    var retrofit: Retrofit = Retrofit.Builder()
            .client(getNewClient(context))
            .baseUrl("39.106.122.7:3001/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

    val service = retrofit.create(ApiServices::class.java)

    fun getCaptcha() {
        service.doRegister("","","")
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .unsubscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe {

                }
    }
}
