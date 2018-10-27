package com.binguner.xieyi.adapters

import android.content.Context
import com.binguner.xieyi.beans.ProtocolDetailBean
import com.binguner.xieyi.viewholders.Floater_viewholder
import com.chad.library.adapter.base.BaseQuickAdapter

class AllProtocolAdapter(val ctx:Context,layout_id:Int,data:MutableList<ProtocolDetailBean>): BaseQuickAdapter<ProtocolDetailBean, Floater_viewholder>(layout_id,data) {

    override fun convert(helper: Floater_viewholder?, item: ProtocolDetailBean?) {
        helper?.floater_title?.setText(item?.title)
        helper?.floater_date?.setText(item?.created_at)
        helper?.floater_from_where?.setText(item?.region)
    }

}