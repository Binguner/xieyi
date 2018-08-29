package com.binguner.xieyi.RxUtils

import com.binguner.xieyi.beans.DoLoginBean
import com.binguner.xieyi.beans.DoRegisterBean
import io.reactivex.Observable
import retrofit2.http.*
import java.util.*

interface ApiServices{

    companion object {
        val BASE_URL = "39.106.122.7:3000/api/v1/"
    }

    // Register
    @POST("doregister")
    @FormUrlEncoded
    fun doRegister(@Field("phone") phone:String,@Field("username") username: String, @Field("password") password: String) :Observable<DoRegisterBean>

    // Login
    @POST("dologin")
    @FormUrlEncoded
    fun doLogin(@Field("username") username: String, @Field("password")password :String) :Observable<DoLoginBean>

    // Get user information
    @GET("userinfo")
    fun getUserInfo(@Query("id") userId: String)

    // Create Protocol
    @POST("doProtocol")
    @FormUrlEncoded
    fun createProtocol(@Field("title") title:String, content:String, signatoryNum:String, username:String)

    // Create doFloater
    @POST("doFloater")
    @FormUrlEncoded
    fun createFloater(@Field("title") title: String, @Field("content") content: String, @Field("region") region: String, @Field("username") username: String)

    // send feedback
    @POST("feedback")
    @FormUrlEncoded
    fun giveFeedback(@Field("content") content: String, @Field("qq") qq: String, @Field("phone") phone: String, @Field("weixin") weixin: String)

    // sign Protocol
    @POST("signProtocol")
    @FormUrlEncoded
    fun sighTheProtocol(@Field("username") username: String, @Field("id") protocolId: String)

    // get a Protocol information
    @GET("viewProtocol")
    fun getOneProtocolInfo(@Query("id") protocolId: String)

    // give a favorite
    @POST("protocol-parise")
    @FormUrlEncoded
    fun giveAHeart(@Field("protocol_id") protocol_id: String, @Field("user_id") user_id:String)

    // get the floater information
    @GET("getFloater")
    fun getFloaterInformation(@Query("id") protocolId: String)

}