package com.binguner.xieyi.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binguner.xieyi.LoginActivity
import com.binguner.xieyi.MainActivity

import com.binguner.xieyi.R
import com.binguner.xieyi.activities.SettingActivity
import com.binguner.xieyi.databases.DBUtils
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx

lateinit var personActivity: MainActivity


lateinit var onselected2finishAty : OnSelectToFinishCallback

class PersonFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val person_view = PersonFragmentUI().createView(AnkoContext.Companion.create(ctx,PersonFragment()))
        return person_view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSelectToFinishCallback){
            onselected2finishAty = context
        }
    }



    override fun onDetach() {
        super.onDetach()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    fun attachAtyy(activity: MainActivity){
        personActivity = activity
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                PersonFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }


}

lateinit var sharedPreferences :SharedPreferences
class PersonFragmentUI: AnkoComponent<PersonFragment>{

    val id_person_toolbar = View.generateViewId()
    val id_person_shadow= View.generateViewId()
    val id_person_tiele= View.generateViewId()
    val id_person_avator= View.generateViewId()
    val id_person_username= View.generateViewId()
    val id_person_email= View.generateViewId()
    val id_person_my_protocol= View.generateViewId()
    val id_person_check_update= View.generateViewId()
    val id_person_share_app= View.generateViewId()
    val id_person_feedback= View.generateViewId()
    val id_person_help= View.generateViewId()
    val id_person_logout= View.generateViewId()
    val id_person_setting= View.generateViewId()
    lateinit var dbUtils:DBUtils
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun createView(ui: AnkoContext<PersonFragment>) = with(ui) {
        constraintLayout {

            backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)

            dbUtils = DBUtils(ctx)
            sharedPreferences = ctx.getSharedPreferences("UserData",Context.MODE_PRIVATE)
            editor = ctx.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()

            include<View>(R.layout.toolbar_layout){
                id = id_person_toolbar
            }.lparams(width = matchParent, height = wrapContent){
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }
            include<View>(R.layout.shadow_line){
                id = id_person_shadow
            }.lparams(width = matchParent, height = wrapContent){
                topToBottom = id_person_toolbar
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            textView(){
                id = id_person_tiele
                text = "个人中心"
                textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
            }.lparams(width = wrapContent, height = wrapContent){
                topToTop = id_person_toolbar
                bottomToBottom = id_person_toolbar
                startToStart = id_person_toolbar
                endToEnd = id_person_toolbar
            }

            val person_avator = include<View>(R.layout.circle_imageview){
                id = id_person_avator
            }.lparams(){
                topToBottom = id_person_shadow
                topMargin = dip(20)
                startToStart = PARENT_ID
                leftMargin = dip(20)
            }

            val person_username = textView(sharedPreferences.getString("username","null")){
                id = id_person_username
                textSize = 20f
                textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
            }.lparams(){
                startToEnd = id_person_avator
                leftMargin = dip(20)
                topToTop = id_person_avator
                topMargin = dip(10)
            }

            val person_email = textView(sharedPreferences.getString("email","请到设置页面设置邮箱地址！")){
                id = id_person_email

            }.lparams(height = dip(60)){
                startToEnd = id_person_avator
                leftMargin = dip(20)
                topToBottom = id_person_username
                topMargin = dip(5)
            }

            val person_setting = imageView(){
                id = id_person_setting
                imageResource = R.drawable.ic_settings_cyan_400_24dp
                onClick {
                    //toast("Setting")
                    startActivity<SettingActivity>("flag" to 0)
                }
            }.lparams(){
                endToEnd = PARENT_ID
                topToTop = id_person_avator
                bottomToBottom = id_person_avator
                rightMargin = dip(30)
            }

            button("我的协议"){
                id = id_person_my_protocol
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)

                //gravity = Gravity.CENTER_VERTICAL

                onClick {
                    //toast("My Protocol")
                    startActivity<SettingActivity>("flag" to 1)
                }
            }.lparams(width = matchParent, height = dip(40)){
                topToBottom = id_person_avator
                topMargin = dip(20)
            }

            button("检查更新"){
                id = id_person_check_update
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                onClick {
                    //toast("Check update")
                }
            }.lparams(width = matchParent, height = dip(40)){
                topToBottom = id_person_my_protocol
                topMargin = dip(10)

            }

            button("分享协易"){
                id = id_person_share_app
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                onClick {
                    //toast("Share this app")
                    /*val intent = Intent(android.content.Intent.ACTION_SEND)
                    intent.type = "text/plain"*/
                    val text = "欢迎使用「协易」——基于区块链技术的协议签订与分享平台。\n下载地址：https://fir.im/xieyi"
                    /*intent.putExtra(android.content.Intent.EXTRA_TEXT, text)
                    startActivity(Intent.createChooser(intent, null))*/
                    share(text,"what?")
                }
            }.lparams(width = matchParent, height = dip(40)){
                topToBottom = id_person_check_update
                topMargin = dip(10)
            }

            button("意见反馈"){
                id = id_person_feedback
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                onClick {
                    //toast("Feedback sth")
                    startActivity<SettingActivity>("flag" to 2)
                }
            }.lparams(width = matchParent, height = dip(40)){
                topToBottom = id_person_share_app
                topMargin = dip(10)
            }

            button("使用帮助"){
                id = id_person_help
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                onClick {
                    //toast("Help")
                    startActivity<SettingActivity>("flag" to 3)
                }
            }.lparams(width = matchParent, height = dip(40)){
                topToBottom = id_person_feedback
                topMargin = dip(10)
            }

            button("退出账号"){
                id = id_person_logout
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                onClick {
                    //Log.d("tetete",sharedPreferences.getString("username",""))
                    dbUtils.deleteAccout(sharedPreferences.getString("username",""))
                    //toast("退出成功")
                    editor.putBoolean("isLoging",false)
                    editor.commit()
                    //Log.d("tetete", "${personActivity}")
                    //personActivity.finishAty()
                    onselected2finishAty.selected()
                    startActivity<LoginActivity>()
                }
            }.lparams(width = matchParent, height = dip(40)){
                topToBottom = id_person_help
                topMargin = dip(10)
            }

        }
    }

}

public interface OnSelectToFinishCallback{

    fun selected()
}