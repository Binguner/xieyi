package com.binguner.xieyi.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.binguner.xieyi.R
import com.binguner.xieyi.RxUtils.HttpClient
import com.binguner.xieyi.activities.ProtocolDetial
import com.binguner.xieyi.adapters.FloaterAdapter
import com.binguner.xieyi.beans.Data6
import com.binguner.xieyi.beans.FloaterBean
import com.binguner.xieyi.httpClient
import com.binguner.xieyi.listeners.ResultListener
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.floater_item_layout.view.*
import kotlinx.android.synthetic.main.shake_protocol_item_layout.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.design.themedFloatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast

class ChildFloaterFragment : Fragment() {

    private var flag = false
    private lateinit var childFloaterFragmentUI: ChildFloaterFragmentUI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        csharedPreferences = context!!.getSharedPreferences("UserData",Context.MODE_PRIVATE)
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
        //Log.d("dfrv",flag.toString())
        if (flag){
            childFloaterFragmentUI.refreshList()
        }
        flag = true
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
    lateinit var floater_recyclerview :RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    //var mDatas = mutableListOf<Data6>()
    var mDatas = dbUtils.getFloaterProtocolList(sharedPreferences.getString("user_id","null"))
    var oldSize = mDatas.size
    private lateinit var httpClient:HttpClient



    fun refreshList(){
        if(dbUtils.getFloaterProtocolList(sharedPreferences.getString("user_id","null")).size > oldSize){
            floaterAdapter.addData(0, dbUtils.getFloaterProtocolList(sharedPreferences.getString("user_id", "null"))[0])
            floater_recyclerview.scrollToPosition(0)
            oldSize++
        }
        //mDatas = dbUtils.getFloaterProtocolList(sharedPreferences.getString("user_id","null"))
    }



    override fun createView(ui: AnkoContext<ChildFloaterFragment>) = with(ui){
        httpClient = HttpClient(ctx)
        constraintLayout{
            id = id_floater_constraintlayout

            swipeRefreshLayout = swipeRefreshLayout{

                floater_recyclerview = recyclerView {
                    id = id_floater_recyclerview

                    backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)
                    layoutManager = LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false)
                    floaterAdapter = FloaterAdapter(ctx, R.layout.all_prtocol_layout, mDatas)
                    //floaterAdapter.setEnableLoadMore(false)
                    floaterAdapter.setUpFetchEnable(true)
                    /*floaterAdapter.setUpFetchListener(object :BaseQuickAdapter.UpFetchListener{
                        override fun onUpFetch() {
                            floaterAdapter.isUpFetching = true
                            httpClient.getRandomFloater(object :ResultListener{
                                override fun postResullt(resultType: Int, msg: String) {
                                    toast(msg)
                                }
                            })
                        }
                    })*/
                    //floaterAdapter.setStartUpFetchPosition(0)
                    /* floaterAdapter.setOnLoadMoreListener(object :BaseQuickAdapter.RequestLoadMoreListener{
                         override fun onLoadMoreRequested() {

                         }
                     })*/
                    adapter = floaterAdapter
                    floaterAdapter.setOnItemClickListener {
                        adapter, view, position ->
                        //toast("Youckcicked $position")
                        startActivity<ProtocolDetial>("pro_id" to mDatas[position]._id)
                    }

                }

                setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
                    override fun onRefresh() {
                        //Log.d("rweee","fas")
                        /*httpClient.getRandomFloater(object :ResultListener{
                            override fun postResullt(resultType: Int, msg: String) {
                                toast(msg)
                                if(resultType == 11){

                                }else {
                                    this@ChildFloaterFragmentUI.floaterAdapter.addData(0, dbUtils.getFloaterProtocolList(sharedPreferences.getString("user_id", "null"))[0])
                                    this@ChildFloaterFragmentUI.floater_recyclerview.scrollToPosition(0)
                                }
                            }
                        })
                        this@swipeRefreshLayout.isRefreshing = false*/
                        getAFloater(ctx)
                    }
                })

            }.lparams(width = matchParent,height = matchParent){
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            //mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)

        }


    }

    fun getAFloater(context: Context){
        httpClient.getRandomFloater(object :ResultListener{
            override fun postResullt(resultType: Int, msg: String) {
                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
                if(resultType == 11){
                    getAFloater(context)
                }else {
                    this@ChildFloaterFragmentUI.floaterAdapter.addData(0, dbUtils.getFloaterProtocolList(sharedPreferences.getString("user_id", "null"))[0])
                    this@ChildFloaterFragmentUI.floater_recyclerview.scrollToPosition(0)
                }
            }
        })
        this@ChildFloaterFragmentUI.swipeRefreshLayout.isRefreshing = false
    }

}