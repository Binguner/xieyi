package com.binguner.xieyi.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.binguner.xieyi.beans.*
import com.binguner.xieyi.fragments.dbUtils
import com.binguner.xieyi.sharedPreferences
import com.binguner.xieyi.username

class DBUtils(val context: Context){

    companion object {
        val TypeNickname = 0
        val TypePassword = 1
        val TypeEmail = 2
        val TypePhoneNumber = 3
        val TypeAvatar = 4
    }

    var db:SQLiteDatabase
    val contentValues = ContentValues()
    init {
        db = context.database.writableDatabase
    }

    // register
    fun insertNewUserInfo(phonenumber: String, username: String, password: String, user_id: String?, user_avator_url:String?, nickname:String) {
        contentValues.put("user_id",user_id)
        contentValues.put("user_name",username)
        contentValues.put("user_phonenumber",phonenumber)
        contentValues.put("user_password",password)
        contentValues.put("user_avatar",user_avator_url)
        contentValues.put("nickname",nickname)
        try {
            db.insert("User_info",null,contentValues)
        }catch (e: Exception){ }
        contentValues.clear()
    }

    // login
    fun insertOldUserInfo(doLoginBean: DoLoginBean) {
        contentValues.put("user_id",doLoginBean.data._id)
        contentValues.put("user_name",doLoginBean.data.username)
        contentValues.put("user_phonenumber",doLoginBean.data.phone)
        contentValues.put("user_password",doLoginBean.data.password)
        if(null != doLoginBean.data.email && !doLoginBean.data.email.equals("")){
            contentValues.put("email",doLoginBean.data.email)
        }else{
            contentValues.put("email","")
        }
        contentValues.put("user_avatar","")
        try {
            db.insert("User_info",null,contentValues)
        }catch (e:Exception){}
        contentValues.clear()
    }

    // get user name and phonenumber
    fun selectUserInfo(user_id:String):List<String>{
        val cursor: Cursor = db.query("User_info", null, "user_id like ?", arrayOf(user_id), null, null, null)
        lateinit var user_name:String
        lateinit var user_phonenumber : String

        if(cursor.moveToFirst()){
            do {
                user_name = cursor.getString(cursor.getColumnIndex("user_name"))
                user_phonenumber = cursor.getString(cursor.getColumnIndex("user_phonenumber"))
            }while (cursor.moveToNext())
        }
        var list:List<String>
        if (null != user_name && null != user_phonenumber){
            var list = listOf<String>(user_name, user_phonenumber)
            return list
        }else{
            return null!!
        }
    }

    // delete this accoutn/
    fun deleteAccout(user_name:String){
        val isDelete = db.delete("User_info","user_name like ?", arrayOf(user_name))
        db.delete("all_protocol","username like ?", arrayOf(user_name))
        db.delete("normal_protocol","username like ?", arrayOf(user_name))
        db.delete("floater_protocol","username like ?", arrayOf(user_name))
        db.delete("signatory_list",null,null)
    }

    // insert all protocols
    fun insertAllProtocol(protocol_id:String, username: String, user_id: String, createPro_ed_title: String,type :String ){
        contentValues.put("protocol_id",protocol_id)
        contentValues.put("username",username)
        contentValues.put("user_id",user_id)
        contentValues.put("createPro_ed_title", createPro_ed_title)
        contentValues.put("type", type)
        try {
            db.insert("all_protocol",null,contentValues)
            contentValues.clear()
        }catch (e:Exception){}
    }

    /**
     * 判断 漂流瓶列表中是否存在这个协议
     * 存在 ： true
     * 不存在 ： false
     */
    fun isExistThisFloaterInDB(protocol_id: String):Boolean{
        val cursor = db.query("all_protocol",null,"protocol_id like ?", arrayOf(protocol_id),null,null,null)
        if (cursor.moveToFirst()){
            do {
                if(cursor.getString(cursor.getColumnIndex("protocol_id")).equals(protocol_id)){
                    return true
                }
            }while (cursor.moveToNext())
        }
        return false
    }

    // inert normal protocol
    fun insertNormalProtocol(protocol_id:String, username:String, user_id: String, title:String, peopleNum:String, isShared:String, pro_content:String, create_at:String){
        contentValues.put("protocol_id",protocol_id)
        contentValues.put("username",username)
        contentValues.put("user_id",user_id)
        contentValues.put("createPro_title",title)
        contentValues.put("choosePeopleNum",peopleNum)
        contentValues.put("isShared",isShared)
        contentValues.put("pro_content",pro_content)
        contentValues.put("create_at",create_at)
        try {
            db.insert("normal_protocol",null,contentValues)
            contentValues.clear()
        }catch (e:Exception){}
    }

