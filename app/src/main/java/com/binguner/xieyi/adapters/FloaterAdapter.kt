package com.binguner.xieyi.adapters

import android.content.Context
import android.view.View
import com.binguner.xieyi.beans.Data6
import com.binguner.xieyi.beans.FloaterBean
import com.binguner.xieyi.viewholders.Floater_viewholder
import com.chad.library.adapter.base.BaseQuickAdapter
import org.jetbrains.anko.toast

class FloaterAdapter(val ctx:Context, layoutid:Int,data: MutableList<Data6>?) : BaseQuickAdapter<Data6, Floater_viewholder>(layoutid,data){

    override fun convert(helper: Floater_viewholder?, item: Data6?) {
        helper?.floater_title?.setText(item?.title)
        helper?.floater_date?.setText(item?.created_at!!.split("T")[0].replace("-","."))
        helper?.floater_from_where?.setText(item?.region)
    }

    

}