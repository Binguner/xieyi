package com.binguner.xieyi.custom_view

import android.app.ActionBar
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.TextView
import com.binguner.xieyi.R
import org.jetbrains.anko.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.dip
import org.jetbrains.anko.textColor

/*class ImageTextView(context: Context): TextView(context){
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

public inline fun ViewManager.imageTextView() = imageTextView()
public inline fun ViewManager.imageTextView(init: ImageTextView.() -> Unit) = ankoView({ ImageTextView(it) }, init)*/

class imageTextView(context: Context) : TextView(context) {
    var normalDrawable: Drawable? = null
    var selectedDrawable: Drawable? = null

    init {
        var layoutParams = LinearLayout.LayoutParams(dip(50),
                LinearLayout.LayoutParams.MATCH_PARENT, 1f)
        layoutParams.weight = 1f
        this.layoutParams = layoutParams
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)

        if (selected) {
            this.backgroundColor = ContextCompat.getColor(context, R.color.colorLightGrey)
            this.textColor = ContextCompat.getColor(context, R.color.colorBlack)

            if (selectedDrawable != null) {
                this.setCompoundDrawablesWithIntrinsicBounds(null, selectedDrawable, null, null)
            }
        } else {
            this.backgroundColor = ContextCompat.getColor(context, android.R.color.transparent)
            this.textColor = ContextCompat.getColor(context, R.color.colorTextGray)
            if (normalDrawable != null) {
                this.setCompoundDrawablesWithIntrinsicBounds(null, normalDrawable, null, null)
            }
        }
    }

   /* public inline fun ViewManager.imageTextView() = imageTextView {}
    public inline fun ViewManager.imageTextView(init: imageTextView.() -> Unit) = ankoView({ imageTextView(it) }, init)
*/
}

