package com.binguner.xieyi.utils

import android.app.Activity
import android.app.Application

class MyActivity:Activity(){

    companion object {

        lateinit var instance : Activity

        fun getInstance(){
            if (null == instance){
                instance  = Activity()
            }
            //return instance
        }
    }

}