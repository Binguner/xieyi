package com.binguner.xieyi.RxUtils

import com.binguner.xieyi.beans.*
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
    fun createProtocol(@Field("title") title:String,
                       @Field("content")content:String,
                       @Field("signatoryNum")signatoryNum:String,
                       @Field("username")username:String,
                       @Field("share")isShared:String):Observable<DoProtocolBean>

    // Create doFloater
    @POST("makefloater")
    @FormUrlEncoded
    fun createFloater(@Field("username") username: String, @Field("title") title: String, @Field("content") content: String, @Field("region") region: String):Observable<MakeFloaterBean>

    // send feedback
    @POST("feedback")
    @FormUrlEncoded
    fun giveFeedback(@Field("content") content: String, @Field("qq") qq: String, @Field("phone") phone: String, @Field("weixin") weixin: String):Observable<FeedBackBean>

    // sign Protocol //http://xyapi.lzhu.top/api/v1/signProtocol
    @POST("signProtocol")
    @FormUrlEncoded
    fun sighProtocol(@Field("username") username: String, @Field("id") protocolId: String):Observable<SignProtocolBean>

    // get a Protocol information
    @GET("viewProtocol")
    fun getOneProtocolInfo(@Query("id") protocolId: String)

    // give a favorite
    @POST("protocol-parise")
    @FormUrlEncoded
    fun giveAHeart(@Field("protocol_id") protocol_id: String, @Field("user_id") user_id:String)



    // modifyUserInfo
    @POST("modifyinfo")
    @FormUrlEncoded
    fun modifyUserInfo(@Field("id")user_id: String, @Field("nickname")nickname:String?, @Field("avatar") avatar_url:String?, @Field("sex")sex:String?,
                       @Field("career")career:String?, @Field("region")region:String?, @Field("phone")phoneNumber:String?, @Field("email")email:String?,
                       @Field("password")newPassword:String?):Observable<ModifyinfoBean>

    @GET("getProtocol")
    fun getNormalProtocolInfo(@Query("id")id:String):Observable<NormalProtocolBean>

    // get the floater information
    @GET("getFloater")
    fun getFloaterProtocolInfo(@Query("id")id:String):Observable<FloaterProtocolInfoBean>

    // xyapi.lzhu.top/api/v1/getRandomFloater
    @GET("getRandomFloater")
    fun getRandomFloater():Observable<RandomFloaterBean>

}