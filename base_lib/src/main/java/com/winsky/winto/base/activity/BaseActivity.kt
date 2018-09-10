package com.winsky.winto.base.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import cn.winsky.travel.airporttravel.utils.LogUtils
import cn.winsky.travel.airporttravel.view.dialog.LoadingDialog
import com.jaeger.library.StatusBarUtil
import com.winsky.winto.base.R
import com.winsky.winto.base.util.Density
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * <br/>================================================
 * <br/> @author：Blizzard-liu
 * <br/> @date：2018/9/7 15:44
 * <br/> @describe：
 * <br/>================================================
 */

abstract class BaseActivity : AppCompatActivity() {
    private val TAG = "BaseActivity"
    private lateinit var sub_Activity: String
    private lateinit var linActivityBaseCustom: RelativeLayout
    protected abstract val layoutResId: Int
    private var mLoadingDialog: LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        sub_Activity = javaClass.simpleName
        LogUtils.w(TAG, "$sub_Activity=========================== onCreate ")
        setOrientation()
        setContentView(R.layout.activity_base)
        setStatusBar()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT   //设置禁止横屏

        val view = LayoutInflater.from(this)
                .inflate(layoutResId, null)
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layout_content?.addView(view)

        toolbar_return_iv?.setOnClickListener { finish() }

        toolbar_title?.run { text = title }  //getTitle()的值是activity的android:lable属性值
        if (openNewsListening()) {
            //是否开启消息监听
            EventBus.getDefault().register(this)
        }
        initView(savedInstanceState)
        initData(savedInstanceState)
        initListener(savedInstanceState)

    }

    /**
     * 打开消息监听
     * @return
     */
    protected open fun openNewsListening(): Boolean {
        return false
    }


    protected open fun initView(savedInstanceState: Bundle?) {

    }

    protected open fun initData(savedInstanceState: Bundle?) {

    }

    protected open fun initListener(savedInstanceState: Bundle?) {

    }

    override fun onRestart() {
        super.onRestart()
        LogUtils.w(TAG, "$sub_Activity=========================== onRestart ")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.w(TAG, "$sub_Activity=========================== onStart ")

    }

    override fun onResume() {
        super.onResume()
        LogUtils.w(TAG, "$sub_Activity=========================== onResume ")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.w(TAG, "$sub_Activity=========================== onPause ")
    }


    override fun onDestroy() {
        LogUtils.w(TAG, "$sub_Activity=========================== onDestroy ")
        if (openNewsListening()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
        dismissProgressDialog()
    }

    @JvmOverloads
    fun showProgressDialog(str: String = "正在加载...") {

        if (mLoadingDialog == null) {

            mLoadingDialog = LoadingDialog(this, str)
        }

        mLoadingDialog?.show()
    }

    fun dismissProgressDialog() {
        mLoadingDialog?.dismiss()

    }


    private fun setStatusBar() {
        //        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this,R.color.white));
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white))
        //        StatusBarUtil.setLightMode(this);
    }

    fun setOrientation() {
        Density.setDefault(this)
    }

    fun setToolBarTitle(str: String) {
        toolbar_title?.text = str
    }

}
