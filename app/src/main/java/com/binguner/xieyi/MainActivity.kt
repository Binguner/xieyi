package com.binguner.xieyi

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getColor
import android.text.Layout
import android.view.ViewManager
import android.view.WindowManager
import com.binguner.xieyi.utils.StatusBarUtil
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        MainActivityUI().setContentView(this)
        setTransparentStatusbar();
    }

    private fun setTransparentStatusbar() {
        StatusBarUtil.setStatusBarColor(this,R.color.colorWhite)
        StatusBarUtil.setStatusBarTextBalck(this)
    }


}

class MainActivityUI: AnkoComponent<MainActivity>{
    val id_toolbar = View.generateViewId()
    val id_shadow_line = View.generateViewId()
    val id_btn1 = View.generateViewId()
    val id_btn2 = View.generateViewId()
    val id_btn3 = View.generateViewId()


    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        constraintLayout(){
            backgroundColor = getColor(ctx,R.color.colorNormalBack)

            var mtoolbar = toolbar{
                id = id_toolbar
                backgroundColor = Color.WHITE
            }.lparams(width = matchParent)

            include<View>(R.layout.shadow_line){
                id = id_shadow_line
                fitsSystemWindows = true
            }.lparams(width = matchParent, height = wrapContent){
                topToBottom = id_toolbar
                fitsSystemWindows = true
            }

            val btn1 = imageView(R.drawable.ic_crop_square_black_24dp){
                id = id_btn1
            }.lparams(width = dip(30)){
                padding = dip(10)
            }
            val btn2 = imageView(R.drawable.ic_add_black_24dp){
                id = id_btn2
            }.lparams(width = dip(30)){
                padding = dip(10)

            }
            val btn3 = imageView(R.drawable.ic_person_black_24dp){
                id = id_btn3
            }.lparams(width = dip(30)){
                padding = dip(10)
            }


            applyConstraintSet {
                connect(
                        START of btn1 to START of PARENT_ID,
                        END of btn1 to START of btn2,
                        START of btn2 to END of btn1,
                        END of btn2 to START of btn3,
                        START of btn3 to END of btn2,
                        END of btn3 to END of PARENT_ID,
                        BOTTOM of btn1 to BOTTOM of PARENT_ID,
                        BOTTOM of btn2 to BOTTOM of PARENT_ID,
                        BOTTOM of btn3 to BOTTOM of PARENT_ID
                )
                mtoolbar{
                    connect(
                            TOP to TOP of PARENT_ID
                    )
                }

            }

        }

    }

}
