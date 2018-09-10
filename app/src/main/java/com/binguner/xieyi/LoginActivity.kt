package com.binguner.xieyi

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.content.ContextCompat
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import com.binguner.xieyi.RxUtils.HttpClient
import com.binguner.xieyi.databases.DBUtils
import com.binguner.xieyi.databases.database
import com.binguner.xieyi.listeners.ResultListener
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
        userType = newUser
    }

    override fun onBackPressed() {
        this.finish()
    }
}
val newUser = 0
val oldUser = 1
var userType = newUser
var phoneNumber = ""
var username = ""
var password = ""
lateinit var httpClient :HttpClient

class LoginActitivyUI:AnkoComponent<LoginActivity>{

    val id_appname = View.generateViewId()
    val id_app_des = View.generateViewId()
    val id_app_phone_pass_name_image = View.generateViewId()
    val id_app_phone_pass_name_text= View.generateViewId()
    val id_app_phone_pass_name_ed= View.generateViewId()
    val id_app_phone_pass_name_shadow= View.generateViewId()
    val id_app_phone_pass_name_ok_btn= View.generateViewId()
    val id_app_phone_pass_name_ok_already_have= View.generateViewId()
    val id_app_phone_pass_name_eye= View.generateViewId()
    val type_phone = 0
    val type_username = 1
    val type_passwor = 2
    var type_flag = -1
    lateinit var editor :SharedPreferences.Editor

    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui) {

        val db = ctx.database.writableDatabase
        type_flag = type_phone
        httpClient = HttpClient(ctx)
        editor = ctx.getSharedPreferences("UserData",Context.MODE_PRIVATE).edit()

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
                if(type_flag == type_phone){
                    id = id_app_phone_pass_name_image
                    setImageResource(R.drawable.ic_phone_android_grey_800_24dp)
                }
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
                this@editText.textColor = ContextCompat.getColor(ctx,R.color.colorBlack)
                hint = "请输入手机号码："
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorNormalBack)
                inputType = InputType.TYPE_CLASS_NUMBER


                textChangedListener {


                    beforeTextChanged { charSequence1, i1, i2, i3 ->
                        when(type_flag){
                            0 -> {
                                this@editText.inputType = InputType.TYPE_CLASS_NUMBER
                            }
                            1 -> {
                                this@editText.inputType = InputType.TYPE_CLASS_TEXT
                            }
                            2 ->{
                                //this@editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                                //this@editText.transformationMethod = PasswordTransformationMethod.getInstance()
                            }
                        }
                    }

                    afterTextChanged {
                        when (type_flag) {
                            0 -> {
                                phoneNumber = this@editText.text.toString()
                                if(phoneNumber.length > 11){
                                    this@editText.textColor = ContextCompat.getColor(ctx,R.color.colorRed)
                                }else{
                                    this@editText.textColor = ContextCompat.getColor(ctx,R.color.colorBlack)
                                }
                            }
                            1 -> {
                                username = this@editText.text.toString()
                                if(username.equals("")){
                                   //toast("请输入用户名！")
                                }else{
                                    username = this@editText.text.toString()
                                }
                            }
                            2 -> {
                                password = this@editText.text.toString()
                                if(password.equals("")){
                                    //toast("请输入密码！")
                                }else{
                                    password = this@editText.text.toString()
                                }
                            }
                        }
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

            val app_phone_pass_name_ok_already_have = textView(){
                id = id_app_phone_pass_name_ok_already_have
                text = "已经注册"
                onClick {
                    userType = oldUser
                    app_phone_pass_name_ed.setText("")
                    app_phone_pass_name_image.setImageResource(R.drawable.ic_person_outline_grey_800_24dp)
                    app_phone_pass_name_text.text = "用户名"
                    type_flag = type_username
                    app_phone_pass_name_ed.hint = "请输入用户名"
                    app_phone_pass_name_ed.inputType = InputType.TYPE_CLASS_TEXT

                }
            }.lparams(){
                topToBottom = id_app_phone_pass_name_ed
                endToEnd = id_app_phone_pass_name_ed
                topMargin = dip(20)
                rightMargin = dip(5)

            }

            val app_phone_pass_name_eye = imageView(){
                id = id_app_phone_pass_name_eye
                setImageResource(R.drawable.ic_remove_red_eye_blue_grey_200_24dp)
                visibility = View.INVISIBLE
                var visiable = false
                onClick {
                    if(type_flag == type_passwor && visiable == false){
                        visiable = true
                        this@imageView.setImageResource(R.drawable.ic_remove_red_eye_grey_900_24dp)
                        //app_phone_pass_name_ed.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        app_phone_pass_name_ed.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    }
                    else if(visiable == true && type_flag == type_passwor){
                        visiable = false
                        this@imageView.setImageResource(R.drawable.ic_remove_red_eye_blue_grey_200_24dp)
                        app_phone_pass_name_ed.inputType = InputType.TYPE_CLASS_TEXT and InputType.TYPE_TEXT_VARIATION_PASSWORD
                        app_phone_pass_name_ed.transformationMethod = PasswordTransformationMethod.getInstance()
                    }
                }
            }.lparams(){
                bottomToBottom = id_app_phone_pass_name_ed
                endToEnd = id_app_phone_pass_name_ed
                rightMargin = dip(10)
                bottomMargin = dip(6)

            }

            val app_phone_pass_name_ok_btn = button("确定"){
                id = id_app_phone_pass_name_ok_btn
                setBackgroundResource(R.drawable.protocol_publish_btn)
                onClick {
                    when(type_flag){
                        type_phone -> {
                            app_phone_pass_name_eye.visibility = View.INVISIBLE
                            if(phoneNumber.length != 11 ){
                                toast("请输入正确的号码！")
                            }else{
                                app_phone_pass_name_ed.setText("")
                                app_phone_pass_name_image.setImageResource(R.drawable.ic_person_outline_grey_800_24dp)
                                app_phone_pass_name_text.text = "用户名"
                                type_flag = type_username
                                app_phone_pass_name_ed.hint = "请输入用户名"
                                app_phone_pass_name_ed.inputType = InputType.TYPE_CLASS_TEXT
                            }
                        }
                        type_username -> {
                            app_phone_pass_name_ok_already_have.visibility = View.INVISIBLE
                            if(username != ""){
                                type_flag = type_passwor
                                app_phone_pass_name_eye.visibility = View.VISIBLE
                                app_phone_pass_name_ed.setText("")
                                app_phone_pass_name_image.setImageResource(R.drawable.ic_lock_outline_grey_800_24dp)
                                app_phone_pass_name_text.text = "密码"
                                app_phone_pass_name_ed.hint = "请输入密码"
                                app_phone_pass_name_ed.transformationMethod = PasswordTransformationMethod.getInstance()
                            }else{
                                toast("请输入用户名！")
                            }

                        }
                        type_passwor -> {
                            if(!password.equals("")){
                                when(userType){
                                    newUser ->{
                                        httpClient.doRegister(phoneNumber, username, password,object: ResultListener {
                                            override fun postResullt(resultType: Int, msg: String) {
                                                when (resultType) {
                                                    /*ResultListener.nextType -> {
                                                       // if(!msg.equals("")){
                                                            toast(msg)
                                                        if (msg.equals("注册成功")) {
                                                            toast(msg)
                                                            startActivity<MainActivity>()
                                                            editor.putBoolean("isLoging",true)
                                                            editor.commit()
                                                            owner.finish()
                                                        }
                                                        //}else if(msg.contains("登录失败")){
                                                          //  toast("登陆失败，请检查用户名和密码是否正确!")
                                                        //}
                                                    }*/
                                                    ResultListener.succeedType -> {
                                                        //123toast(msg)
                                                        //Thread.sleep(1000)
                                                        if (msg.equals("注册成功")) {
                                                            toast(msg)
                                                            editor.putBoolean("isLoging",true)
                                                            editor.commit()
                                                            startActivity<MainActivity>()
                                                            owner.finish()
                                                        }
                                                    }
                                                    ResultListener.errorType -> {
                                                        if (!msg.equals("")) {
                                                            toast(msg)
                                                        }
                                                    }
                                                    ResultListener.failedType -> {
                                                        toast(msg)
                                                    }
                                                }
                                            }
                                        })
                                    }
                                    oldUser ->{
                                        httpClient.doLogin(username, password,object :ResultListener{
                                            override fun postResullt(resultType: Int, msg: String) {
                                                when (resultType){
                                                    ResultListener.nextType ->{
                                                        /*if(msg.equals("登录成功")){
                                                            //Thread.sleep(1000)
                                                            editor.putBoolean("isLoging",true)
                                                            editor.commit()
                                                            startActivity<MainActivity>()
                                                            owner.finish()
                                                        }else if(msg.equals("登录失败")){
                                                            toast("登陆失败，请检查用户名和密码！")
                                                        }*/
                                                    }
                                                    ResultListener.succeedType ->{
                                                        if(msg.equals("登录成功")){
                                                            toast(msg)
                                                            editor.putBoolean("isLoging",true)
                                                            editor.commit()
                                                            startActivity<MainActivity>()
                                                            owner.finish()
                                                        }
                                                    }
                                                    ResultListener.failedType -> {
                                                        toast("登录失败,请检查用户名和密码是否正确")
                                                    }
                                                    ResultListener.errorType ->{

                                                        toast(msg)
                                                    }
                                                }
                                            }
                                        })
                                    }
                                }
                                //startActivity<MainActivity>()
                            }else{
                                toast("请输入密码！")
                            }
                        }
                    }
                    //startActivity<MainActivity>()
                }
            }.lparams(height = dip(40)){
                topToBottom = id_app_phone_pass_name_ok_already_have
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topMargin = dip(30)
            }

        }

    }



}
