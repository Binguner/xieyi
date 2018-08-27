package com.binguner.xieyi.RxUtils

import android.content.Context
import android.os.Build
import com.binguner.xieyi.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import kotlin.math.log

class HttpClient{

    val retrofit = Retrofit.Builder()
            .client()

    fun getNewClient(context: Context):OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG){
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }else{
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
        val path = "data/user/0/com.nenguou.xieyi/cache"
        val catchFile = File(path,"xieYiCatch")
        val cache = Cache(catchFile, 10 * 1024 * 1024)
        val catchInterceptor = Interceptor{
            chain: Interceptor.Chain ->  {
            val request = chain.request()
            if(!Network)
        }
        }
    }
}