package com.binguner.xieyi

import android.Manifest
import android.app.Activity
import android.app.NativeActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.graphics.Color
import android.media.Image
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintSet.INVISIBLE
import android.support.v7.widget.Toolbar
import android.view.View
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getColor
import android.text.Layout
import android.util.Log
import android.view.ViewManager
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TableLayout
import com.binguner.xieyi.activities.type
import com.binguner.xieyi.fragments.*
import com.binguner.xieyi.utils.StatusBarUtil
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.design.tabItem
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity(),OnSelectToFinishCallback{



    override fun selected() {
        this.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        initId()
        var isLoging = sharedPreferences.getBoolean("isLoging",false)
        //Log.d("DSDSD","$isLoging")
        if(!isLoging){
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        MainActivityUI().setContentView(this)
        setTransparentStatusbar();
        initFragments()
        askPermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        personFragment?.onActivityResult(requestCode,resultCode,data)

    }

    private fun initId() {
        sharedPreferences = getSharedPreferences("UserData",Context.MODE_PRIVATE)
    }

    private fun askPermission() {
        var permissionid = -1
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            permissionid = 0
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionid = 1
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionid = 2
        }
        when(permissionid){
            0 -> ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET),1)
            1 -> ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
            2 -> ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }
    }

    private fun initFragments() {
        if (homeFragment == null){
            homeFragment = HomeFragment.newInstance()
        }
        if(createProtocolFragment == null){
            createProtocolFragment = CreateProtocolFragment.newInstance()
        }
        if(personFragment == null){
            personFragment = PersonFragment.newInstance()
            //Log.d("tetete","isAttaching")
            personFragment!!.attachAtyy(this!!)
        }
        mfragmentManager = supportFragmentManager
        mfragmentManager?.beginTransaction()?.add(id_fragmentscontainer, homeFragment)?.add(id_fragmentscontainer, createProtocolFragment)?.add(id_fragmentscontainer, personFragment)?.commit()
        mfragmentManager?.beginTransaction()?.hide(createProtocolFragment)?.hide(personFragment)?.commit()
    }

    private fun setTransparentStatusbar() {
        StatusBarUtil.setStatusBarColor(this, R.color.colorWhite)
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            StatusBarUtil.setStatusBarTextBalck(this)
        }
        //StatusBarUtil.transparentStatusBar(this)
    }

    fun finishAty(){
        /*Log.d("tetete","finishAty")
        mfragmentManager?.beginTransaction()?.detach(personFragment)?.commit()
        mfragmentManager?.beginTransaction()?.detach(homeFragment)?.commit()
        mfragmentManager?.beginTransaction()?.detach(createProtocolFragment)?.commit()
        personFragment!!.onDetach()
        homeFragment!!.onDetach()
        createProtocolFragment!!.onDetach()*/
        //this.onBackPressed()
        this.finish()
        //og.d("tetete","finished")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //Log.d("tetete","onBackPressed!")
    }

}

var homeFragment:HomeFragment ?= null
var createProtocolFragment:CreateProtocolFragment ?= null
var personFragment:PersonFragment ?= null
val id_fragmentscontainer = View.generateViewId()
var mfragmentManager:FragmentManager ?= null
lateinit var sharedPreferences: SharedPreferences

class MainActivityUI: AnkoComponent<MainActivity>{

