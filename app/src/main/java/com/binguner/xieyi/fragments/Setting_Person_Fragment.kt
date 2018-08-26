package com.binguner.xieyi.fragments

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.binguner.xieyi.R
import com.binguner.xieyi.beans.FloaterBean
import com.binguner.xieyi.utils.MyImageLoader
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import kotlinx.android.synthetic.main.shake_protocol_item_layout.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.textChangedListener
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

lateinit var aactivity:Activity
class Setting_Person_Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val container = Setting_Person_FragmengUI().createView(AnkoContext.Companion.create(ctx,Setting_Person_Fragment()))
        return container
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    fun attachAty(activity: Activity){
        aactivity = activity
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
                Setting_Person_Fragment().apply {
                }
    }
}

class Setting_Person_FragmengUI:AnkoComponent<Setting_Person_Fragment>{

    val id_set_person_toolbar = View.generateViewId()
    val id_set_person_shadow= View.generateViewId()
    val id_set_person_back= View.generateViewId()
    val id_set_person_avator_layout= View.generateViewId()
    val id_set_person_avator= View.generateViewId()
    val id_set_person_username_layout= View.generateViewId()
    val id_set_person_username= View.generateViewId()
    val id_set_person_phonenumber_layout= View.generateViewId()
    val id_set_person_phonenumber= View.generateViewId()
    val id_set_person_email_layout = View.generateViewId()
    val id_set_person_email = View.generateViewId()
    val id_set_person_password_layout = View.generateViewId()
    val id_set_person_password= View.generateViewId()
    val id_set_person_alert_ok= View.generateViewId()
    val id_set_person_alert_cancel= View.generateViewId()
    val id_set_person_alert_content_ed= View.generateViewId()
    val id_set_person_alert_content_ed_password_again= View.generateViewId()
    val id_set_person_alert_custom_title= View.generateViewId()
    val id_set_person_alert_custom_title_phonenumber= View.generateViewId()

    var person_settting_dialog_username : DialogInterface ?= null
    var person_settting_dialog_phonenumber : DialogInterface ?= null
    var person_settting_dialog_email : DialogInterface ?= null
    var person_settting_dialog_password : DialogInterface ?= null
    val id_set_recycler_view = View.generateViewId()
    var items = mutableListOf<FloaterBean>()
    val choosePhotoslists = listOf<String>("从相册中选择","拍一张")

    lateinit var usernmae:String
    lateinit var phonenumber:String
    lateinit var email:String
    lateinit var password:String
    lateinit var passwordAgain:String

