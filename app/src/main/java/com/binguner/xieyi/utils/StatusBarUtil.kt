package com.binguner.xieyi.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import java.sql.BatchUpdateException

class StatusBarUtil{

    companion object {
        fun transparentStatusBar(activity:Activity){
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                var window = activity.window
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN and View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.statusBarColor = Color.TRANSPARENT
            }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                var window = activity.window
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
        fun setStatusBarColor(activity: Activity, color:Int){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                var window = activity.window
                window.statusBarColor = ContextCompat.getColor(activity,color)
                        //activity.getResources().getColor(color)
            }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                transparentStatusBar(activity)
                val contentView = activity.findViewById<ViewGroup>(android.R.id.content)
                val statusBarView = View(activity)
                val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)))
                statusBarView.setBackgroundColor(color)
                contentView.addView(statusBarView,layoutParams)
            }
        }

        fun getStatusBarHeight(ctx: Context):Int{
            var result = 0
            var resourceId = ctx.resources.getIdentifier("status_bar_height","dimen","android")
            if (resourceId > 0){
                result = ctx.resources.getDimensionPixelOffset(resourceId)
            }
            return  result
        }

        fun setStatusBarTextBalck(activity: Activity){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}