package com.binguner.xieyi.activities

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.binguner.xieyi.R
import org.jetbrains.anko.constraint.layout.constraintLayout
import android.support.constraint.ConstraintSet.PARENT_ID
import com.binguner.xieyi.utils.StatusBarUtil
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.cardview.v7.cardView

class ProrocolDetial : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ProtocolDetialUI()
        view.prorocolDetial = this
        view.setContentView(this)
        transpatentStatusbar()
    }

    fun  transpatentStatusbar(){
        StatusBarUtil.transparentStatusBar(this)
        StatusBarUtil.setStatusBarTextBalck(this)
        StatusBarUtil.setStatusBarColor(this,ContextCompat.getColor(this,R.color.colorWhite))
    }
}

class ProtocolDetialUI:AnkoComponent<ProrocolDetial>{

    lateinit var prorocolDetial: ProrocolDetial
    private var id_toolbar = 0
    private var id_typecard = 0
    private var id_shadow = 0
    private var id_scroll = 0

    override fun createView(ui: AnkoContext<ProrocolDetial>)= with(ui){

        constraintLayout {
            fitsSystemWindows = true
            backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)

            toolbar {
                id_toolbar = View.generateViewId()
                id = id_toolbar
                backgroundColor = ContextCompat.getColor(ctx,R.color.colorWhite)
                navigationIcon = ContextCompat.getDrawable(ctx,R.drawable.ic_arrow_back_black_24dp)
                setNavigationOnClickListener {
                    prorocolDetial.finish()
                }


                textView ("协议详情"){
                    textColor = ContextCompat.getColor(ctx,R.color.colorBlack)
                }.lparams(){
                    gravity = Gravity.CENTER
                }
            }.lparams(width = matchParent)

            view(){
                id_shadow = View.generateViewId()
                id = id_shadow
                background = ContextCompat.getDrawable(ctx,R.drawable.shadow_line)
            }.lparams(width = matchParent, height = dip(5)){
                topToBottom = id_toolbar
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            scrollView {
                id_scroll = View.generateViewId()
                id = id_scroll

                constraintLayout {

                    /*cardView {
                        imageView(){
                            id_typecard = View.generateViewId()
                            id = id_typecard
                            imageResource = R.drawable.floater
                        }.lparams(width = matchParent,height = matchParent)
                    }.lparams(width = matchParent,height = dip(40)){
                        topToTop = PARENT_ID
                        startToStart = PARENT_ID
                        endToEnd = PARENT_ID
                    }*/

                }.lparams(width = matchParent,height = matchParent){

                }

            }.lparams(width = matchParent, height = matchParent){
                topToBottom = id_shadow
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                bottomToBottom = PARENT_ID
            }

        }
    }

}