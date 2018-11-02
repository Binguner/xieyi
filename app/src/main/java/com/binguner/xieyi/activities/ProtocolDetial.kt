package com.binguner.xieyi.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.binguner.xieyi.R
import com.binguner.xieyi.RxUtils.HttpClient
import com.binguner.xieyi.beans.ProtocolDetailBean
import com.binguner.xieyi.databases.DBUtils
import com.binguner.xieyi.fragments.dbUtils
import com.binguner.xieyi.httpClient
import com.binguner.xieyi.listeners.ResultListener
import com.binguner.xieyi.utils.StatusBarUtil
import org.jetbrains.anko.toast

class ProtocolDetial : AppCompatActivity() {

    private lateinit var pro_id : String
    private lateinit var  myThread : CheckProtocolThread
    private lateinit var myHandler :SetProHandler
    private lateinit var text_owner :TextView
    private lateinit var text_time :TextView
    private lateinit var text_content :TextView
    private lateinit var pro_detial_title :TextView
    private lateinit var pro_detial_signatory_had :TextView
    private lateinit var pro_detial_signatory_all :TextView
    private lateinit var pro_detial_signatory_list :TextView
    private lateinit var pro_detial_type :ImageView
    private lateinit var pro_detial_back :ImageView
    private lateinit var pro_detial_share :ImageView
    private lateinit var pro_detial_radio :ImageView
    private lateinit var pro_detial_sign :Button
    private lateinit var httpClient: HttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protocol_detial)
        myHandler = SetProHandler()
        transparentStatusbar()
        httpClient = HttpClient(this)
        initId()
        getDatas()
        initViews()

    }

    fun initViews(){
        when(dbUtils.getProType(pro_id)){
            "0"->{
                pro_detial_radio.visibility = View.VISIBLE
            }
            "1"->{
                pro_detial_radio.visibility = View.GONE
            }
        }
        when (dbUtils.getProtocolState(pro_id)){
            "0" -> {
                this@ProtocolDetial.pro_detial_radio.setImageResource(R.drawable.ic_radio_deep_orange_600_24dp)
            }
            "1" -> {
                this@ProtocolDetial.pro_detial_radio.setImageResource(R.drawable.ic_radio_black_24dp)
            }
        }
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
        pro_detial_sign = findViewById(R.id.pro_detial_sign)
        pro_detial_sign.setOnClickListener {
            when(dbUtils.getProType(pro_id)){
                "0" -> {
                    httpClient.signProtocol(pro_id,object :ResultListener{
                        override fun postResullt(resultType: Int, msg: String) {
                            toast(msg)
                            when(resultType){
                                1 -> {
                                    val checkThred = CheckProtocolThread(this@ProtocolDetial,pro_id)
                                    Thread(checkThred).start()
                                }
                                0 -> {
                                    val checkThred = CheckProtocolThread(this@ProtocolDetial,pro_id)
                                    Thread(checkThred).start()
                                }
                                else -> {
                                }
                            }
                        }

                    })
                }
                "1" -> {
                    httpClient.signFloater(pro_id,object :ResultListener{
                        override fun postResullt(resultType: Int, msg: String) {
                            toast(msg)
                            when(resultType){
                                1 -> {
                                    val checkThred = CheckProtocolThread(this@ProtocolDetial,pro_id)
                                    Thread(checkThred).start()
                                }
                                0 -> {
                                    val checkThred = CheckProtocolThread(this@ProtocolDetial,pro_id)
                                    Thread(checkThred).start()
                                }
                                else -> {
                                }
                            }
                        }
                    })
                }
            }

        }

        pro_detial_radio = findViewById(R.id.pro_detial_radio)
        pro_detial_radio.setOnClickListener {
            when(dbUtils.getTheProState(pro_id)){
                // no shared, go to share
                "0" -> {
                    httpClient.changeProtocolState(pro_id,object :ResultListener{
                        override fun postResullt(resultType: Int, msg: String) {
                            if(msg.equals("修改状态成功")) {
                                Toast.makeText(this@ProtocolDetial, "已发布到广场", Toast.LENGTH_SHORT).show()
                            }
                            if (resultType == 1){
                                this@ProtocolDetial.pro_detial_radio.setImageResource(R.drawable.ic_radio_black_24dp)
                            }
                        }
                    })
                }
                // shared, go to no share
                "1" -> {
                    httpClient.changeProtocolState(pro_id,object :ResultListener{
                        override fun postResullt(resultType: Int, msg: String) {
                            if(msg.equals("修改状态成功")) {
                                Toast.makeText(this@ProtocolDetial, "已取消发布到广场", Toast.LENGTH_SHORT).show()
                            }
                            if (resultType == 1){
                                this@ProtocolDetial.pro_detial_radio.setImageResource(R.drawable.ic_radio_deep_orange_600_24dp)

                            }
                        }
                    })
                }
            }
        }
        pro_detial_share = findViewById(R.id.pro_detial_share)
        pro_detial_share.setOnClickListener {
            Toast.makeText(this@ProtocolDetial,"share",Toast.LENGTH_SHORT).show()
        }
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
                    //Log.d("ProtocolDetialTag0",Thread.currentThread().name.toString())
                    pro_detial_type.setImageResource(R.drawable.protocol)
                    if (null != msg.obj){
                        protocolDetialBean = msg.obj as ProtocolDetailBean
                    }
                    text_owner.text = protocolDetialBean.owner
                    pro_detial_title.text = protocolDetialBean.title
                    text_content.text = protocolDetialBean.content
                    text_time.text = protocolDetialBean.created_at.split("T")[0].replace("-",".")

                    val had = dbUtils.getTheSignedNumber(protocolDetialBean._id).replace(" ","")
                    //pro_detial_signatory_had.text = dbUtils.getTheSignedNumber(protocolDetialBean._id)
                    pro_detial_signatory_had.text = had
                    val all = protocolDetialBean.signatoryNum.toString().replace(" ","")
                    //pro_detial_signatory_all.text = protocolDetialBean.signatoryNum.toString()
                    pro_detial_signatory_all.text = all
                    pro_detial_signatory_list.text = dbUtils.getTheSignedPeopleName(protocolDetialBean._id)
                    //Log.d("thesignedNumber" , "normal $pro_detial_signatory_had  $pro_detial_signatory_all")
                    if(all.contains(had)){
                        pro_detial_sign.visibility = View.GONE
                    }
                    //toast("finish")
                }
                1 -> {
                    //Log.d("ProtocolDetialTag1",Thread.currentThread().name.toString())
                    pro_detial_type.setImageResource(R.drawable.floater)
                    if( null != msg.obj){
                        protocolDetialBean = msg.obj as ProtocolDetailBean
                    }
                    pro_detial_title.text = protocolDetialBean.title
                    text_owner.text = protocolDetialBean.owner
                    text_content.text = protocolDetialBean.content
                    pro_detial_signatory_had.text = dbUtils.getTheSignedNumber(protocolDetialBean._id)
                    pro_detial_signatory_all.text = "2"
                    text_time.text = protocolDetialBean.created_at.split("T")[0].replace("-",".")
                    pro_detial_signatory_list.text = dbUtils.getTheSignedPeopleName(protocolDetialBean._id)
                    //Log.d("thesignedNumber" , "floater $pro_detial_signatory_had  $pro_detial_signatory_all")
                    if(pro_detial_signatory_all.text.contains(pro_detial_signatory_had.text)){
                        pro_detial_sign.visibility = View.GONE
                    }
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
        private lateinit var mmsg: Message
        private lateinit var bean: ProtocolDetailBean

        override fun run() {
            val type = dbUtils.getProType(pro_id)
            mmsg = Message()
            when (type) {
                "0" -> {
                    bean = dbUtils.getNormalProtocolDetail(pro_id)
                    mmsg.what = 0
                    // 每次点开一个协议，自动从后台查询一次协议详情
                    httpClient.updateFloaterInfo(0,bean._id,object :ResultListener{
                        override fun postResullt(resultType: Int, msg: String) {
                            if (resultType == 1){
                                bean.signatory = dbUtils.getSignatoryList(bean._id)
                                this@ProtocolDetial.myHandler.handleMessage(mmsg)

                            }
                        }
                    })
                    mmsg.obj = bean
                }
                "1" -> {
                    bean = dbUtils.getFloaterProtocolDetail(pro_id)
                    mmsg.what = 1
                    // 每次点开一个协议，自动从后台查询一次协议详情
                    httpClient.updateFloaterInfo(1,bean._id,object :ResultListener{
                        override fun postResullt(resultType: Int, msg: String) {
                            if (resultType == 1){
                                bean.signatory = dbUtils.getSignatoryList(bean._id)
                                this@ProtocolDetial.myHandler.handleMessage(mmsg)
                            }
                        }
                    })
                    mmsg.obj = bean
                }
                else -> {
                    mmsg.what = -1
                    this@ProtocolDetial.myHandler.handleMessage(mmsg)
                }
            }
            //this@ProtocolDetial.myHandler.handleMessage(mmsg)
        }

    }
}


