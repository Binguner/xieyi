package com.binguner.xieyi.fragments

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.binguner.xieyi.R
import com.binguner.xieyi.RxUtils.HttpClient
import com.binguner.xieyi.listeners.ResultListener
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx

class Setting_Feedback_Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val container = SettingFeedbackFragmentUI().createView(AnkoContext.Companion.create(ctx,Setting_Feedback_Fragment()))
        return container
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    fun attachAty(activity: Activity){
        feedbackAty = activity
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                Setting_Feedback_Fragment().apply {
                }
    }


}

lateinit var feedbackAty:Activity
var feedContent:String = ""
var feedPhoneNnmber:String = ""
var feedQQ:String = ""

class SettingFeedbackFragmentUI:AnkoComponent<Setting_Feedback_Fragment>{
    override fun createView(ui: AnkoContext<Setting_Feedback_Fragment>) = with(ui){

        val id_feed_toolbar = View.generateViewId()
        val id_feed_shadow= View.generateViewId()
        val id_feed_back= View.generateViewId()
        val id_feed_content= View.generateViewId()
        val id_feed_qq= View.generateViewId()
        val id_feed_phone= View.generateViewId()
        val id_feed_ok= View.generateViewId()
        val httpClient = HttpClient(ctx)

        constraintLayout {
            backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)

            val feed_toolbar = include<View>(R.layout.toolbar_layout){
                id = id_feed_toolbar
            }.lparams(width = matchParent){
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            val feed_shadow = include<View>(R.layout.shadow_line){
                id = id_feed_shadow
            }.lparams(width = matchParent){
                topToBottom = id_feed_toolbar
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            val feed_back = imageView(){
                id = id_feed_back
                setImageResource(R.drawable.ic_arrow_back_black_24dp)
                onClick {
                    feedbackAty?.finish()
                }
            }.lparams(){
                topToTop = id_feed_toolbar
                bottomToBottom = id_feed_toolbar
                startToStart = id_feed_toolbar
                leftMargin = dip(10)
            }

            textView(){
                text = "意见反馈"
            }.lparams(){
                topToTop = id_feed_toolbar
                bottomToBottom = id_feed_toolbar
                startToStart = id_feed_toolbar
                endToEnd = id_feed_toolbar
            }

            val feed_content = editText(){
                id = id_feed_content
                gravity = Gravity.TOP
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                hint = "请输入您的建议或反馈"
                leftPadding = dip(20)
            }.lparams(width = matchParent, height = dip(0)){
                topToBottom  = id_feed_shadow
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                bottomToTop = id_feed_phone
                topMargin = dip(20)
            }

            val feed_phone = editText(){
                id = id_feed_phone
                singleLine = true
                leftPadding = dip(80)
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                inputType = InputType.TYPE_CLASS_PHONE
            }.lparams(width = matchParent){
                topToBottom = id_feed_content
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                bottomToTop = id_feed_qq
                topMargin = dip(10)
            }

            textView(){
                text = "联系电话："
                leftPadding = dip(10)
            }.lparams(){
                topToTop = id_feed_phone
                bottomToBottom = id_feed_phone
                startToStart = id_feed_phone
            }

            val feed_qq = editText(){
                id = id_feed_qq
                singleLine = true
                leftPadding = dip(40)
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                inputType = InputType.TYPE_CLASS_NUMBER

            }.lparams(width = matchParent){
                topToBottom = id_feed_phone
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                bottomToTop = id_feed_ok
                topMargin = dip(10)

            }

            textView(){
                text = "QQ:"
                leftPadding = dip(10)
            }.lparams(){
                topToTop = id_feed_qq
                bottomToBottom = id_feed_qq
                startToStart = id_feed_qq

            }

            val feed_ok = button(){
                id = id_feed_ok
                text = "反馈"
                onClick {
                    if (feed_content.text.toString().equals("")) {
                        toast("请输入反馈内容")
                    } else {
                        feedContent = feed_content.text.toString()
                        feedPhoneNnmber = feed_phone.text.toString()
                        feedQQ = feed_qq.text.toString()
                        if (!feedContent.equals("")) {
                            httpClient.giveFeedback(feedContent, feedQQ, feedPhoneNnmber, "", object : ResultListener {
                                override fun postResullt(resultType: Int, msg: String) {
                                    when (resultType) {
                                        ResultListener.succeedType -> {
                                            toast(msg)
                                            feed_content.setText("")
                                            feed_phone.setText("")
                                            feed_qq.setText("")
                                        }
                                    }
                                }

                            })
                        } /*else {
                            toast("反馈内容不能为空！")
                        }*/
                    }

                }

                setBackgroundResource(R.drawable.protocol_publish_btn)
            }.lparams(){
                topToBottom = id_feed_qq
                topMargin = dip(20)
                bottomToBottom = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                bottomMargin = dip(100)
            }



        }

    }

}
