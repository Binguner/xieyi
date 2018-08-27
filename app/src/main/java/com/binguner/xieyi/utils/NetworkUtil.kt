package com.binguner.xieyi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtil{
    companion object {
        fun getActivitveNetworkInfo(context: Context):NetworkInfo{
            val connectivityManager:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo
        }

        fun isAvailable(context: Context):Boolean{
            var networkInfo: NetworkInfo = getActivitveNetworkInfo(context)
            return networkInfo != null && networkInfo.isAvailable
        }
    }
}