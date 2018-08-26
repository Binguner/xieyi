package com.binguner.xieyi.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.binguner.xieyi.R
import com.lzy.imagepicker.loader.ImageLoader
import com.squareup.picasso.Picasso
import java.io.File
import java.util.*

class MyImageLoader (ctx:Context):ImageLoader{

    override fun displayImagePreview(activity: Activity?, path: String?, imageView: ImageView?, width: Int, height: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearMemoryCache() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayImage(activity: Activity?, path: String?, imageView: ImageView?, width: Int, height: Int) {
        Picasso.with(activity)
                .load(Uri.fromFile(File(path)))
                .error(R.color.colorBlack)
                .resize(width,height)
                .centerInside()
                .into(imageView)
    }

    companion object {
        fun displayImage(ctx: Context,path:String,iv:ImageView){
            Picasso.with(ctx.applicationContext).load(Uri.parse(path.toString())).into(iv)
        }
    }

}