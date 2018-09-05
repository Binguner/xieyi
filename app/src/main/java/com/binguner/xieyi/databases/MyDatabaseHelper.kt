package com.binguner.xieyi.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import java.awt.font.TextAttribute

class MyDatabaseHelper(context: Context):ManagedSQLiteOpenHelper(context,"Xieyiedatabase",null,1){

    companion object {

        private var instance:MyDatabaseHelper? = null

        @Synchronized
        fun getInstance(context: Context):MyDatabaseHelper{
            if(null == instance){
                instance = MyDatabaseHelper(context)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable("User_info", true,
                "user_id" to TEXT + PRIMARY_KEY + UNIQUE,
                "user_name" to TEXT,
                "user_password" to TEXT,
                "user_phonenumber" to TEXT,
                "user_avatar" to TEXT,
                "email" to TEXT
        )

        db?.createTable("normal_protocol",true,
                "protocol_id" to TEXT + PRIMARY_KEY + UNIQUE,
                "user_id" to TEXT,
                "createPro_ed_title" to TEXT,
                "createPro_ed_content" to TEXT,
                "choosePeopleNum" to TEXT,
                "username" to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}

val Context.database:MyDatabaseHelper
    get() = MyDatabaseHelper.getInstance(applicationContext)

