package com.binguner.xieyi.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.binguner.xieyi.beans.DoLoginBean
import com.binguner.xieyi.username

class DBUtils(context: Context){

    companion object {
        val TypeNickname = 0
        val TypePassword = 1
        val TypeEmail = 2
        val TypePhoneNumber = 3
        val TypeAvatar = 4
    }

    var db:SQLiteDatabase
    val contentValues = ContentValues()
    init {
        db = context.database.writableDatabase
    }

    // register
    fun insertNewUserInfo(phonenumber: String, username: String, password: String, user_id: String?, user_avator_url:String?, nickname:String) {
        contentValues.put("user_id",user_id)
        contentValues.put("user_name",username)
        contentValues.put("user_phonenumber",phonenumber)
        contentValues.put("user_password",password)
        contentValues.put("user_avatar",user_avator_url)
        contentValues.put("nickname",nickname)
        try {
            db.insert("User_info",null,contentValues)
        }catch (e: Exception){ }
        contentValues.clear()
    }

    // login
    fun insertOldUserInfo(doLoginBean: DoLoginBean) {
        contentValues.put("user_id",doLoginBean.data._id)
        contentValues.put("user_name",doLoginBean.data.username)
        contentValues.put("user_phonenumber",doLoginBean.data.phone)
        contentValues.put("user_password",doLoginBean.data.password)
        if(null != doLoginBean.data.email && !doLoginBean.data.email.equals("")){
            contentValues.put("email",doLoginBean.data.email)
        }else{
            contentValues.put("email","")
        }
        contentValues.put("user_avatar","")
        try {
            db.insert("User_info",null,contentValues)
        }catch (e:Exception){}
        contentValues.clear()
    }

    // get user name and phonenumber
    fun selectUserInfo(user_id:String):List<String>{
        val cursor: Cursor = db.query("User_info", null, "user_id like ?", arrayOf(user_id), null, null, null)
        lateinit var user_name:String
        lateinit var user_phonenumber : String

        if(cursor.moveToFirst()){
            do {
                user_name = cursor.getString(cursor.getColumnIndex("user_name"))
                user_phonenumber = cursor.getString(cursor.getColumnIndex("user_phonenumber"))
            }while (cursor.moveToNext())
        }
        var list:List<String>
        if (null != user_name && null != user_phonenumber){
            var list = listOf<String>(user_name, user_phonenumber)
            return list
        }else{
            return null!!
        }
    }

    // delete this accoutn/
    fun deleteAccout(user_name:String){
        val isDelete = db.delete("User_info","user_name like ?", arrayOf(user_name))
    }

    // insert all protocols
    fun insertAllProtocol(protocol_id:String, username: String, user_id: String, createPro_ed_title: String,type :String ){
        contentValues.put("protocol_id",protocol_id)
        contentValues.put("username",username)
        contentValues.put("user_id",user_id)
        contentValues.put("createPro_ed_title", createPro_ed_title)
        contentValues.put("type", type)
        try {
            db.insert("all_protocol",null,contentValues)
            contentValues.clear()
        }catch (e:Exception){}
    }

    // inert normal protocol
    fun insertNormalProtocol(protocol_id:String, username:String, user_id: String, title:String, peopleNum:String, isShared:String, pro_content:String, create_at:String){
        contentValues.put("protocol_id",protocol_id)
        contentValues.put("username",username)
        contentValues.put("user_id",user_id)
        contentValues.put("createPro_title",title)
        contentValues.put("choosePeopleNum",peopleNum)
        contentValues.put("isShared",isShared)
        contentValues.put("pro_content",pro_content)
        contentValues.put("create_at",create_at)
        try {
            db.insert("normal_protocol",null,contentValues)
            contentValues.clear()
        }catch (e:Exception){}
    }

    // insert floater protocol
    fun insertFloaterProtocol(protocol_id:String, username:String, user_id:String, createPro_ed_title:String, pro_content:String, created_at:String,obtain_at:String, region:String, state:String){
        contentValues.put("protocol_id",protocol_id)
        contentValues.put("username",username)
        contentValues.put("user_id",user_id)
        contentValues.put("createPro_ed_title",createPro_ed_title)
        contentValues.put("pro_content",pro_content)
        contentValues.put("created_at",created_at)
        contentValues.put("obtain_at",obtain_at)
        contentValues.put("region",region)
        contentValues.put("state",state)
        try {
            db.insert("floater_protocol",null,contentValues)
        }catch (e:Exception){ }
        contentValues.clear()
    }

    // update the user information
    fun updateUserInfo(user_id: String, changeType:Int,any: Any){
        when (changeType){
            DBUtils.TypeNickname -> {
                contentValues.put("nickname",any as String)
                db.update("User_info",contentValues,"user_id like ?", arrayOf(user_id))
                //Log.d("tetete","inserted")
                contentValues.clear()
            }
            DBUtils.TypePhoneNumber -> {
                contentValues.put("user_phonenumber",any as String)
                db.update("User_info", contentValues, "user_id like ?", arrayOf(user_id))
                contentValues.clear()
            }
            DBUtils.TypeEmail -> {
                contentValues.put("email", any as String)
                db.update("User_info", contentValues,"user_id like?", arrayOf(user_id))
                contentValues.clear()
            }
            DBUtils.TypePassword -> {
                contentValues.put("user_password",any as String)
                db.update("User_info",contentValues,"user_id like?", arrayOf(user_id))
                contentValues.clear()
            }
        }
    }



}