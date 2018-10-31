package com.binguner.xieyi.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.binguner.xieyi.R
import com.binguner.xieyi.beans.ProtocolDetailBean
import com.binguner.xieyi.databases.DBUtils
import com.binguner.xieyi.fragments.dbUtils
import com.binguner.xieyi.utils.StatusBarUtil
import org.jetbrains.anko.toast

class ProtocolDetial : AppCompatActivity() {

    private lateinit var pro_id : String
    private lateinit var  myThread : CheckProtocolThread
    private val myHandler = SetProHandler()
    private lateinit var text_owner :TextView
    private lateinit var text_time :TextView
    private lateinit var text_content :TextView
    private lateinit var pro_detial_title :TextView
    private lateinit var pro_detial_signatory_had :TextView
    private lateinit var pro_detial_signatory_all :TextView
    private lateinit var pro_detial_signatory_list :TextView
    private lateinit var pro_detial_type :ImageView
    private lateinit var pro_detial_back :ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protocol_detial)
        transparentStatusbar()
        initId();
        getDatas()

    }
    fun initId(){
        text_owner = findViewById(R.id.pro_detial_owner_ed)
        text_time = findViewById(R.id.pro_detial_time_ed)
        text_content = findViewById(R.id.pro_detial_content)
        pro_detial_title = findViewById(R.id.pro_detial_title)
        pro_detial_type = findViewById(R.id.pro_detial_type)
        pro_detial_back = findViewById(R.id.pro_detial_back)
        pro_detial_back.setOnClickListener {
            this.finish()
        }
        pro_detial_signatory_had = findViewById(R.id.pro_detial_signatory_had)
        pro_detial_signatory_all = findViewById(R.id.pro_detial_signatory_all)
        pro_detial_signatory_list = findViewById(R.id.pro_detial_signatory_list)
    }

    fun getDatas(){
        pro_id = intent.extras.getString("pro_id")
        myThread = CheckProtocolThread(this,pro_id)
        Thread(myThread).start()
    }

    fun transparentStatusbar(){
        StatusBarUtil.transparentStatusBar(this)
        StatusBarUtil.setStatusBarColor(this,R.color.colorWhite)
        StatusBarUtil.setStatusBarTextBalck(this)

    }


    inner class SetProHandler:Handler(){

        private lateinit var protocolDetialBean: ProtocolDetailBean
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what){
                0 -> {
                    pro_detial_type.setImageResource(R.drawable.protocol)
                    protocolDetialBean = msg.obj as ProtocolDetailBean
                    text_owner.text = protocolDetialBean.owner
                    pro_detial_title.text = protocolDetialBean.title
                    text_content.text = protocolDetialBean.content
                    text_time.text = protocolDetialBean.created_at.split("T")[0].replace("-",".")
                    pro_detial_signatory_had.text = dbUtils.getTheSignedNumber(protocolDetialBean._id).toString()
                    pro_detial_signatory_all.text = protocolDetialBean.signatoryNum
                    pro_detial_signatory_list.text = dbUtils.getTheSignedPeopleName(protocolDetialBean._id)
                    //toast("finish")
                }
                1 -> {
                    pro_detial_type.setImageResource(R.drawable.floater)
                    protocolDetialBean = msg.obj as ProtocolDetailBean
                    pro_detial_title.text = protocolDetialBean.title
                    text_owner.text = protocolDetialBean.owner
                    text_content.text = protocolDetialBean.content
                    pro_detial_signatory_had.text = dbUtils.getTheSignedNumber(protocolDetialBean._id).toString()
                    pro_detial_signatory_all.text = "2"
                    text_time.text = protocolDetialBean.created_at.split("T")[0].replace("-",".")
                    pro_detial_signatory_list.text = dbUtils.getTheSignedPeopleName(protocolDetialBean._id)

                }
                -1 -> {
                    Looper.prepare()
                    toast("未找到该协议详情！")
                    Looper.loop()
                }
            }
        }

    }

    inner class CheckProtocolThread(val ctx: Context, val pro_id: String) : Runnable {

        private val dbUtils = DBUtils(ctx)
        private val sp = ctx.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        private lateinit var msg: Message
        private lateinit var bean: ProtocolDetailBean

        override fun run() {
            val type = dbUtils.getProType(pro_id)
            msg = Message()
            when (type) {
                "0" -> {
                    bean = dbUtils.getNormalProtocolDetail(pro_id)
                    msg.what = 0
                    msg.obj = bean
                }
                "1" -> {
                    bean = dbUtils.getFloaterProtocolDetail(pro_id)
                    msg.what = 1
                    msg.obj = bean
                }
                else -> {
                    msg.what = -1
                }
            }
            this@ProtocolDetial.myHandler.handleMessage(msg)

        }

    }
}


