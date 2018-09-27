package com.binguner.xieyi.activities

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.print.PageRange
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.binguner.xieyi.R
import com.binguner.xieyi.fragments.*
import com.binguner.xieyi.utils.MyImageLoader
import com.binguner.xieyi.utils.StatusBarUtil
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk25.coroutines.onClick


var flag:Int = -1

/**
 * 0 seting
 * 1 my protocol
 * 2 feedback
 * 3 help
 */
class SettingActivity : AppCompatActivity() ,UserInfoChangedListener,OnSelectToFinishCallback{
    override fun isChanged(type: Int, changged: Boolean) {
        //Log.d("tetete","changged + $changged")
        if(changged){
            val intent = Intent()
            when(type){
                1 -> {
                    setResult(1,intent)
                }
                2 -> {
                    setResult(2,intent)
                }
            }
        }
    }

    override fun selected() {
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SettingActivityUI().setContentView(this)
        StatusBarUtil.setStatusBarColor(this,R.color.colorWhite)
        StatusBarUtil.setStatusBarTextBalck(this)
        //setContentView()

        flag = intent.extras.getInt("flag")
        initFragments()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123){
            if (null != data){
                var image = data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS)
                //toast("${image)}")
            }
        }
    }

    private fun initFragments() {
        myFragmentManager = supportFragmentManager

        if(null == settingPersonFragment){
            settingPersonFragment = Setting_Person_Fragment.newInstance()
        }
        if(null == settingMyProFragment){
            settingMyProFragment = Setting_MyProFragment.newInstance()
        }
        if(null == setting_Feedback_Fragment){
            setting_Feedback_Fragment = Setting_Feedback_Fragment.newInstance()
        }
        if(null == setting_Help_Fragment){
            setting_Help_Fragment = Setting_Help_Fragment.newInstance()
        }

        when(flag){
            0 ->{
                selectFragment(settingPersonFragment!!)
                settingPersonFragment!!.attachAty(this)
                //Log.d("tttttt","selectFragment")z
            }
            1 ->{
                selectFragment(settingMyProFragment!!)
                settingMyProFragment?.attachActivity(this)
            }
            2 ->{
                selectFragment(setting_Feedback_Fragment!!)
                setting_Feedback_Fragment?.attachAty(this)
            }
            3 ->{
                selectFragment(setting_Help_Fragment!!)
                setting_Help_Fragment?.attachAty(this)
            }
        }
        /*myFragmentManager.beginTransaction()
                .add(containerid, settingPersonFragment)
                .show(settingPersonFragment)
                .commit()*/
    }

    private fun selectFragment(fragment: Fragment){
        myFragmentManager!!.beginTransaction()
                .add(containerid, fragment)
                .show(fragment)
                .commit()
    }
}

var settingPersonFragment : Setting_Person_Fragment ?= null
var settingMyProFragment: Setting_MyProFragment ?= null
var setting_Feedback_Fragment: Setting_Feedback_Fragment ?= null
var setting_Help_Fragment: Setting_Help_Fragment?= null

var myFragmentManager : FragmentManager ?= null
val containerid = View.generateViewId()

class SettingActivityUI:AnkoComponent<SettingActivity>{

    val id_setting_frame = View.generateViewId()

    override fun createView(ui: AnkoContext<SettingActivity>) = with(ui){

        constraintLayout {

            val set_framelayout = frameLayout {
                id = containerid
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorNormalBack)
            }.lparams(width = matchParent, height = matchParent){
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
            }
        }

    }
}
