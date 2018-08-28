package com.binguner.xieyi.RxUtils

import android.content.Context
import android.util.Log
import com.binguner.xieyi.BuildConfig
import com.binguner.xieyi.listeners.ResultListener
import com.binguner.xieyi.utils.NetworkUtil

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class HttpClient(context: Context){

    lateinit var retrofit:Retrofit
    init {
        val gson:Gson = GsonBuilder()
                .setLenient()
                .create()

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
            /*val catchInterceptor = Interceptor{
                chain: Interceptor.Chain ->  {
                var request = chain.request()
                if(!NetworkUtil.isAvailable(context)){
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build()
                }
                var response:Response = chain.proceed(request)

                if (NetworkUtil.isAvailable(context){
                            var maxAge = 0
                            response.newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxAge)
                                    .build();
                        }
            }
                return response
            }*/

            val cacheIntercepter:Interceptor = Interceptor {
                var request:Request = it.request()
                if(!NetworkUtil.isAvailable(context)){
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build()
                }

                var response:okhttp3.Response = it.proceed(request)

                if (NetworkUtil.isAvailable(context)){
                    var maxAge = 0
                    response.newBuilder()
                            .header("Cache-Control","public, only-if-cached, max-statle=$maxAge" )
                            .build()
                }
                response
            }

            var builder = OkHttpClient.Builder()

            builder.addInterceptor(loggingInterceptor)
                    .addInterceptor(cacheIntercepter)
                    .connectTimeout(15,TimeUnit.SECONDS)
                    //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .readTimeout(15,TimeUnit.SECONDS)
                    .cache(cache)
                    .writeTimeout(15,TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
            return builder.build()
        }

        val retrofit = Retrofit.Builder()
                .client(getNewClient(context))
                .baseUrl("http://39.106.122.7:3001/api/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        this.retrofit = retrofit
    }




    val services = retrofit.create(ApiServices::class.java)

    fun doRegister(phone:String, username:String, password:String, resultListener:ResultListener){
        services.doRegister(phone, username, password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("fmosanfaus",it.message)
                },{
                    Log.d("fmosanfaus",it.toString())
                    resultListener.postResullt(ResultListener.errorType)
                }, {
                    Log.d("fmosanfaus","onCompliated")
                    resultListener.postResullt(ResultListener.succeedType)

                })


    }
}