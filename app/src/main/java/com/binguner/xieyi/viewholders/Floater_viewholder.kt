package com.binguner.xieyi.viewholders

import android.util.Log
import android.view.View
import android.widget.TextView
import com.binguner.xieyi.R
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.floater_item_layout.view.*
import org.jetbrains.anko.find

class Floater_viewholder(view: View?) : BaseViewHolder(view),View.OnClickListener{

    lateinit var floater_title:TextView
    lateinit var floater_from_where:TextView
    lateinit var floater_date:TextView

    init {
        floater_title = view!!.find(R.id.all_pro_title)
        floater_from_where = view!!.find(R.id.all_pro_region_share)
        floater_date = view!!.find(R.id.all_pro_time)
    }

    override fun onClick(v: View?) {
        Log.d("tetete","Yopui clcs")
    }


}