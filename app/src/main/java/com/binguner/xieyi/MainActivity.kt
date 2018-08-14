package com.binguner.xieyi

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.content.ContextCompat
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        MainActivityUI().setContentView(this)
    }


}

class MainActivityUI: AnkoComponent<MainActivity>{
    val id_toolbar = View.generateViewId()
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        constraintLayout(){
            backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)
            //id = id_toolbar
            var mtoolbar = toolbar{
                id = id_toolbar
                backgroundColor = Color.WHITE
            }.lparams(width = matchParent)





            applyConstraintSet {
                mtoolbar{
                    connect(
                            TOP to TOP of PARENT_ID
                    )
                }
            }
        }

    }

}
