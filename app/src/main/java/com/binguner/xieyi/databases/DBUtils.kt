package com.binguner.xieyi.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.binguner.xieyi.beans.DoLoginBean
import com.binguner.xieyi.username

class DBUtils(context: Context){
    var db:SQLiteDatabase
    val contentValues = ContentValues()
    init {
        db = context.database.writableDatabase
    }

    // register
    fun insertNewUserInfo(phonenumber: String, username: String, password: String, user_id: String?, user_avator_url:String?) {
        contentValues.put("user_id",user_id)
        contentValues.put("user_name",username)
        contentValues.put("user_phonenumber",phonenumber)
        contentValues.put("user_password",password)
        contentValues.put("user_avatar",user_avator_url)
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

    // delete this accoutn
    fun deleteAccout(user_name:String){
        db.delete("User_info","user_name like ?", arrayOf("user_name"))
    }

}