    override fun createView(ui: AnkoContext<Setting_Person_Fragment>) = with(ui) {

        constraintLayout(){

            backgroundColor = ContextCompat.getColor(ctx,R.color.colorNormalBack)

            val set_person_toolbar = include<View>(R.layout.toolbar_layout){
                id = id_set_person_toolbar
            }.lparams(width = matchParent){
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            val set_person_shadow = include<View>(R.layout.shadow_line){
                id = id_set_person_shadow
            }.lparams(width = matchParent){
                topToBottom = id_set_person_toolbar
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            textView(){
                text = "修改个人资料"
            }.lparams(){
                topToTop = id_set_person_toolbar
                bottomToBottom  = id_set_person_toolbar
                startToStart = id_set_person_toolbar
                endToEnd = id_set_person_toolbar
            }

            val set_person_back = imageView(){
                id = id_set_person_back
                setImageResource(R.drawable.ic_arrow_back_black_24dp)
                onClick {
                    //Log.d("sjmfoiasjfo;isad","back clicked")
                    //owner.activity?.finish()
                    if(null != aactivity ){
                        aactivity?.finish()
                    }
                }
            }.lparams(){
                topToTop = id_set_person_toolbar
                bottomToBottom = id_set_person_toolbar
                startToStart = PARENT_ID
                leftMargin = dip(10)
            }

            // avator
            val set_person_avator_layout = constraintLayout {
                id = id_set_person_avator_layout
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)

                // text: change avator
                textView(){
                    text = "更改头像"
                    textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
                }.lparams(){
                    topToTop = PARENT_ID
                    startToStart = PARENT_ID
                    bottomToBottom = PARENT_ID
                    leftMargin = dip(20)
                }

                // avtor
                val set_person_circleimagevie = include<View>(R.layout.circle_imageview){
                    id = id_set_person_avator
                }.lparams{
                    topToTop = PARENT_ID
                    bottomToBottom = PARENT_ID
                    endToEnd = PARENT_ID
                    rightMargin = dip(12)
                }

                // click listener
                onClick {
                    //toast("change avatar")
                    selector(null,choosePhotoslists){
                        dialogInterface, i ->
                        when(i){
                            0 -> {
                                if(null != aactivity) {
                                    //Log.d("iaaaa","isAdded")
                                    chooseFromGallery(ctx, aactivity)
                                }
                            }
                            1 -> toast("Take a photo")
                        }
                    }
                }

            }.lparams(height = dip(100), width = dip(0)){
                topToBottom = id_set_person_shadow
                topMargin = dip(10)
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            // Useername
            constraintLayout(){
                id = id_set_person_username_layout
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)

                //  text : Change username
                textView{
                    text = "修改昵称"
                    textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
                }.lparams(){
                    topToTop = PARENT_ID
                    startToStart = PARENT_ID
                    bottomToBottom = PARENT_ID
                    leftMargin = dip(20)
                }

                // username
                val set_person_username = textView(){
                    text = "Binguenr"
                    id = id_set_person_username
                }.lparams(){
                    topToTop = PARENT_ID
                    bottomToBottom = PARENT_ID
                    endToEnd = PARENT_ID
                    rightMargin = dip(18)
                }

                // Click Listener
                onClick {
                    //toast("Change username")
                    person_settting_dialog_username = alert {
                        backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)

                        customTitle {
                            id = id_set_person_alert_custom_title
                            //backgroundColor = ContextCompat.getColor(ctx, R.color.colorLightGrey)
                            textView(){
                                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAlertDialogToolbar)
                                text = "设置昵称"
                                textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
                                textSize = 20f
                                gravity = Gravity.CENTER
                                topPadding = dip(16)
                                bottomPadding = dip(16)
                            }
                        }
                        customView {
                            constraintLayout {

                                topPadding = dip(20)
                                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAlertDialogContentBackground)

                                val set_person_alert_content_ed = editText(){
                                    id = id_set_person_alert_content_ed
                                    singleLine = true
                                    setBackgroundResource(R.drawable.alert_edittext_style)
                                    textChangedListener {
                                        afterTextChanged(){
                                            usernmae = this@editText.text.toString()
                                        }
                                    }
                                }.lparams(width = dip(0), height = dip(35)){
                                    topToBottom = PARENT_ID
                                    startToStart = PARENT_ID
                                    endToEnd = PARENT_ID
                                    bottomToTop = id_set_person_alert_cancel
                                    leftMargin = dip(20)
                                    rightMargin = dip(20)
                                }

                                button(){
                                    text = "Cancel"
                                    id = id_set_person_alert_cancel

                                    onClick {
                                        person_settting_dialog_username?.dismiss()
                                    }
                                }.lparams(width = dip(0)){
                                    topToBottom = id_set_person_alert_content_ed
                                    topMargin = dip(10)
                                    bottomToBottom = PARENT_ID
                                    endToStart = id_set_person_alert_ok
                                    startToStart = PARENT_ID
                                }

                                button(){
                                    text = "OK"
                                    id = id_set_person_alert_ok
                                    onClick {
                                        set_person_username.text = usernmae
                                        person_settting_dialog_username?.dismiss()
                                    }
                                }.lparams(width = dip(0)){
                                    topToBottom = id_set_person_alert_content_ed
                                    startToEnd = id_set_person_alert_cancel
                                    topMargin = dip(10)
                                    bottomToBottom = PARENT_ID
                                    endToEnd = PARENT_ID
                                }
                            }

                        }
                    }.show()
                }

            }.lparams(width = dip(0), height = dip(60)){
                topToBottom = id_set_person_avator_layout
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topMargin = dip(10)
            }

