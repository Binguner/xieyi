package com.binguner.xieyi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.content.ContextCompat
import android.view.View
import com.binguner.xieyi.utils.StatusBarUtil
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActitivyUI().setContentView(this)
        StatusBarUtil.transparentStatusBar(this)
        StatusBarUtil.setStatusBarColor(this,R.color.colorNormalBack)
        StatusBarUtil.setStatusBarTextBalck(this)
    }
}

var phoneNumber = -1

class LoginActitivyUI:AnkoComponent<LoginActivity>{

    val id_appname = View.generateViewId()
    val id_app_des = View.generateViewId()
    val id_app_phone_pass_name_image = View.generateViewId()
    val id_app_phone_pass_name_text= View.generateViewId()
    val id_app_phone_pass_name_ed= View.generateViewId()
    val id_app_phone_pass_name_shadow= View.generateViewId()
    val id_app_phone_pass_name_ok_btn= View.generateViewId()

    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui) {

        constraintLayout {

            backgroundColor = ContextCompat.getColor(ctx, R.color.colorNormalBack)
            textView(){
                text = "「协易」"
                id = id_appname
                textSize = 60f
            }.lparams(){
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topMargin = dip(40)
            }

            textView(){
                id = id_app_des
                text = "—基于区块链技术的协议签订与分享平台—"
            }.lparams(){
                topToBottom = id_appname
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topMargin = dip(10)
            }

            val app_phone_pass_name_image = imageView(){
                id = id_app_phone_pass_name_image
                setImageResource(R.drawable.ic_phone_android_grey_800_24dp)
            }.lparams(){
                topToBottom = id_app_des
                startToStart = PARENT_ID
                topMargin = dip(70)
                leftMargin = dip(40)
            }

            val app_phone_pass_name_text = textView(){
                id  = id_app_phone_pass_name_text
                text = "电话"
                textSize = 17f
            }.lparams(){
                topToTop = id_app_phone_pass_name_image
                bottomToBottom = id_app_phone_pass_name_image
                startToEnd = id_app_phone_pass_name_image
                leftMargin = dip(15)
            }

            val app_phone_pass_name_ed = editText(){
                id = id_app_phone_pass_name_ed
                hint = "请输入手机号码："
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorNormalBack)

                textChangedListener {
                    afterTextChanged {
                        phoneNumber = this@editText.text.toString().toInt()
                    }
                }
            }.lparams(width = matchParent){
                topToBottom = id_app_phone_pass_name_image
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topMargin = dip(20)
                leftMargin = dip(38)
                rightMargin = dip(38)
            }

            val app_phone_pass_name_shadow = imageView(){
                id = id_app_phone_pass_name_shadow
                setImageResource(R.color.colorLightGrey)
            }.lparams(width = matchParent, height = dip(2) ){
                topToBottom = id_app_phone_pass_name_ed
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                leftMargin = dip(38)
                rightMargin = dip(38)
            }

            val app_phone_pass_name_ok_btn = button("确定"){
                id = id_app_phone_pass_name_ok_btn
                setBackgroundResource(R.drawable.protocol_publish_btn)
                onClick {
                    startActivity<MainActivity>()
                }
            }.lparams(height = dip(40)){
                topToBottom = id_app_phone_pass_name_shadow
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topMargin = dip(30)
            }




        }

    }

}
