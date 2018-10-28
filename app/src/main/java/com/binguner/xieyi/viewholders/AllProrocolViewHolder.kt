package com.binguner.xieyi.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.binguner.xieyi.R
import com.chad.library.adapter.base.BaseViewHolder

class AllProrocolViewHolder(view:View):BaseViewHolder(view){

    lateinit var pro_title : TextView
    lateinit var fromwhere_share : TextView
    lateinit var pro_time: TextView
    lateinit var pro_bg: ImageView
    init {
        pro_title = view.findViewById(R.id.all_pro_title)
        fromwhere_share = view.findViewById(R.id.all_pro_region_share)
        pro_time = view.findViewById(R.id.all_pro_time)
        pro_bg = view.findViewById(R.id.all_pro_bg)
    }
}