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
import android.widget.TextView
import com.binguner.xieyi.R
import com.binguner.xieyi.adapters.ShakeProtocolAdapter
import com.binguner.xieyi.beans.ShakeProtocoBean
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx

class ChildShakeProtocolFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val shake_protocol_containerview = ChildShakeProtocolFragmnentUI().createView(AnkoContext.Companion.create(ctx, ChildShakeProtocolFragment()))
        return shake_protocol_containerview
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

}

class ChildShakeProtocolFragmnentUI:AnkoComponent<ChildShakeProtocolFragment>{

    var protocolDatas = mutableListOf<ShakeProtocoBean>()
    val id_recyclerview = View.generateViewId()

    lateinit var myAdapter:ShakeProtocolAdapter
    override fun createView(ui: AnkoContext<ChildShakeProtocolFragment>) = with(ui){

        constraintLayout(){

            backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)

            val home_recyclerview = recyclerView(){

                for ( i in 1..10){
                    val bean:ShakeProtocoBean = ShakeProtocoBean("https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg","Binguner","It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.It's protocol content.","2018 年 8 月 19 日")
                    protocolDatas.add(bean)
                }
                id = id_recyclerview
                backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)
                layoutManager = LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false)
                //if(null == myAdapter){
                    adapter = ShakeProtocolAdapter(R.layout.shake_protocol_item_layout,ctx,protocolDatas)
                //}


            }.lparams(width = matchParent, height = dip(0)){
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }


        }

    }

}
