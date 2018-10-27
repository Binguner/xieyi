package com.binguner.xieyi.activities

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import com.binguner.xieyi.R
import com.binguner.xieyi.RxUtils.HttpClient
import com.binguner.xieyi.httpClient
import com.binguner.xieyi.listeners.ResultListener
import com.binguner.xieyi.sharedPreferences
import com.binguner.xieyi.utils.StatusBarUtil
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

var type = 3
class CreateProtocolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //StatusBarUtil.transparentStatusBar(this)
        StatusBarUtil.setStatusBarColor(this,R.color.colorWhite)
        StatusBarUtil.setStatusBarTextBalck(this)
        type = 1
        CreateProtocolActivityUI().setContentView(this)
        //type = intent.extras.getInt("Type")
        sharedPreferences3 = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        prohttpClient = HttpClient(this)
    }
}

lateinit var sharedPreferences3: SharedPreferences
lateinit var prohttpClient: HttpClient

class CreateProtocolActivityUI:AnkoComponent<CreateProtocolActivity>{
    val id_createProToolbar = View.generateViewId()
    val id_createPro_shadow = View.generateViewId()
    val id_createPro_back_btn = View.generateViewId()
    val id_createPro_title = View.generateViewId()
    val id_createPro_title_ed = View.generateViewId()
    val id_createPro_conten_ed = View.generateViewId()
    val id_createPro_ok_btn = View.generateViewId()
    val id_createPro_from = View.generateViewId()
    val id_createPro_fromwhere = View.generateViewId()
    val id_createPro_choosePeoNum = View.generateViewId()
    lateinit var proTitle :String
    lateinit var proContent:String
    var proFromWhere = ""
    lateinit var createProFromWhere:EditText

    val items = arrayOf("  1  ", "  2  ", "  3  ", "  4  ", "  5  ")


    override fun createView(ui: AnkoContext<CreateProtocolActivity>)= with(ui) {

        constraintLayout(){
            background = ContextCompat.getDrawable(ctx, R.color.colorNormalBack)
            val createProToolbar = include<View>(R.layout.toolbar_layout){
                id = id_createProToolbar
            }.lparams(width = matchParent){
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            val createRro_shadoe = include<View>(R.layout.shadow_line){
                id = id_createPro_shadow
            }.lparams(width = matchParent){
                topToBottom = id_createProToolbar
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            val createPro_back = imageView(){
                id = id_createPro_back_btn
                setImageResource(R.drawable.ic_arrow_back_black_24dp)
                onClick {
                    val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(this@imageView.windowToken,0)
                    owner.finish()
                }
            }.lparams(){
                topToTop = id_createProToolbar
                bottomToBottom = id_createProToolbar
                startToStart = id_createProToolbar
                leftMargin = dip(10)
            }

            val createPro_title = textView(){
                id = id_createPro_title
                when (type){
                    0 -> text = "编写抖协议"
                    1 -> text = "编写漂流瓶"
                }
            }.lparams(){
                topToTop = id_createProToolbar
                bottomToBottom = id_createProToolbar
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            // title
            val createPro_title_ed = editText(){
                id = id_createPro_title_ed
                singleLine = true
                maxLines = 1
                when(type){
                    0 -> {
                        hint = "请输入抖协议标题："
                    }
                    1 -> {
                        hint = "请输入漂流瓶标题："
                    }
                }
                textChangedListener {
                    afterTextChanged {
                        proTitle = this@editText.text.toString()
                    }
                }
                setBackgroundResource(R.drawable.create_pro_title_edit)
            }.lparams(width = matchParent){
                topToBottom = id_createPro_shadow
                topMargin = dip(2)
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            // content
            val createPro_conten_ed = editText(){
                id = id_createPro_conten_ed
                gravity = Gravity.TOP
                when(type){
                    0 -> hint = "请输入抖协议内容"
                    1 -> hint = "请输入漂流瓶内容"
                }
                setBackgroundResource(R.drawable.create_pro_content)
                //singleLine = false
                textChangedListener {
                    afterTextChanged {
                        proContent = this@editText.text.toString()
                    }
                }

            }.lparams(width = matchParent, height = dip(0)){
                topToBottom = id_createPro_title_ed
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topMargin = dip(10)
                bottomMargin = dip(17)
                bottomToTop = id_createPro_from
            }
            // form where
            val createPrp_from = textView(){
                id = id_createPro_from
                when(type){
                    0 -> text = "签署人数"
                    1 -> text = "来自："
                }

            }.lparams(){
                startToStart = PARENT_ID
                leftMargin = dip(16)
                bottomToBottom = PARENT_ID
                bottomMargin = dip(16)

            }

            val createPro_ok_btn = button(){
                id = id_createPro_ok_btn
                setBackgroundResource(R.drawable.protocol_publish_btn)
                when (type){
                    0 -> text = "发表"
                    1 -> text = "漂流"
                }
                onClick {
                    when(type){
                        1 -> {
                            if(null == proTitle && proTitle == ""){
                                toast("请输入标题！")
                            }
                            if(null == proContent && proContent == ""){
                                toast("请输入内容！")
                            }else {
                                prohttpClient.createFloater(sharedPreferences3.getString("username", ""), proTitle, proContent, proFromWhere, object : ResultListener {
                                    override fun postResullt(resultType: Int, msg: String) {
                                        toast(msg)
                                        when (resultType) {
                                            ResultListener.succeedType -> {
                                                createPro_title_ed.setText("")
                                                createPro_conten_ed.setText("")
                                                createProFromWhere.setText("")
                                            }
                                            ResultListener.failedType -> {

                                            }
                                        }
                                    }
                                })
                            }
                        }
                    }
                }
            }.lparams(height = dip(35)){
                bottomToBottom = id_createPro_from
                topToTop = id_createPro_from
                endToEnd = PARENT_ID
                rightMargin = dip(8)
            }

            // number or from where
            when(type){
                0 -> {
                    val createProPeoNum = spinner(){
                        id = id_createPro_choosePeoNum
                        adapter = ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item, items)
                    }.lparams(){
                        startToEnd = id_createPro_from
                        leftMargin = dip(16)
                        topToTop = id_createPro_from
                        bottomToBottom = id_createPro_from
                    }
                }
                1 -> {
                     createProFromWhere = editText(){
                         id = id_createPro_fromwhere

                         setBackgroundResource(R.drawable.create_pro_title_edit)
                         hint = "神秘领域"
                         textChangedListener {
                             afterTextChanged {
                                 proFromWhere = this@editText.text.toString()
                             }
                         }
                    }.lparams( width = dip(0) ){
                        startToEnd = id_createPro_from
                        leftMargin = dip(16)
                        bottomToBottom = id_createPro_from
                        topToTop = id_createPro_from
                        endToStart = id_createPro_ok_btn
                        rightMargin = dip(20)
                    }
                }
            }
        }
    }
}
