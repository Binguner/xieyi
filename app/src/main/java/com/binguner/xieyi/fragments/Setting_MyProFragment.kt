package com.binguner.xieyi.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.binguner.xieyi.R
import com.binguner.xieyi.activities.ProrocolDetial
import com.binguner.xieyi.adapters.AllProtocolAdapter
import com.binguner.xieyi.adapters.FloaterAdapter
import com.binguner.xieyi.beans.FloaterBean
import com.binguner.xieyi.beans.ProtocolDetailBean
import com.binguner.xieyi.databases.DBUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx

lateinit var mactivity: Activity
lateinit var mpcontext: Context

class Setting_MyProFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val container = SettingMyProFragmentUI().createView(AnkoContext.Companion.create(ctx, Setting_MyProFragment()))
        return container
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mpcontext = context
    }

    override fun onDetach() {
        super.onDetach()
    }

    fun attachActivity(activity: Activity){
        mactivity = activity
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                Setting_MyProFragment().apply {
                }
    }
}

var items = mutableListOf<FloaterBean>()

class SettingMyProFragmentUI:AnkoComponent<Setting_MyProFragment>{

    val id_myPro_toolbar = View.generateViewId()

    val id_myPro_shadow = View.generateViewId()
    val id_myPro_back = View.generateViewId()
    val id_myPro_recycler_view = View.generateViewId()
    private val dbUtils = DBUtils(mpcontext)
    private val sharedPreferences = mpcontext.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    lateinit var set_recycler_view :RecyclerView

    override fun createView(ui: AnkoContext<Setting_MyProFragment>)= with(ui) {

        constraintLayout {
            fitsSystemWindows = true
            val myPro_toolbar = include<View>(R.layout.toolbar_layout){
                id = id_myPro_toolbar
                //fitsSystemWindows = true

            }.lparams(width = matchParent){
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            val myPro_shadow = include<View>(R.layout.shadow_line){
                id = id_myPro_shadow
            }.lparams(width = matchParent){
                topToBottom = id_myPro_toolbar
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            textView(){
                text = "我的协议"
                onClick {
                    set_recycler_view.scrollToPosition(0)
                }
            }.lparams(){
                topToTop = id_myPro_toolbar
                bottomToBottom = id_myPro_toolbar
                startToStart = id_myPro_toolbar
                endToEnd = id_myPro_toolbar
            }

            val myPro_back = imageView(){
                id = id_myPro_back
                setImageResource(R.drawable.ic_arrow_back_black_24dp)
                onClick {
                    mactivity.finish()
                }
            }.lparams(){
                topToTop = id_myPro_toolbar
                bottomToBottom = id_myPro_toolbar
                startToStart = id_myPro_toolbar
                leftMargin = dip(10)
            }

            set_recycler_view = recyclerView {
                id = id_myPro_recycler_view
                //val list = dbUtils.getFloaterProtocolList(sharedPreferences.getString("user_id", "null"))
                val list = mutableListOf<ProtocolDetailBean>()
                val protocolMap = dbUtils.getAllProtocol_id_List(sharedPreferences.getString("user_id","null")).toSortedMap(compareBy {
                    it
                })
                for((key,value) in protocolMap){
                    when(value){
                        "0" -> {
                            // normal
                            list.add(dbUtils.getNormalProtocolDetail(key))
                        }
                        "1" ->{
                            // floater
                            list.add(dbUtils.getFloaterProtocolDetail(key))
                        }
                    }
                }
                /*for(values in protocolMap.values){
                    Log.d("sdnfasidnf",values)
                }
                for (key in protocolMap.keys){
                    Log.d("sdnfasidnf",key + " !")
                }*/
                if (null != list) {

                }
                for(i in 1..10){
                    var data = FloaterBean("标题","数据","Home")
                    items.add(data)
                }
                layoutManager = LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false)
                //val myAdapter = FloaterAdapter(ctx, R.layout.floater_item_layout, list)
                val reversedList = list.reversed().toMutableList()
                val myAdapter = AllProtocolAdapter(ctx, R.layout.all_prtocol_layout, reversedList)
                myAdapter.setOnItemClickListener {
                    adapter, view, position ->  toast("You clicked $position")
                    startActivity<ProrocolDetial>()
                }
                adapter = myAdapter
                /*dapter.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener {
                    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                        Log.d("tetete","here@")
                        toast("You clicked $position")
                    }
                })*/
            }.lparams(width = matchParent, height = dip(0)){
                topToBottom = id_myPro_shadow
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                bottomToBottom = PARENT_ID
            }

        }
    }

}