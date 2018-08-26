package com.binguner.xieyi.fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.binguner.xieyi.R
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx


lateinit var helpAty:Activity

class Setting_Help_Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val container = SettingHelpFragmentUI().createView(AnkoContext.Companion.create(ctx, Setting_Help_Fragment()))
        return container
    }


    fun attachAty(activity: Activity){
        helpAty = activity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }


    companion object {
        @JvmStatic
        fun newInstance() =
                Setting_Help_Fragment().apply {

                }
    }
}

class SettingHelpFragmentUI:AnkoComponent<Setting_Help_Fragment>{

    val id_help_toolbar = View.generateViewId()
    val id_help_shadow = View.generateViewId()

    override fun createView(ui: AnkoContext<Setting_Help_Fragment>) = with(ui){

        constraintLayout {
            backgroundColor = ContextCompat.getColor(ctx,R.color.colorTransparent)
            val help_toolbar = include<View>(R.layout.toolbar_layout){
                id = id_help_toolbar
            }.lparams(width = matchParent){
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            imageView(){
                setImageResource(R.drawable.ic_arrow_back_black_24dp)
                onClick {
                    helpAty?.finish()
                }
            }.lparams(){
                topToTop = id_help_toolbar
                bottomToBottom = id_help_toolbar
                startToStart = id_help_toolbar
                leftMargin = dip(10)
            }

            val help_shadow = include<View>(R.layout.shadow_line){
                id = id_help_shadow
            }.lparams(width = matchParent){
                topToBottom = id_help_toolbar
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }


        }
    }

}
