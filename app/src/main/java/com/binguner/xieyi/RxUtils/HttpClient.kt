package com.binguner.xieyi.RxUtils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.binguner.xieyi.BuildConfig
import com.binguner.xieyi.databases.DBUtils
import com.binguner.xieyi.listeners.ResultListener
import com.binguner.xieyi.phoneNumber
import com.binguner.xieyi.utils.NetworkUtil

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers

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


/**
 *  Name: UserData
 *      isLoging
 *      username
 *      password
 *      phonenumber
 *      user_id
 */
class HttpClient(context: Context){

    lateinit var retrofit:Retrofit
    lateinit var editor: SharedPreferences.Editor
    var dbUtils = DBUtils(context)
    init {

        editor = context.getSharedPreferences("UserData",Context.MODE_PRIVATE).edit()

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
                .baseUrl("http://xyapi.lzhu.top/api/v1/")
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
                    //Log.d("fmosanfaus",it.message)
                    editor.putString("username", username)
                    editor.putString("password", password)
                    editor.putString("phonenumber", phone)
                    editor.putString("user_id", it.data.id)
                    editor.commit()

                    dbUtils.insertNewUserInfo(phone,username,password,it.data.id,"" )

                    //Log.d("Whatsinsp","phontnumber is $phoneNumber, username is $username , password is $password")
                    resultListener.postResullt(ResultListener.nextType,it.message)

                },{
                    //Log.d("fmosanfaus",it.toString())
                    resultListener.postResullt(ResultListener.errorType,it.message!!)
                }, {
                    //Log.d("fmosanfaus","onCompliated")
                    resultListener.postResullt(ResultListener.succeedType, "")
                })

    }

    fun doLogin(username: String, password: String, resultListener: ResultListener){
        Log.d("erwr", "username is $username, password id $password")
        services.doLogin(username, password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    editor.putString("username", it.data.username)
                    editor.putString("password", password)
                    editor.putString("phonenumber", it.data.phone)
                    editor.putString("user_id", it.data._id)
                    editor.commit()

                    dbUtils.insertOldUserInfo(it)
                    resultListener.postResullt(ResultListener.nextType,it.message)
                },{
                    resultListener.postResullt(ResultListener.errorType,it.message!!)
                },{
                    resultListener.postResullt(ResultListener.succeedType,"")
                })
    }
}