package com.binguner.xieyi.fragments

import android.content.Context
import android.graphics.Color
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
import com.binguner.xieyi.MainActivityUI
import com.binguner.xieyi.activities.CreateProtocolActivity
import com.binguner.xieyi.activities.SettingActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabItem
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
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
    val id_recyclerview = View.generateViewId()
    val id_add_new_shake_protocol = View.generateViewId()


    override fun createView(ui: AnkoContext<HomeFragment>)= with(ui) {

        constraintLayout(){

            val home_toolbar = include<View>(R.layout.toolbar_layout) {
                id = id_home_toolbar
            }.lparams(width = matchParent, height = wrapContent){
                topToTop = PARENT_ID
                bottomToTop = id_home_shadow
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
                            0 -> current_page = 0
                            1 -> current_page = 1
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

            val add_new_shake_protocol = imageView(){
                imageResource = R.drawable.ic_add_black_24dp

                onClick {
                    when(current_page){
                        0 -> {
                            //toast("Create Shake Protocol")
                            //startActivity<CreateProtocolActivity>("Type" to 0)
                            startActivity<SettingActivity>("flag" to 1)
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