    // insert floater protocol
    fun insertFloaterProtocol(protocol_id:String, username:String, user_id:String, createPro_ed_title:String, pro_content:String, created_at:String,obtain_at:String, region:String, state:String){
        contentValues.put("protocol_id",protocol_id)
        contentValues.put("username",username)
        contentValues.put("user_id",user_id)
        contentValues.put("createPro_ed_title",createPro_ed_title)
        contentValues.put("pro_content",pro_content)
        contentValues.put("created_at",created_at)
        contentValues.put("obtain_at",obtain_at)
        contentValues.put("region",region)
        contentValues.put("state",state)
        try {
            db.insert("floater_protocol",null,contentValues)
        }catch (e:Exception){ }
        contentValues.clear()
    }

    // update the user information
    fun updateUserInfo(user_id: String, changeType:Int,any: Any){
        when (changeType){
            DBUtils.TypeNickname -> {
                contentValues.put("nickname",any as String)
                db.update("User_info",contentValues,"user_id like ?", arrayOf(user_id))
                //Log.d("tetete","inserted")
                contentValues.clear()
            }
            DBUtils.TypePhoneNumber -> {
                contentValues.put("user_phonenumber",any as String)
                db.update("User_info", contentValues, "user_id like ?", arrayOf(user_id))
                contentValues.clear()
            }
            DBUtils.TypeEmail -> {
                contentValues.put("email", any as String)
                db.update("User_info", contentValues,"user_id like?", arrayOf(user_id))
                contentValues.clear()
            }
            DBUtils.TypePassword -> {
                contentValues.put("user_password",any as String)
                db.update("User_info",contentValues,"user_id like?", arrayOf(user_id))
                contentValues.clear()
            }
        }
    }

    // get floater protocol list
    lateinit var bean : Data6
    fun getFloaterProtocolList(user_id: String): MutableList<Data6> {
        val list = mutableListOf<Data6>()
        val cursor = db.query("floater_protocol",null,"user_id like ?", arrayOf(user_id),null,null, null)
        if(cursor.moveToLast()){
            do{
                bean = Data6(
                        cursor.getString(cursor.getColumnIndex("protocol_id")),
                        cursor.getString(cursor.getColumnIndex("createPro_ed_title")),
                        cursor.getString(cursor.getColumnIndex("pro_content")),
                        null,
                        cursor.getString(cursor.getColumnIndex("created_at")),
                        cursor.getString(cursor.getColumnIndex("obtain_at")),
                        cursor.getString(cursor.getColumnIndex("region")),
                        cursor.getString(cursor.getColumnIndex("state"))
                )
                list.add(bean)
            }while (cursor.moveToPrevious())
        }
        cursor.close()
        return list
    }

    fun insertSignatoryList(protocol_id: String,nameList: List<String>){
        if ( null != nameList ){
            val sp = context.getSharedPreferences("UserData",Context.MODE_PRIVATE)
            val contentValues = ContentValues()
            for (name in nameList){
                contentValues.put("protocol_id",protocol_id)
                contentValues.put("user_id",sp.getString("user_id",""))
                contentValues.put("signatory_name",name)
                db.insert("signatory_list",null,contentValues)
                contentValues.clear()
            }
        }
    }

    /**
     * 获取 protocol_id 的签名个数
     */
    fun getTheSignedNumber(protocol_id:String):String{
        var num = 0;
        val cursor = db.query("signatory_list",null,"protocol_id like ?", arrayOf(protocol_id),null,null,null)
        if (cursor.moveToFirst()){
            do {
                if ( protocol_id == cursor.getString(cursor.getColumnIndex("protocol_id"))){
                    num++
                }
            }while (cursor.moveToNext())
        }
        return num.toString()
    }

    fun getTheSignedPeopleName(protocol_id:String):String{
        var name:String = ""
        val cursor = db.query("signatory_list",null,"protocol_id like ?", arrayOf(protocol_id),null,null,null)
        if (cursor.moveToFirst()){
            do {
                val newName = cursor.getString(cursor.getColumnIndex("signatory_name"))
                if(name != ""){
                    name = "$name、$newName"
                }else{
                    name = newName
                }
            }while (cursor.moveToNext())
        }
        return name
    }

    /**
     *  get all protocols id list
     *  key is id
     *  value is type
     *  0 normal
     *  1 floater
     */
    fun getAllProtocol_id_List(user_id: String):MutableMap<String,String>{
        val maplist = linkedMapOf<String,String>()
        val cursor = db.query("all_protocol",null,"user_id like ?", arrayOf(user_id),null,null,null)
        if(null != cursor) {
            if (cursor.moveToLast()) {
                do {
                    maplist.put(
                            cursor.getString(cursor.getColumnIndex("protocol_id")),
                            cursor.getString(cursor.getColumnIndex("type"))
                    )
                } while (cursor.moveToPrevious())
            }
            cursor.close()
        }
        return maplist
    }

