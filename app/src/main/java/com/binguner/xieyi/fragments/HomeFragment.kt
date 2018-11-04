package com.binguner.xieyi.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.inputmethodservice.InputMethodService
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.binguner.xieyi.R
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.binguner.xieyi.MainActivityUI
import com.binguner.xieyi.RxUtils.HttpClient
import com.binguner.xieyi.activities.CreateProtocolActivity
import com.binguner.xieyi.activities.ProtocolDetial
import com.binguner.xieyi.activities.SettingActivity
import com.binguner.xieyi.httpClient
import com.binguner.xieyi.listeners.ResultListener
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.alertDialogLayout
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.design.tabItem
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.textChangedListener
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.jetbrains.anko.support.v4.viewPager
import kotlin.system.measureNanoTime

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        current_page = 0
        initFragments();
    }

    private fun initFragments() {
        if(childShakeProtocolFragment == null){
            childShakeProtocolFragment = ChildShakeProtocolFragment.newInstance()
        }
        if (childFloaterFragment == null){
            childFloaterFragment = ChildFloaterFragment.newInstance()
        }
        mchildFragmentManager = childFragmentManager
        fragmentsList = listOf(childShakeProtocolFragment!!, childFloaterFragment!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = HomeFragmentUI().createView(AnkoContext.create(ctx,HomeFragment()))
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                HomeFragment().apply {
                }
    }
}

var childShakeProtocolFragment: ChildShakeProtocolFragment ?= null
var childFloaterFragment: ChildFloaterFragment ?= null
var mchildFragmentManager:FragmentManager ?= null
val id_viewPager = View.generateViewId()
var fragmentsList:List<Fragment> ?= null
var current_page = 0


class HomeFragmentUI:AnkoComponent<HomeFragment>{

    val id_home_toolbar = View.generateViewId()
    val id_home_shadow = View.generateViewId()
    val id_home_TabLayout = View.generateViewId()
    val id_home_alert_title = View.generateViewId()
    val id_home_alert_ed = View.generateViewId()
    val id_home_alert_search_btn = View.generateViewId()
    val id_home_alert_cancle_btn = View.generateViewId()
    val id_home_alert_parent = View.generateViewId()
    val id_home_alert = View.generateViewId()
    private lateinit var mAlert:DialogInterface
    val id_recyclerview = View.generateViewId()
    val id_add_new_shake_protocol = View.generateViewId()
    private lateinit var homeHttpClient:HttpClient

    private lateinit var pro_id : String
    lateinit var add_new_shake_protocol:ImageView


