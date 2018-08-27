package com.binguner.xieyi.RxUtils

import com.binguner.xieyi.beans.DoRegisterBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface ApiServices{

    companion object {
        val BASE_URL = "http://39.106.122.7:3001/api/v1/"
    }

    @POST("doregister")
    @FormUrlEncoded
    fun doRegister(@Field("phone") phone:String,@Field("username") username: String, @Field("password") password: String) :Observable<DoRegisterBean>
}