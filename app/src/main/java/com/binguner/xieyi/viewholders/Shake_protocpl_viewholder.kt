package com.binguner.xieyi.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.binguner.xieyi.R
import com.chad.library.adapter.base.BaseViewHolder
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.shake_protocol_item_layout.view.*
import org.jetbrains.anko.find

class Shake_protocpl_viewholder(view: View?) : BaseViewHolder(view) {
    lateinit var shake_avator : CircleImageView
    lateinit var shake_username : TextView
    lateinit var shake_date : TextView
    lateinit var shake_content : TextView
    lateinit var shadow_comment: ImageView
    lateinit var shake_favorite : ImageView

    init {

        shadow_comment = view!!.find(R.id.shadow_comment)
        shake_username = view.find(R.id.shake_username)
        shake_avator = view.find(R.id.shake_avator)
        shake_date = view.find(R.id.shake_date)
        shake_content = view.find(R.id.shake_content)
        shake_favorite = view.find(R.id.shake_favorite)
    }
}