    override fun createView(ui: AnkoContext<HomeFragment>)= with(ui) {
        homeHttpClient = HttpClient(ctx)
        constraintLayout(){

            val home_toolbar = include<View>(R.layout.toolbar_layout) {
                id = id_home_toolbar
                //fitsSystemWindows = true
            }.lparams(width = matchParent, height = wrapContent){
                topToTop = PARENT_ID
                bottomToTop = id_home_shadow
            }

            imageView {
                imageResource = R.drawable.ic_search_black_24dp
                onClick {
                    //toast("Search")
                    mAlert = alert() {
                        id = id_home_alert

                        onCancelled {
                            val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromInputMethod(this@imageView.windowToken,0)
                        }




                        customView {
                            constraintLayout {
                                id = id_home_alert_parent
                                textView {
                                    id = id_home_alert_title
                                    text = "搜索协议: "
                                    textSize = 20f
                                    textColor = ContextCompat.getColor(ctx,R.color.colorTextGrayDeep)
                                }.lparams(){
                                    topToTop = id_home_alert_parent
                                    startToStart = id_home_alert_parent
                                    leftPadding = dip(20)
                                    topPadding = dip(10)
                                }

                                val myed = editText() {
                                    id = id_home_alert_ed
                                    setHint("协议 ID")
                                    hintTextColor = ContextCompat.getColor(ctx,R.color.colorLightGrey)
                                    textColor = ContextCompat.getColor(ctx,R.color.colorLightGrey)
                                    textChangedListener {
                                        afterTextChanged {
                                            pro_id = this@editText.text.toString()
                                        }
                                    }
                                }.lparams(width = matchParent){
                                    topToBottom = id_home_alert_title
                                    startToStart = id_home_alert_parent
                                    endToEnd = id_home_alert_parent
                                    topMargin = dip(10)
                                    leftPadding = dip(10)
                                    rightPadding = dip(10)
                                }

                                button("搜索"){
                                    id = id_home_alert_search_btn
                                    onClick {
                                        if (null != pro_id && !pro_id.equals("")){
                                            homeHttpClient.updateFloaterInfo(2,pro_id,object : ResultListener{
                                                override fun postResullt(resultType: Int, msg: String) {
                                                    if(resultType == 1){
                                                        startActivity<ProtocolDetial>("pro_id" to pro_id,"ProtocolList" to false)
                                                        myed.setText("")
                                                    }else{
                                                        toast("找不到该协议～")
                                                    }
                                                }
                                            })
                                        }else{
                                            toast("请输入协议ID")
                                        }
                                    }
                                }.lparams(){
                                    bottomToBottom = id_home_alert_parent
                                    endToEnd = id_home_alert_parent
                                    topToBottom = id_home_alert_ed
                                    bottomMargin = dip(10)
                                    rightMargin = dip(10)
                                }

                                button("取消") {
                                    id = id_home_alert_cancle_btn
                                    onClick {
                                        mAlert.dismiss()
                                        val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                        imm.hideSoftInputFromInputMethod(this@imageView.windowToken,0)
                                    }
                                }.lparams(){
                                    topToTop = id_home_alert_search_btn
                                    endToStart = id_home_alert_search_btn

                                }

                            }
                        }
                    }.show()
                }
                leftPadding = dip(10)
                rightPadding = dip(10)
            }.lparams(){
                topToTop = id_home_toolbar
                bottomToBottom = id_home_toolbar
                startToStart = id_home_toolbar

            }

            val home_shadow_line = include<View>(R.layout.shadow_line){
                id = id_home_shadow
            }.lparams(width = matchParent){
                topToBottom = id_home_toolbar
            }

            val mviewPager = viewPager(){
                id = id_viewPager
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorNormalBack)
                adapter = MyPageAdapter(mchildFragmentManager!!, fragmentsList!!)
                onPageChangeListener {
                    //toast("#postion")
                    onPageSelected {
                        when (it){
                            0 -> {
                                current_page = 0
                                add_new_shake_protocol.setImageResource(R.drawable.ic_assignment_black_24dp)
                            }
                            1 ->{
                                current_page = 1
                                add_new_shake_protocol.setImageResource(R.drawable.ic_add_black_24dp)
                            }
                        }

                    }

                }
            }.lparams(width = matchConstraint, height = dip(0)){
                topToBottom = id_home_shadow
                bottomToBottom = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            val home_TabLayout = include<TabLayout>(R.layout.main_tab_layout){
                id = id_home_TabLayout
                setupWithViewPager(mviewPager)
                addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        //toast("current_page is $current_page")
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabSelected(tab: TabLayout.Tab?) {
                    }
                })
            }.lparams(){
                topToTop = id_home_toolbar
                bottomToBottom = id_home_toolbar
                startToStart = id_home_toolbar
                endToEnd = id_home_toolbar
            }

            add_new_shake_protocol = imageView(){
                imageResource = R.drawable.ic_assignment_black_24dp

                onClick {
                    when(current_page){
                        0 -> {
                            //toast("Create Shake Protocol")
                            //startActivity<CreateProtocolActivity>("Type" to 0)
                            startActivity<SettingActivity>("flag" to 1,"justNormalProtocol" to true)
                        }
                        1 -> {
                            //toast("Create Floater Protocol")
                            startActivity<CreateProtocolActivity>("Type" to 1)
                        }
                    }
                }
            }.lparams(height = dip(45) ){
                topToTop = id_home_toolbar
                bottomToBottom = id_home_toolbar
                endToEnd = id_home_toolbar
                rightMargin = dip(10)
            }


        }

    }
}

class MyPageAdapter(fm: FragmentManager, var mlist:List<Fragment>) : FragmentPagerAdapter(fm) {

    var names = listOf<String>("抖协议","漂流瓶")
    override fun getItem(position: Int): Fragment {
        return mlist.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return names.get(position)
    }

    override fun getCount(): Int {
        return mlist.size
    }

}