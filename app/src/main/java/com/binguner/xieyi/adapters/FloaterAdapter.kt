package com.binguner.xieyi.adapters

import android.content.Context
import com.binguner.xieyi.beans.FloaterBean
import com.binguner.xieyi.viewholders.Floater_viewholder
import com.chad.library.adapter.base.BaseQuickAdapter

class FloaterAdapter(ctx:Context, layoutid:Int,data: MutableList<FloaterBean>?) : BaseQuickAdapter<FloaterBean, Floater_viewholder>(layoutid,data){

    override fun convert(helper: Floater_viewholder?, item: FloaterBean?) {
        helper?.floater_title?.setText(item?.title)
        helper?.floater_date?.setText(item?.date)
        helper?.floater_title?.setText(item?.title)
    }

}