    /**
     * Get the Protocol isShared pro
     * 1 shared
     * 0 no shared
     */
    fun getTheProState(protocol_id: String):String{
        var isShared:String = "0"
        val cursor = db.query("normal_protocol",null,"protocol_id like ?", arrayOf(protocol_id),null,null,null)
        if (cursor.moveToFirst()){
            do {
                isShared = cursor.getString(cursor.getColumnIndex("isShared"))
            }while (cursor.moveToNext())
        }
        return isShared
    }

    /**
     * Get The protocol Type
     */
    fun  getProType(pro_id:String):String{
        var type :String = "-1"
        val cursor = db.query("all_protocol",null,"protocol_id like ?", arrayOf(pro_id),null,null,null)
        if (null != cursor){
            if(cursor.moveToFirst()){
                do {
                    type = cursor.getString(cursor.getColumnIndex("type"))
                }while (cursor.moveToNext())
            }
        }
        return type
    }

    /**
     * get the normal protocol detail
     */
    lateinit var protocol:ProtocolDetailBean
    fun getNormalProtocolDetail(protocol_id: String):ProtocolDetailBean{
        val cursor = db.query("normal_protocol", null, "protocol_id like ?", arrayOf(protocol_id), null, null, null)
        if(null != cursor){
            if(cursor.moveToFirst()){
                do{
                    protocol = ProtocolDetailBean(
                            cursor.getString(cursor.getColumnIndex("protocol_id")),
                            cursor.getString(cursor.getColumnIndex("username")),
                            cursor.getString(cursor.getColumnIndex("createPro_title")),
                            cursor.getString(cursor.getColumnIndex("pro_content")),
                            cursor.getString(cursor.getColumnIndex("choosePeopleNum")).toString(),
                            null,
                            cursor.getString(cursor.getColumnIndex("create_at")),
                            null,
                            "0",
                            null,
                            cursor.getString(cursor.getColumnIndex("isShared")),
                            null,
                            null,
                            0
                            )
                }while (cursor.moveToNext())
            }
        }
        return protocol
    }

    // get the floater protocol detail
    fun getFloaterProtocolDetail(protocol_id: String):ProtocolDetailBean{
        val cursor = db.query("floater_protocol",null,"protocol_id like ?", arrayOf(protocol_id),null,null,null)
        if(null != cursor){
            if(cursor.moveToFirst()){
                do {
                    protocol = ProtocolDetailBean(
                            cursor.getString(cursor.getColumnIndex("protocol_id")),
                            cursor.getString(cursor.getColumnIndex("username")),
                            cursor.getString(cursor.getColumnIndex("createPro_ed_title")),
                            cursor.getString(cursor.getColumnIndex("pro_content")),
                            null,
                            null,
                            cursor.getString(cursor.getColumnIndex("created_at")),
                            cursor.getString(cursor.getColumnIndex("obtain_at")),
                            cursor.getString(cursor.getColumnIndex("state")),
                            cursor.getString(cursor.getColumnIndex("region")),
                            null,
                            null,
                            null,
                            1
                    )
                }while (cursor.moveToNext())
            }
        }

        return protocol
    }

    // insert New Signer
    fun insertNewSigner(protocol_id: String,list: List<String>):Boolean{
        db.delete("signatory_list","protocol_id like ?", arrayOf(protocol_id))
        val sp = context.getSharedPreferences("UserData",Context.MODE_PRIVATE)
        var contentValues = ContentValues()
        try {
            /*list.forEach {
                contentValues.put("protocol_id",protocol_id)
                contentValues.put("user_id", sp.getString("user_id","null"))
                contentValues.put("signatory_name", it)
                db.insert("signatory_list",null,contentValues)
                contentValues.clear()
            }*/
            for (s in list) {
                contentValues.put("protocol_id",protocol_id)
                contentValues.put("user_id", sp.getString("user_id","null"))
                contentValues.put("signatory_name", s)
                db.insert("signatory_list",null,contentValues)
                contentValues.clear()
            }
        }catch (e:java.lang.Exception){
            return false
        }
        return true
    }

    // get the signatory list
    fun getSignatoryList(protocol_id: String):List<String>{
        val nameList = mutableListOf<String>()
        val cursor = db.query("signatory_list",null,"protocol_id like ?", arrayOf(protocol_id),null,null,null)
        if (cursor.moveToFirst()){
            do {
                nameList.add(cursor.getString(cursor.getColumnIndex("protocol_id")))
            }while (cursor.moveToNext())
        }
        return nameList
    }