            // Phonenumber
            constraintLayout(){
                id = id_set_person_phonenumber_layout
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)

                // text: change phonenumber
                textView{
                    text = "修改手机号"
                    textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
                }.lparams(){
                    topToTop = PARENT_ID
                    startToStart = PARENT_ID
                    bottomToBottom = PARENT_ID
                    leftMargin = dip(20)
                }

                // phone number
                 val set_person_phonenumber = textView(){
                    id = id_set_person_phonenumber
                    text = "123123123123"
                }.lparams(){
                    topToTop = PARENT_ID
                    bottomToBottom = PARENT_ID
                    endToEnd = PARENT_ID
                    rightMargin = dip(18)
                }

                // click listenre
                onClick {
                    //toast("Change phonenumber")
                    person_settting_dialog_phonenumber = alert {
                        backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                        customTitle {
                            //backgroundColor = ContextCompat.getColor(ctx, R.color.colorLightGrey)
                            textView(){
                                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAlertDialogToolbar)
                                text = "设置手机号码"
                                textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
                                textSize = 20f
                                gravity = Gravity.CENTER
                                topPadding = dip(16)
                                bottomPadding = dip(16)
                            }
                        }
                        customView {
                            constraintLayout {
                                topPadding = dip(20)
                                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAlertDialogContentBackground)

                                val set_person_alert_content_ed = editText(){
                                    id = id_set_person_alert_content_ed
                                    singleLine = true
                                    inputType = InputType.TYPE_CLASS_NUMBER
                                    setBackgroundResource(R.drawable.alert_edittext_style)
                                    phonenumber = this@editText.text.toString()
                                }.lparams(width = dip(0), height = dip(35)){
                                    topToBottom = PARENT_ID
                                    startToStart = PARENT_ID
                                    endToEnd = PARENT_ID
                                    bottomToTop = id_set_person_alert_cancel
                                    leftMargin = dip(20)
                                    rightMargin = dip(20)
                                }

                                button(){
                                    text = "Cancel"
                                    id = id_set_person_alert_cancel

                                    onClick {
                                        person_settting_dialog_phonenumber?.dismiss()
                                    }
                                }.lparams(width = dip(0)){
                                    topToBottom = id_set_person_alert_content_ed
                                    topMargin = dip(10)
                                    bottomToBottom = PARENT_ID
                                    endToStart = id_set_person_alert_ok
                                    startToStart = PARENT_ID
                                }

                                button(){
                                    text = "OK"
                                    id = id_set_person_alert_ok
                                    onClick {
                                        set_person_phonenumber.text = phonenumber
                                        person_settting_dialog_phonenumber?.dismiss()
                                    }
                                }.lparams(width = dip(0)){
                                    topToBottom = id_set_person_alert_content_ed
                                    startToEnd = id_set_person_alert_cancel
                                    topMargin = dip(10)
                                    bottomToBottom = PARENT_ID
                                    endToEnd = PARENT_ID
                                }
                            }

                        }
                    }.show()
                }

            }.lparams(width = dip(0), height = dip(60)){
                topToBottom = id_set_person_username_layout
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topMargin = dip(10)
            }

            // email
            constraintLayout(){
                id = id_set_person_email_layout
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)

                // text: change email
                textView(){
                    text = "修改邮箱"
                    textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
                }.lparams(){
                    topToTop = PARENT_ID
                    startToStart = PARENT_ID
                    bottomToBottom = PARENT_ID
                    leftMargin = dip(20)
                }

                //  email
                val set_person_email = textView(){
                    id = id_set_person_email
                    text = "478718805@qq.com"

                }.lparams(){
                    topToTop = PARENT_ID
                    bottomToBottom = PARENT_ID
                    endToEnd = PARENT_ID
                    rightMargin = dip(18)
                }

                // click listener
                onClick {
                    //toast("Change email ")
                    person_settting_dialog_email = alert {
                        backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                        customTitle {
                            //backgroundColor = ContextCompat.getColor(ctx, R.color.colorLightGrey)
                            textView(){
                                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAlertDialogToolbar)
                                text = "设置邮箱地址"
                                textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
                                textSize = 20f
                                gravity = Gravity.CENTER
                                topPadding = dip(16)
                                bottomPadding = dip(16)
                            }
                        }
                        customView {
                            constraintLayout {
                                topPadding = dip(20)
                                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAlertDialogContentBackground)

                                val set_person_alert_content_ed = editText(){
                                    id = id_set_person_alert_content_ed
                                    singleLine = true
                                    inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                                    setBackgroundResource(R.drawable.alert_edittext_style)
                                    email = this@editText.text.toString()
                                }.lparams(width = dip(0), height = dip(35)){
                                    topToBottom = PARENT_ID
                                    startToStart = PARENT_ID
                                    endToEnd = PARENT_ID
                                    bottomToTop = id_set_person_alert_cancel
                                    leftMargin = dip(20)
                                    rightMargin = dip(20)
                                }

                                button(){
                                    text = "Cancel"
                                    id = id_set_person_alert_cancel

                                    onClick {
                                        person_settting_dialog_email?.dismiss()
                                    }
                                }.lparams(width = dip(0)){
                                    topToBottom = id_set_person_alert_content_ed
                                    topMargin = dip(10)
                                    bottomToBottom = PARENT_ID
                                    endToStart = id_set_person_alert_ok
                                    startToStart = PARENT_ID
                                }

                                button(){
                                    text = "OK"
                                    id = id_set_person_alert_ok
                                    onClick {
                                        set_person_email.text = email
                                        person_settting_dialog_email?.dismiss()
                                    }
                                }.lparams(width = dip(0)){
                                    topToBottom = id_set_person_alert_content_ed
                                    startToEnd = id_set_person_alert_cancel
                                    topMargin = dip(10)
                                    bottomToBottom = PARENT_ID
                                    endToEnd = PARENT_ID
                                }
                            }

                        }
                    }.show()
                }
                // id_set_person_email_layout
            }.lparams(width = dip(0), height = dip(60)){
                topToBottom = id_set_person_phonenumber_layout
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topMargin = dip(10)
            }

            // password
            constraintLayout(){
                id = id_set_person_password_layout
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)

                // text: change passowrd
                textView(){
                    text = "修改密码"
                    textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
                }.lparams(){
                    topToTop = PARENT_ID
                    startToStart = PARENT_ID
                    bottomToBottom = PARENT_ID
                    leftMargin = dip(20)
                }

                //  password *********
                val set_person_password = textView(){
                    id = id_set_person_password
                    text = "***********"
                    textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
                }.lparams(){
                    topToTop = PARENT_ID
                    bottomToBottom = PARENT_ID
                    endToEnd = PARENT_ID
                    rightMargin = dip(18)
                }

                // click listener
                onClick {
                    //toast("Change Password")
                    person_settting_dialog_password = alert {
                        backgroundColor = ContextCompat.getColor(ctx, R.color.colorWhite)
                        customTitle {
                            //backgroundColor = ContextCompat.getColor(ctx, R.color.colorLightGrey)
                            textView(){
                                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAlertDialogToolbar)
                                text = "设置新密码"
                                textColor = ContextCompat.getColor(ctx, R.color.colorBlack)
                                textSize = 20f
                                gravity = Gravity.CENTER
                                topPadding = dip(16)
                                bottomPadding = dip(16)
                            }
                        }
                        customView {
                            constraintLayout {
                                topPadding = dip(20)
                                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAlertDialogContentBackground)

                                val set_person_alert_content_ed = editText(){
                                    id = id_set_person_alert_content_ed
                                    singleLine = true
                                    // ********************
                                    transformationMethod = PasswordTransformationMethod.getInstance()
                                    //inputType = EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD
                                    setBackgroundResource(R.drawable.alert_edittext_style)
                                    textChangedListener {
                                        afterTextChanged {
                                            password = this@editText.text.toString()
                                        }
                                    }
                                }.lparams(width = dip(0), height = dip(35)){
                                    topToBottom = PARENT_ID
                                    startToStart = PARENT_ID
                                    endToEnd = PARENT_ID
                                    bottomToTop = id_set_person_alert_content_ed
                                    leftMargin = dip(20)
                                    rightMargin = dip(20)
                                }

                                val set_person_alert_contentt_ed_password_again = editText(){
                                    id = id_set_person_alert_content_ed_password_again
                                    singleLine = true
                                    hint = "请确认密码"
                                    transformationMethod = PasswordTransformationMethod.getInstance()
                                    setBackgroundResource(R.drawable.alert_edittext_style)
                                    textChangedListener {
                                        afterTextChanged(){
                                            passwordAgain = this@editText.text.toString()
                                        }
                                    }
                                }.lparams(width = dip(0), height = dip(35)){
                                    topToBottom = id_set_person_alert_content_ed
                                    startToStart = PARENT_ID
                                    endToEnd = PARENT_ID
                                    topMargin = dip(20)
                                    bottomToTop = id_set_person_alert_cancel
                                    leftMargin = dip(20)
                                    rightMargin = dip(20)
                                }



                                button(){
                                    text = "Cancel"
                                    id = id_set_person_alert_cancel

                                    onClick {
                                        person_settting_dialog_password?.dismiss()
                                    }
                                }.lparams(width = dip(0)){
                                    topToBottom = id_set_person_alert_content_ed_password_again
                                    topMargin = dip(10)
                                    bottomToBottom = PARENT_ID
                                    endToStart = id_set_person_alert_ok
                                    startToStart = PARENT_ID
                                }

                                button(){
                                    text = "OK"
                                    id = id_set_person_alert_ok
                                    onClick {
                                        if(!password.equals(passwordAgain)){
                                            toast("$password and $passwordAgain 两次密码输入不一致，请重试")
                                        }else{

                                        }
                                    }
                                }.lparams(width = dip(0)){
                                    topToBottom = id_set_person_alert_content_ed_password_again
                                    startToEnd = id_set_person_alert_cancel
                                    topMargin = dip(10)
                                    bottomToBottom = PARENT_ID
                                    endToEnd = PARENT_ID
                                }
                            }
                        }
                    }.show()
                }

            }.lparams(width = dip(0), height = dip(60)){
                topToBottom = id_set_person_email_layout
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topMargin = dip(10)
            }







        }

    }

    private fun chooseFromGallery(context: Context, activity: Activity) {
        /*if(ContextCompat.checkSelfPermission(context,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Log.d("iaaaa","askPermissoning")
            Log.d("iaaaa","${activity.callingPackage} and ${activity.localClassName}")
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
            Toast.makeText(context,"t",Toast.LENGTH_LONG).show()
            Log.d("iaaaa","notPermissoned")
        }else{
            Log.d("iaaaa","isPermissoned")
        }*/
        /*Matisse.from(activity)
                .choose(MimeType.ofAll())
                .countable(false)
                .maxSelectable(1)
                .gridExpectedSize(activity.resources.getDimensionPixelSize(R.dimen.media_grid_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(3.15f)
                //.imageEngine(GlideEngine())
                .imageEngine(PicassoEngine())
                .forResult(1);*/
        val imagePicker = ImagePicker.getInstance()
        imagePicker.setImageLoader(MyImageLoader(context))
        imagePicker.isShowCamera = true
        imagePicker.isCrop = true
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        var i = Intent(context,ImageGridActivity::class.java)
        activity.startActivityForResult(i,123)



    }

}
