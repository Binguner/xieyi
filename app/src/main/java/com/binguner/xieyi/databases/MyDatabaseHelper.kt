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
                "email" to TEXT,
                "nickname" to TEXT
        )

        db?.createTable("all_protocol",true,
                "protocol_id" to TEXT + PRIMARY_KEY + UNIQUE,
                "username" to TEXT,
                "user_id" to TEXT,
                "createPro_ed_title" to TEXT,
                "type" to TEXT
                )

        db?.createTable("normal_protocol",true,
                "protocol_id" to TEXT + PRIMARY_KEY + UNIQUE,
                "username" to TEXT,
                "user_id" to TEXT,
                "createPro_title" to TEXT,
                "choosePeopleNum" to TEXT,
                "isShared" to TEXT,
                "pro_content" to TEXT,
                "create_at" to TEXT
        )

        db?.createTable("floater_protocol",true,
                "protocol_id" to TEXT + PRIMARY_KEY + UNIQUE,
                "username" to TEXT,
                "user_id" to TEXT,
                "createPro_ed_title" to TEXT,
                "pro_content" to TEXT,
                "created_at" to TEXT,
                "obtain_at" to TEXT,
                "region" to TEXT,
                "state" to TEXT
                )
        db?.createTable("signatory_list",true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "protocol_id" to TEXT,
                "user_id" to TEXT,
                "signatory_name" to TEXT)

        db?.createTable("protocolList",true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "protocol_id" to TEXT,
                "user_id" to TEXT,
                "owner" to TEXT,
                "signatoryNum" to TEXT,
                "title" to TEXT,
                "content" to TEXT,
                "createAt" to TEXT,
                "pageNumber" to TEXT
                )

        db?.createTable("protocolList_sign",true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "protocol_id" to TEXT,
                "user_id" to TEXT,
                "signatory_name" to TEXT)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}

val Context.database:MyDatabaseHelper
    get() = MyDatabaseHelper.getInstance(applicationContext)

