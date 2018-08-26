package com.binguner.xieyi.adapters

import android.content.Context
import com.binguner.xieyi.beans.ShakeProtocoBean
import com.binguner.xieyi.utils.MyImageLoader
import com.binguner.xieyi.viewholders.Shake_protocpl_viewholder
import com.chad.library.adapter.base.BaseQuickAdapter

class ShakeProtocolAdapter(val layoutid:Int,private val ctx:Context, data: MutableList<ShakeProtocoBean>?) : BaseQuickAdapter<ShakeProtocoBean, Shake_protocpl_viewholder>(layoutid,data) {

    override fun convert(helper: Shake_protocpl_viewholder?, item: ShakeProtocoBean?) {
        try {
            MyImageLoader.displayImage(ctx,item!!.path,helper!!.shake_avator)
        }catch (e:Exception){ }
        helper?.shake_username?.setText(item?.username)
        helper?.shake_date?.setText(item?.date)
        helper?.shake_content?.setText(item?.content)
    }

}