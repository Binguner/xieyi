package com.binguner.xieyi.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.binguner.xieyi.R
import com.binguner.xieyi.RxUtils.HttpClient
import com.binguner.xieyi.activities.ProtocolDetial
import com.binguner.xieyi.adapters.ShakeProtocolAdapter
import com.binguner.xieyi.beans.ProtocolListBean
import com.binguner.xieyi.beans.ProtocolListBeanData
import com.binguner.xieyi.beans.ShakeProtocoBean
import com.binguner.xieyi.databases.DBUtils
import com.binguner.xieyi.httpClient
import com.binguner.xieyi.listeners.ResultListener
import com.chad.library.adapter.base.BaseQuickAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class ChildShakeProtocolFragment : Fragment() {

    private lateinit var protocolListHandler: ProtocolListHandler
    private lateinit var childShakeProtocolFragmnentUI: ChildShakeProtocolFragmnentUI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prolisthttpClient = HttpClient(context!!)
        protocoListSP = context!!.getSharedPreferences("UserData",Context.MODE_PRIVATE)

        protocolListDbUtils = DBUtils(context!!)
        initDatas()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        protocolListHandler = ProtocolListHandler()
        childShakeProtocolFragmnentUI = ChildShakeProtocolFragmnentUI()
        return childShakeProtocolFragmnentUI.createView(AnkoContext.Companion.create(ctx, ChildShakeProtocolFragment()))
    }

    fun onButtonPressed(uri: Uri) {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance() =
                ChildShakeProtocolFragment().apply {}
    }

    fun initDatas(){
        prolisthttpClient.getProtocolList(10, pageNumber,object :ResultListener{
            override fun postResullt(resultType: Int, msg: String) {
                //toast(msg)
                if (resultType == 1){
                    val protocolListThread = ProtocolListThread()
                    Thread(protocolListThread).start()
                }else{
                    toast(msg)
                }
            }
        })
    }

    inner class ProtocolListHandler:Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what){    // init recyclerview at first time : 0
                                // refresh recyclerview : 1
                0 -> {
                    childShakeProtocolFragmnentUI.refreshRecyclerView(proList)
                }

                1 -> {

                }
            }
        }
    }

    inner class ProtocolListThread:Runnable{
        override fun run() {
            //Log.d("ChildShakeProtentTag","pageNUmber is ${pageNumber.toString()}")
            proList = dbUtils.getProtocolList(protocoListSP.getString("user_id","null"), pageNumber.toString()).toMutableList()
            val msg = Message()
            msg.what = 0
            if(null != proList && proList.size == 10 ){
                //pageNumber++
            }
            this@ChildShakeProtocolFragment.protocolListHandler.sendMessage(msg)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pageNumber = 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pageNumber = 1
    }
}

private lateinit var protocolListDbUtils : DBUtils
private lateinit var protocoListSP :SharedPreferences
private lateinit var proList :MutableList<ProtocolListBeanData>
private lateinit var prolisthttpClient: HttpClient
private var pageNumber = 1

class ChildShakeProtocolFragmnentUI:AnkoComponent<ChildShakeProtocolFragment>{

    var protocolDatas = mutableListOf<ProtocolListBeanData>()
    val id_recyclerview = View.generateViewId()
    private lateinit var home_recyclerview :RecyclerView

    lateinit var myAdapter:ShakeProtocolAdapter
    override fun createView(ui: AnkoContext<ChildShakeProtocolFragment>) = with(ui){

        constraintLayout(){

            backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)

             home_recyclerview = recyclerView(){

                /*for ( i in 1..10){
                    //val bean:ShakeProtocoBean = ShakeProtocoBean("https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg","Binguner","It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.","2018 年 8 月 19 日")
                    //protocolDatas.add(bean)
                }*/
                 //protocolDatas = dbUtils

                id = id_recyclerview
                backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)
                layoutManager = LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false)
                //if(null == myAdapter){
                 myAdapter = ShakeProtocolAdapter(R.layout.shake_protocol_item_layout,ctx,protocolDatas)

                 myAdapter.setOnItemClickListener {
                     adapter, view, position -> startActivity<ProtocolDetial>("pro_id" to proList.get(position)._id,"ProtocolList" to true)

                 }
                 myAdapter.setOnLoadMoreListener(object :BaseQuickAdapter.RequestLoadMoreListener{
                     override fun onLoadMoreRequested() {
                         pageNumber++
                         //Log.d("onbottome","onBottom")
                         prolisthttpClient.getProtocolList(10, pageNumber,object :ResultListener{
                             override fun postResullt(resultType: Int, msg: String) {
                                 if ( resultType == 1 ){
                                     Log.d("currentPageNumber", pageNumber.toString())
                                     val data = dbUtils.getProtocolList(protocoListSP.getString("user_id","null"), pageNumber.toString())
                                     if (data.size < 10){
                                         //pageNumber--
                                         toast("暂无更多数据")
                                     }
                                     if (data.size == 0){
                                         toast("暂无更多数据")
                                         myAdapter.loadMoreFail()
                                         return
                                     }
                                     data.forEach {
                                         proList.add(myAdapter.data.size,it)
                                         this@ChildShakeProtocolFragmnentUI.myAdapter.addData(myAdapter.data.size,it)
                                         myAdapter.loadMoreComplete()
                                         //this@recyclerView.scrollToPosition(0)
                                     }
                                     //pageNumber++
                                 }else{
                                     toast("暂无更多数据")
                                     myAdapter.loadMoreFail()
                                     pageNumber--
                                 }
                             }
                         })
                     }

                 })
                 //adapter = ShakeProtocolAdapter(R.layout.shake_protocol_item_layout,ctx,protocolDatas)
                    adapter = myAdapter
                //}

            }.lparams(width = matchParent, height = dip(0)){
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }


        }

    }

    fun refreshRecyclerView(list:MutableList<ProtocolListBeanData>){

        val count = home_recyclerview.adapter.itemCount
        //if(count == 0){
          //  for ()
        //}
        //Log.d("ChildShakeProtentTag","size is :${list.size.toString()}")
        list.forEach {
            //Log.d("ChildShakeProtentTag",it._id)
            //Log.d("ChildShakeProtentTag",it._id)
            myAdapter.addData(myAdapter.data.size,it)
        }
        home_recyclerview.scrollToPosition(0)
    }

}
