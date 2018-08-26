package com.binguner.xieyi.fragments

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.binguner.xieyi.R
import com.binguner.xieyi.adapters.FloaterAdapter
import com.binguner.xieyi.beans.FloaterBean
import kotlinx.android.synthetic.main.shake_protocol_item_layout.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.design.themedFloatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx

class ChildFloaterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mContainer = ChildFloaterFragmentUI().createView(AnkoContext.Companion.create(ctx,ChildFloaterFragment()))
        return mContainer
    }

    fun onButtonPressed(uri: Uri) {
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

class ChildFloaterFragmentUI: AnkoComponent<ChildFloaterFragment>{

    val id_floater_constraintlayout = View.generateViewId()
    val id_floater_recyclerview = View.generateViewId()

    lateinit var mAdapter :FloaterAdapter
    var mDatas = mutableListOf<FloaterBean>()

    override fun createView(ui: AnkoContext<ChildFloaterFragment>) = with(ui){

        constraintLayout{
            id = id_floater_constraintlayout

            val floater_recyclerview = recyclerView {
                id = id_floater_recyclerview
                for(i in 1..10){
                    var bean = FloaterBean("I am a title","2013 3 3","Taiyuan")
                    mDatas.add(bean)
                }
                backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)
                layoutManager = LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false)
                adapter = FloaterAdapter(ctx,R.layout.floater_item_layout,mDatas)
            }.lparams(width = matchParent, height = matchParent)




        }

    }

}