package com.binguner.xieyi.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.binguner.xieyi.R
import com.binguner.xieyi.adapters.FloaterAdapter
import com.binguner.xieyi.beans.Data6
import com.binguner.xieyi.beans.FloaterBean
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.shake_protocol_item_layout.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.design.themedFloatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class ChildFloaterFragment : Fragment() {

    private lateinit var childFloaterFragmentUI: ChildFloaterFragmentUI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        csharedPreferences = context!!.getSharedPreferences("UserData",Context.MODE_PRIVATE);

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        childFloaterFragmentUI = ChildFloaterFragmentUI()
        val mContainer = childFloaterFragmentUI.createView(AnkoContext.Companion.create(ctx,ChildFloaterFragment()))
        //mAdapter.setOnItemClickListener { adapter, view, position -> toast("You clicked $position") }
        return mContainer

    }

    fun onButtonPressed(uri: Uri) {
    }

    override fun onResume() {
        super.onResume()
        childFloaterFragmentUI.refreshList()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                ChildFloaterFragment().apply {
                }
    }
}
//lateinit var mAdapter :FloaterAdapter
private lateinit var csharedPreferences :SharedPreferences
class ChildFloaterFragmentUI: AnkoComponent<ChildFloaterFragment>{

    lateinit var floaterAdapter: FloaterAdapter
    val id_floater_constraintlayout = View.generateViewId()
    val id_floater_recyclerview = View.generateViewId()

    //var mDatas = mutableListOf<Data6>()
    var mDatas = dbUtils.getFloaterProtocolList(sharedPreferences.getString("user_id","null"))

    fun refreshList(){
        mDatas = dbUtils.getFloaterProtocolList(sharedPreferences.getString("user_id","null"))
        floaterAdapter.addData(0,dbUtils.getFloaterProtocolList(sharedPreferences.getString("user_id","null"))[0])
    }

    override fun createView(ui: AnkoContext<ChildFloaterFragment>) = with(ui){

        constraintLayout{
            id = id_floater_constraintlayout

            val floater_recyclerview = recyclerView {
                id = id_floater_recyclerview
                for(i in 1..10){
                    //var bean = Data6("I am a title","2013 3 3","Taiyuan",null,"","","","")
                    //mDatas.add(bean)
                }
                backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)
                layoutManager = LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false)
                floaterAdapter = FloaterAdapter(ctx, R.layout.floater_item_layout, mDatas)
                adapter = floaterAdapter


            }.lparams(width = matchParent, height = matchParent)
            //mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)

        }

    }

}