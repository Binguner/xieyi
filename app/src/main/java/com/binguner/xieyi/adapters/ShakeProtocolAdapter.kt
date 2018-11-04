package com.binguner.xieyi.adapters

import android.content.Context
import android.util.Log
import com.binguner.xieyi.R
import com.binguner.xieyi.beans.ProtocolListBean
import com.binguner.xieyi.beans.ProtocolListBeanData
import com.binguner.xieyi.beans.ShakeProtocoBean
import com.binguner.xieyi.utils.MyImageLoader
import com.binguner.xieyi.viewholders.Shake_protocpl_viewholder
import com.chad.library.adapter.base.BaseQuickAdapter
import java.util.*

class ShakeProtocolAdapter(val layoutid:Int,private val ctx:Context, data: MutableList<ProtocolListBeanData>?) : BaseQuickAdapter<ProtocolListBeanData, Shake_protocpl_viewholder>(layoutid,data) {

    private val colorList = listOf(
            R.color.colorAccent,
            R.color.colorCardBlue2,
            R.color.colorGrey,
            R.color.colorAlertDialogToolbar,
            R.color.colorRed,
            R.color.colorYello,
            R.color.colorOrange,
            R.color.colorPink,
            R.color.colorPurple)

    private fun getRandomColor():Int{
        val r = Random()
        //Log.d("sdfasd",r.nextInt(colorList.size).toString())
        //return r.nextInt(colorList.size)
        return colorList[r.nextInt(colorList.size)]
    }
    override fun convert(helper: Shake_protocpl_viewholder?, item: ProtocolListBeanData?) {
        try {
            //MyImageLoader.displayImage(ctx,item!!.path,helper!!.shake_avator)
        }catch (e:Exception){ }
        getRandomColor()
        helper?.shake_username?.setText(item!!.signatory[0])
        helper?.shake_date?.setText(item!!.created_at.split("T")[0].replace("-","."))
        helper?.shake_content?.setText(item!!.content)
        helper?.shake_avator_name?.setText(item!!.signatory[0].substring(0,1).toString())


        //helper?.shake_avator?.setImageResource(R.color.colorLightGrey)
        helper?.shake_avator?.setImageResource(getRandomColor())
    }

}