    val id_toolbar = View.generateViewId()
    val id_shadow_line = View.generateViewId()
    val id_btn1 = View.generateViewId()
    val id_btn2 = View.generateViewId()
    val id_btn3 = View.generateViewId()
    val id_switch_tab = View.generateViewId()
    val id_switch_tab1 = View.generateViewId()
    val id_switch_tab2 = View.generateViewId()
    var mtablayout: View ?= null
    lateinit var imm : InputMethodManager

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        constraintLayout(){
            backgroundColor = getColor(ctx,R.color.colorNormalBack)

            var mBtnList = listOf<ImageView>()


            /*var mtoolbar = toolbar{
                id = id_toolbar
                backgroundColor = ContextCompat.getColor(ctx,R.color.colorWhite)
            }.lparams(width = matchParent){ fitsSystemWindows = true }*/

            /*var mtablayout = tabLayout() {
                id = id_switch_tab
                setSelectedTabIndicatorColor(Color.RED)
                setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorLightGrey))

                var tab_1 = tabItem(){
                    id = id_switch_tab1
                }

                var tab_2 = tabItem {
                    id = id_switch_tab2

                }

            }*/
            /*mtablayout = include<View>(R.layout.main_tab_layout){
                id = id_switch_tab
            }

            include<View>(R.layout.shadow_line){
                id = id_shadow_line
                fitsSystemWindows = true
            }.lparams(width = matchParent, height = wrapContent){ topToBottom = id_toolbar }
*/
            val btn1 = imageView(R.drawable.ic_home_outline_black_24dp_noneselected){
                id = id_btn1
                backgroundColor = ContextCompat.getColor(ctx,R.color.colorWhite)
                padding = dip(5)
                onClick {
                    imm.hideSoftInputFromWindow(this@imageView.windowToken,0)
                    selectButton(this@imageView,mBtnList)
                }
            }.lparams(width = dip(0), height = dip(40)){}
            val btn2 = imageView(R.drawable.ic_arrow_up_drop_circle_black_24dp_noneselected){
                id = id_btn2
                backgroundColor = ContextCompat.getColor(ctx,R.color.colorWhite)
                padding = dip(5)
                onClick {
                    imm.hideSoftInputFromWindow(this@imageView.windowToken,0)
                    selectButton(this@imageView,mBtnList)
                }
            }.lparams(width = dip(0), height = dip(40)){}
            val btn3 = imageView(R.drawable.ic_account_black_24dp_noneselected){
                id = id_btn3
                backgroundColor = ContextCompat.getColor(ctx,R.color.colorWhite)
                padding = dip(5)
                onClick {
                    imm.hideSoftInputFromWindow(this@imageView.windowToken,0)
                    selectButton(this@imageView,mBtnList)
                }
            }.lparams(width = dip(0), height = dip(40)){}

            val fragmentContainer = frameLayout {
                id = id_fragmentscontainer
                backgroundColor = Color.TRANSPARENT
                fitsSystemWindows = true

            }.lparams(width = matchParent, height = dip(0)){
                //topToBottom = id_shadow_line
                bottomToTop = id_btn3
                topToTop = PARENT_ID
            }


            applyConstraintSet {

                connect(
                        START of btn1 to START of PARENT_ID,
                        END of btn1 to START of btn2,
                        START of btn2 to END of btn1,
                        END of btn2 to START of btn3,
                        START of btn3 to END of btn2,
                        END of btn3 to END of PARENT_ID,
                        BOTTOM of btn1 to BOTTOM of PARENT_ID,
                        BOTTOM of btn2 to BOTTOM of PARENT_ID,
                        BOTTOM of btn3 to BOTTOM of PARENT_ID
                )
                /*mtoolbar{
                    connect(
                            TOP to TOP of PARENT_ID
                    )
                }*/

                /*mtablayout?.let {
                    it {
                        connect(
                                TOP to TOP of id_toolbar,
                                BOTTOM to BOTTOM of id_toolbar,
                                START to START of id_toolbar,
                                END to END of id_toolbar
                        )
                    }
                }*/

            }
            mBtnList = listOf(btn1, btn2, btn3)

        }
    }


    fun selectButton(selectedBtn:ImageView,/*vararg btnlist: ImageView*/btnList:List<ImageView>){
        btnList.get(0).setImageResource(R.drawable.ic_home_outline_black_24dp_noneselected)
        btnList.get(1).setImageResource(R.drawable.ic_arrow_up_drop_circle_black_24dp_noneselected)
        btnList.get(2).setImageResource(R.drawable.ic_account_black_24dp_noneselected)
        if (mfragmentManager != null) {
            //Log.d("MaintActivityTag","fm not null")
            val ft = mfragmentManager?.beginTransaction()
            ft?.hide(homeFragment)?.hide(createProtocolFragment)?.hide(personFragment)?.commit()
        }
        when(selectedBtn.id){
            id_btn1 -> {
                selectedBtn.setImageResource(R.drawable.ic_home_black_24dp_selected)
                mfragmentManager?.beginTransaction()?.show(homeFragment)?.commit()
                //mfragmentManager?.beginTransaction()?.hide(createProtocolFragment)?.hide(personFragment)?.commit()
                mtablayout?.visibility = View.VISIBLE
            }
            id_btn2 -> {
                selectedBtn.setImageResource(R.drawable.ic_arrow_up_drop_circle_black_24dp_selected)
                mfragmentManager?.beginTransaction()?.show(createProtocolFragment)?.commit()
                //mfragmentManager?.beginTransaction()?.hide(homeFragment)?.hide(personFragment)?.commit()
                mtablayout?.visibility = View.INVISIBLE
            }
            id_btn3 -> {
                selectedBtn.setImageResource(R.drawable.ic_account_black_24dp_selected)
                mfragmentManager?.beginTransaction()?.show(personFragment)?.commit()
                //mfragmentManager?.beginTransaction()?.hide(homeFragment)?.hide(createProtocolFragment)?.commit()
                mtablayout?.visibility = View.INVISIBLE

            }
        }
    }

}
