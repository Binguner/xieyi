package com.binguner.xieyi.custom_view

import android.app.ActionBar
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import android.widget.TextView
import com.binguner.xieyi.R
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.textColor

class ImageTextView(context: Context): TextView(context){
    var normalDrawblePic: Drawable ?= null
    var selectedDrawblePic: Drawable ?= null

    init {
        var layoutParamss = LinearLayout.LayoutParams(dip(50), LinearLayout.LayoutParams.MATCH_PARENT,1f)
        layoutParamss.weight = 1f
        this.layoutParams = layoutParams
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)

        if(selected){
            this.backgroundColor = ContextCompat.getColor(context, R.color.colorLightGrey)
            this.textColor = ContextCompat.getColor(context, R.color.colorBlack)
            if(null != selectedDrawblePic){
                this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, selectedDrawblePic, null, null)
            }
        }else{
            this.backgroundColor = ContextCompat.getColor(context,R.color.colorNormalBack)
            this.textColor = ContextCompat.getColor(context, R.color.colorTextGray)
            if(null != normalDrawblePic){
                this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, normalDrawblePic, null, null)
            }
        }
    }
}