    fun getProtocolState(protocol_id: String):String{
        var state = "0"
        val cursor = db.query("normal_protocol",null,"protocol_id like ?", arrayOf(protocol_id),null,null,null)
        if(cursor.moveToFirst()){
            do {
                state = cursor.getString(cursor.getColumnIndex("isShared"))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return state
    }

    fun changeProtocolState(protocol_id: String) {
        var state = "0"
        var contentValues = ContentValues()
        val oldState = dbUtils.getProtocolState(protocol_id)
        when(oldState){
            "0" -> state = "1"
            "1" -> state = "0"
        }
        contentValues.put("isShared",state)
        db.update("normal_protocol",contentValues,"protocol_id like ?", arrayOf(protocol_id))
        contentValues.clear()
    }

    fun insertProtocolList(pageNumber:String,list:List<ProtocolListBeanData>):Boolean{
        val sp = context.getSharedPreferences("UserData",Context.MODE_PRIVATE)
        var contentValues = ContentValues()
        //Log.d("ChildShakeProtentTag","list's size is ${list.size}")
        try {
            list.forEach {
                if(null != it){
                    insertProtocolListSign(it._id,it.signatory)
                    contentValues.put("protocol_id",it._id)
                    contentValues.put("user_id",sp.getString("user_id","null"))
                    if (it.signatory != null && !it.signatory.isEmpty()){
                        contentValues.put("owner",it.signatory[0])
                    }else{
                        contentValues.put("owner","无名氏")
                    }
                    contentValues.put("signatoryNum",it.signatoryNum)
                    contentValues.put("title",it.title)
                    contentValues.put("content",it.content)
                    contentValues.put("createAt",it.created_at)
                    contentValues.put("pageNumber",pageNumber)
                    val flag = db.insert("protocolList",null,contentValues)
                    contentValues.clear()
                }
            }
        }catch (e:java.lang.Exception){
            return false
        }
        return true
    }

    fun insertProtocolListSign(protocol_id: String,list: List<String>){
        var ContentValues = ContentValues()
        val sp = context.getSharedPreferences("UserData",Context.MODE_PRIVATE)
        list.forEach {
            if(null != it){
                contentValues.put("protocol_id",protocol_id)
                contentValues.put("user_id",sp.getString("user_id","null"))
                contentValues.put("signatory_name",it)
                db.insert("protocolList_sign",null,contentValues)
                contentValues.clear()
            }
        }
    }

    fun clearProtocolList(user_id: String){
        db.delete("protocolList","user_id like ?", arrayOf(user_id))
        db.delete("protocolList_sign","user_id like ?", arrayOf(user_id))
    }

    /**
     * get a protocol detail from protocolList
     */
    fun getAProtocolFromProtocolList(protocol_id: String):ProtocolDetailBean{
        val cursor = db.query("protocolList",null,"protocol_id like ?", arrayOf(protocol_id),null,null,null)
        if(cursor.moveToFirst()){
            do {
                protocol = ProtocolDetailBean(
                        cursor.getString(cursor.getColumnIndex("protocol_id")),
                        cursor.getString(cursor.getColumnIndex("owner")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("content")),
                        null,
                        null,
                        cursor.getString(cursor.getColumnIndex("createAt")),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        0
                )
            }while (cursor.moveToNext())
        }
        return protocol
    }

    /**
     * get all protocol from protocolList
     */
    fun getProtocolList(user_id: String,pageNumber: String):List<ProtocolListBeanData>{
        val list = mutableListOf<ProtocolListBeanData>()
        val cursor = db.query("protocolList",null,"user_id like ? and pageNumber like ?", arrayOf(user_id,pageNumber),null,null,null)
        if (cursor.moveToFirst()){
            do {
               val bean = ProtocolListBeanData(
                        cursor.getString(cursor.getColumnIndex("protocol_id")),
                       null,
                        cursor.getString(cursor.getColumnIndex("content")),
                        cursor.getString(cursor.getColumnIndex("createAt")),
                       null,
                       "1",
                        getProtocolListSign(cursor.getString(cursor.getColumnIndex("protocol_id"))),
                        cursor.getString(cursor.getColumnIndex("signatoryNum")),
                       "1",
                        cursor.getString(cursor.getColumnIndex("title"))
                )
                list.add(bean)
            }while (cursor.moveToNext())
        }
        return list
    }

    /**
     * get the signed people from protocolList
     */
    fun getProtocolListSign(protocol_id: String):List<String>{
        val list = mutableListOf<String>()
        val cursor = db.query("protocolList_sign",null,"protocol_id like ?", arrayOf(protocol_id),null,null,null)
        if (cursor.moveToFirst()){
            do {
                list.add(cursor.getString(cursor.getColumnIndex("signatory_name")))
            }while (cursor.moveToNext())
        }
        return list

    }


}