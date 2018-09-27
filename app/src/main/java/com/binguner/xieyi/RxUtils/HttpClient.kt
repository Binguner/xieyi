package com.binguner.xieyi.RxUtils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.binguner.xieyi.BuildConfig
import com.binguner.xieyi.activities.type
import com.binguner.xieyi.databases.DBUtils
import com.binguner.xieyi.httpClient
import com.binguner.xieyi.listeners.ResultListener
import com.binguner.xieyi.password
import com.binguner.xieyi.sharedPreferences
import com.binguner.xieyi.utils.NetworkUtil

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
 *      email
 *      nickname
 *      avatar_url
 *      sex
 */
class HttpClient(context: Context){

    val HttpClientTag = "HttpTagTag"
    lateinit var retrofit:Retrofit
    lateinit var editor: SharedPreferences.Editor
    lateinit var sp :SharedPreferences
    var dbUtils = DBUtils(context)
    init {

        editor = context.getSharedPreferences("UserData",Context.MODE_PRIVATE).edit()
        sp = context.getSharedPreferences("UserData",Context.MODE_PRIVATE)

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
                    //editor.putBoolean("isLoging",true)
                    if(it.message.equals("注册成功")) {
                        editor.putString("username", username)
                        editor.putString("password", password)
                        editor.putString("phonenumber", phone)
                        editor.putString("user_id", it.data.id)
                        editor.putString("nickname", "请设置昵称")
                        editor.commit()

                        dbUtils.insertNewUserInfo(phone, username, password, it.data.id, "","请设置昵称")

                        //Log.d("Whatsinsp","phontnumber is $phoneNumber, username is $username , password is $password")
                        resultListener.postResullt(ResultListener.succeedType, it.message)
                    }else{
                        resultListener.postResullt(ResultListener.failedType, it.message)
                    }
                },{
                    //Log.d("fmosanfaus",it.toString())
                    //resultListener.postResullt(ResultListener.errorType,it.message!!)
                    //resultListener.postResullt(ResultListener.failedType, it.message)

                }, {
                    //Log.d("fmosanfaus","onCompliated")
                    //resultListener.postResullt(ResultListener.succeedType, "")
                })

    }

    fun doLogin(username: String, password: String, resultListener: ResultListener){
        //Log.d("tetete", "username is $username, password id $password")
        services.doLogin(username, password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //editor.putBoolean("isLoging",true)
                    //Log.d("tetete",it.message)
                    if(it.message.equals("登录成功")) {
                        editor.putString("username", it.data.username)
                        editor.putString("password", password)
                        editor.putString("phonenumber", it.data.phone)
                        //Log.d("tetete",password)
                        editor.putString("user_id", it.data._id)
                        editor.putString("nickname", it.data.nickname)
                        try {
                            editor.putString("email", it.data.email)
                        } catch (e: Exception) {
                        }
                        //editor.putString("email", it.data.email)
                        editor.commit()
                        dbUtils.insertOldUserInfo(it)
                        resultListener.postResullt(ResultListener.succeedType, it.message)
                        //Log.d(HttpClientTag,"onNext : ${it.message}")

                        if (it.message.equals("登录成功")) {
                            it.data.protocols.forEach {
                                //Log.d(HttpClientTag, it.id)
                                dbUtils.insertAllProtocol(it.id,
                                        sharedPreferences.getString("username","null"),
                                        sharedPreferences.getString("user_id","null"),
                                        "null",
                                        it.type.toString())
                                when(it.type){
                                    0 -> {
                                        services.getNormalProtocolInfo(it.id)
                                                .subscribeOn(Schedulers.io())
                                                .unsubscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe {
                                                    if(null != it){
                                                        dbUtils.insertNormalProtocol(it.data._id,
                                                                sharedPreferences.getString("username","null"),
                                                                sharedPreferences.getString("user_id","null"),
                                                                it.data.title,
                                                                it.data.signatoryNum.toString(),
                                                                it.data.share.toString(),
                                                                it.data.content,
                                                                it.data.created_at)
                                                    }else{

                                                    }
                                                }
                                    }
                                    1 -> {
                                        services.getFloaterProtocolInfo(it.id)
                                                .subscribeOn(Schedulers.io())
                                                .unsubscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe {
                                                    if(null != it){
                                                        dbUtils.insertFloaterProtocol(it.data._id,
                                                                sharedPreferences.getString("username","null"),
                                                                sharedPreferences.getString("user_id","null"),
                                                                it.data.title,
                                                                it.data.content,
                                                                it.data.created_at,
                                                                it.data.obtain_at,
                                                                it.data.region,
                                                                it.data.state.toString())
                                                    }
                                                }
                                    }
                                }
                            }
                        }
                    }else{
                        //resultListener.postResullt(ResultListener.failedType, it.message)
                    }

                },{
                    //Log.d("tetete","onError : ${it.message}")
                    //resultListener.postResullt(ResultListener.errorType,it.message!!)
                    resultListener.postResullt(ResultListener.failedType, "登录失败，请检查用户名和密码。")

                },{
                    //Log.d("tetete","onComplete : ")
                    //resultListener.postResullt(ResultListener.succeedType,"")
                })
    }

    // create Protocol
    fun doProtocol(title:String, content:String, signatoryNum:String, username:String,resultListener: ResultListener){
        services.createProtocol(title,content,signatoryNum,username,"0")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.message.equals("创建成功")){
                        resultListener.postResullt(ResultListener.succeedType,it.message)
                        // 把找个协议保存到 所有 协议中， 类型为 0 ， 0 是普通协议的意思
                        dbUtils.insertAllProtocol(it.data.id,username,sp.getString("user_id",""),title,"0")
                        // 把找个协议保存到普通协议列表，0 是不分享的意思（分享了变成抖协议）
                        dbUtils.insertNormalProtocol(it.data.id,username,sp.getString("user_id",""),title,signatoryNum,"0",content,System.currentTimeMillis().toString())
                    }else{
                        resultListener.postResullt(ResultListener.succeedType,it.message)
                    }
                },{
                    resultListener.postResullt(ResultListener.failedType,"创建失败，请重试！")

                },{

                })
    }

    // send feed to us
    fun giveFeedback(content:String, qqNumber:String, phoneNumber:String, wechatId:String, resultListener: ResultListener){
        services.giveFeedback(content,qqNumber,phoneNumber,wechatId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.message.equals("意见反馈成功")){
                        resultListener.postResullt(ResultListener.succeedType,it.message)
                    }
                },{

                },{

                })
    }

    // create Floater
    fun createFloater(username:String, title:String, content:String,region:String,resultListener: ResultListener){
        services.createFloater(username,title,content,region)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.message.equals("漂流瓶创建成功")){
                        resultListener.postResullt(ResultListener.succeedType,it.message)
                        try {
                            // 把找个协议保存到 所有 协议中， 类型为 1 ， 1 是漂流瓶的意思
                            dbUtils.insertAllProtocol(it.data.id,
                                    username,
                                    sharedPreferences.getString("user_id",""),
                                    title,
                                    "1")
                            // 保存协议到漂流瓶列表
                            dbUtils.insertFloaterProtocol(it.data.id,
                                    username,
                                    sharedPreferences.getString("user_id",""),
                                    title,
                                    content,
                                    System.currentTimeMillis().toString(),
                                    "null",
                                    region,
                                    "0")
                        }catch (e:Exception){}
                    }
                },{
                    resultListener.postResullt(ResultListener.failedType,"创建失败，请重试！")
                },{

                })
    }

    // modify the user information
    fun modifyInfo(user_id:String, nickname:String?, avatar_url:String?,sex:String?, career:String?, region: String?, phoneNumber:String?, email:String?, newPassword:String?, resultListener: ResultListener){
        services.modifyUserInfo(user_id, nickname, avatar_url, sex,career,region,phoneNumber, email, newPassword)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.message.equals("修改学生信息成功") && it.data.nModified != null){
                        resultListener.postResullt(ResultListener.succeedType, it.message)
                        if (nickname.equals("") && null != nickname) {
                            editor.putString("nickname",nickname)
                            editor.commit()
                        }
                        if(!phoneNumber.equals("") && phoneNumber != null){
                            editor.putString("phonenumber",phoneNumber)
                            editor.commit()
                        }
                        if(!email.equals("") && null != email){
                            editor.putString("email",email)
                            editor.commit()
                        }
                        if(!newPassword.equals("") && null != newPassword){
                            editor.putString("password", newPassword)
                            editor.commit()
                        }
                    }
                },{
                    resultListener.postResullt(ResultListener.failedType,"修改学生信息失败!")
                },{

                })
    }




}