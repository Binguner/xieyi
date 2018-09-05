package com.binguner.xieyi.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import com.binguner.xieyi.R
import com.binguner.xieyi.RxUtils.HttpClient
import com.binguner.xieyi.databases.DBUtils
import com.binguner.xieyi.httpClient
import com.binguner.xieyi.listeners.ResultListener
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.custom.style
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx

lateinit var mhttpClient: HttpClient
class CreateProtocolFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        msharedPreferences = context!!.getSharedPreferences("UserData",Context.MODE_PRIVATE)
        mhttpClient = HttpClient(context!!)
        dbUtils = DBUtils(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val create_protocol_view = CreateProtocolFragmentUI().createView(AnkoContext.Companion.create(ctx,CreateProtocolFragment()))
        return create_protocol_view
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
        @JvmStatic
        fun newInstance() =
                CreateProtocolFragment().apply {
                }
    }
}
lateinit var msharedPreferences :SharedPreferences
lateinit var dbUtils: DBUtils

class CreateProtocolFragmentUI:AnkoComponent<CreateProtocolFragment>{

    val id_createPro_toolbar = View.generateViewId()
    val id_createPro_shadow = View.generateViewId()
    val id_createPro_toolbar_textview = View.generateViewId()
    val id_createPro_ed_title = View.generateViewId()
    val id_createPro_ed_content = View.generateViewId()
    val id_createPro_peo_num = View.generateViewId()
    val id_choosePeopleNum = View.generateViewId()
    val id_createPro_finish_btn = View.generateViewId()
    val items = arrayOf("  1  ", "  2  ", "  3  ", "  4  ", "  5  ")

    lateinit var title:String
    lateinit var mcontent:String
    lateinit var signatoryNum:String
    lateinit var username:String



    override fun createView(ui: AnkoContext<CreateProtocolFragment>) = with(ui) {
        constraintLayout(){

            backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)

            include<View>(R.layout.toolbar_layout){
                id = id_createPro_toolbar
            }.lparams(width = matchParent, height = wrapContent){
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            textView(){
                id = id_createPro_toolbar_textview
                text = "编写协议"
                textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
            }.lparams(width = wrapContent, height = wrapContent){
                topToTop = id_createPro_toolbar
                bottomToBottom = id_createPro_toolbar
                startToStart = id_createPro_toolbar
                endToEnd = id_createPro_toolbar
            }

            include<View>(R.layout.shadow_line){
                id = id_createPro_shadow
            }.lparams(width = matchParent, height = wrapContent){
                topToBottom = id_createPro_toolbar
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            val createPro_ed_title = editText(){
                id = id_createPro_ed_title
                maxLines = 1
                hint = "请输入协议标题："
                singleLine = true

                setBackgroundResource(R.drawable.create_pro_title_edit)
            }.lparams(width = matchParent){
                /*topToBottom = id_createPro_shadow
                startToStart = PARENT_ID
                endToEnd = PARENT_ID*/
            }

            val createPro_ed_content = editText(){
                id = id_createPro_ed_content
                gravity = Gravity.TOP
                hint = "请输入协议内容"
                setBackgroundResource(R.drawable.create_pro_content)
            }.lparams(width = matchParent, height = dip(0    ))

            val createPro_peo_num = textView(){
                id = id_createPro_peo_num
                text = "签署人数："
            }.lparams(){
                bottomToBottom = PARENT_ID
                bottomMargin = dip(16)
                startToStart = PARENT_ID
                leftMargin = dip(16)
            }

            val choosePeopleNum = spinner {
                id = id_choosePeopleNum
                adapter = ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item,items)
            }.lparams(){
                topToTop = id_createPro_peo_num
                startToEnd = id_createPro_peo_num
                leftMargin = dip(4)
                bottomToBottom = id_createPro_peo_num
            }

            val createPro_finish_btn = button("发布"){
                id = id_createPro_finish_btn
                textSize = 14f
                background = ContextCompat.getDrawable(ctx, R.drawable.protocol_publish_btn)

                onClick {
                    title = createPro_ed_title.text.toString()
                    mcontent = createPro_ed_content.text.toString()
                    signatoryNum = choosePeopleNum.selectedItem.toString()
                    username = msharedPreferences.getString("username","")
                    mhttpClient.doProtocol(title,mcontent,signatoryNum,username,object :ResultListener{
                        override fun postResullt(resultType: Int, msg: String) {
                            if(resultType == ResultListener.succeedType){
                                toast(msg)
                                createPro_ed_title.setText("")
                                createPro_ed_content.setText("")
                                choosePeopleNum.setSelection(0)
                            }else{
                                toast(msg)
                            }
                        }

                    })
                }
            }.lparams(height = dip(35)){
                topToTop = id_choosePeopleNum
                bottomToBottom = id_choosePeopleNum
                endToEnd = PARENT_ID
                rightMargin = dip(8)
            }

            applyConstraintSet {

                connect(
                        TOP of id_createPro_ed_title to BOTTOM of id_createPro_shadow margin dip(4),
                        START of id_createPro_ed_title to START of PARENT_ID margin dip(6),
                        END of id_createPro_ed_title to END of PARENT_ID margin dip(6),

                        TOP of id_createPro_ed_content to BOTTOM of id_createPro_ed_title margin dip(6),
                        START of id_createPro_ed_content to START of PARENT_ID margin dip(6),
                        END of id_createPro_ed_content to END of PARENT_ID margin dip(6),
                        BOTTOM of id_createPro_ed_content to TOP of id_createPro_peo_num margin dip(17)
                )
            }
        }
    }

}
