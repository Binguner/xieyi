package com.binguner.xieyi.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import com.binguner.xieyi.R
import com.binguner.xieyi.beans.ProtocolDetailBean
import com.binguner.xieyi.viewholders.AllProrocolViewHolder
import com.binguner.xieyi.viewholders.Floater_viewholder
import com.chad.library.adapter.base.BaseQuickAdapter
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource

class AllProtocolAdapter(val ctx:Context,layout_id:Int,data:MutableList<ProtocolDetailBean>): BaseQuickAdapter<ProtocolDetailBean, AllProrocolViewHolder>(layout_id,data) {

    override fun convert(helper: AllProrocolViewHolder?, item: ProtocolDetailBean?) {
        helper?.pro_title?.setText(item?.title)
        helper?.pro_time?.setText(item?.created_at!!.split("T")[0].replace("-","."))
        when(item?.type){
            0 -> {
                helper?.pro_bg?.setImageResource(R.drawable.protocol)
                if(item?.state == "0"){ // 没有分享
                    helper?.fromwhere_share?.setText("确认分享")
                }else if (item?.state == "1"){  // 分享了
                    helper?.fromwhere_share?.setText("取消分享")
                }
            }
            1 -> {
                helper?.pro_bg?.setImageResource(R.drawable.floater)
                helper?.fromwhere_share?.setText(item?.region)
            }
        }

    }

}