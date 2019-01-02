package com.winsky.winto.base.activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import cn.winsky.travel.airporttravel.utils.LogUtils
import cn.winsky.travel.airporttravel.view.dialog.LoadingDialog
import org.greenrobot.eventbus.EventBus

/**
 * <br></br>================================================
 * <br></br> @author：Blizzard-liu
 * <br></br> @date：2018/5/7 16:07
 * <br></br> @describe：
 * <br></br>================================================
 */
abstract class BaseFragment : Fragment() {
    private var SUB_FRAGMENT = ""

    //布局view
    protected var rootView: View? = null
    protected var mActivity: FragmentActivity? = null
    private var mLoadingDialog: LoadingDialog? = null
    //Fragment的View加载完毕的标记
    private var isViewCreated: Boolean = false

    //Fragment对用户可见的标记
    private var isUIVisible: Boolean = false


    protected abstract val layoutResId: Int

    fun showProgressDialog() {
        showProgressDialog("正在加载...")
    }

    fun showProgressDialog(str: String) {

        activity?.let {
            if (mLoadingDialog == null) {

                mLoadingDialog = LoadingDialog(it, str)
            }

            mLoadingDialog?.show()
        }

    }


    fun dismissProgressDialog() {

        mLoadingDialog?.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SUB_FRAGMENT = javaClass.simpleName
        LogUtils.w(TAG, "$SUB_FRAGMENT=========================== onCreate ")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtils.w(TAG, "$SUB_FRAGMENT=========================== onCreateView ")
        rootView = inflater.inflate(layoutResId, container, false)
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isViewCreated = true
        lazyLoad()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true
            lazyLoad()
        } else {
            isUIVisible = false
        }
    }

   open fun lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            lazyLoadData()
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false
            isUIVisible = false

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.w(TAG, "$SUB_FRAGMENT=========================== onActivityCreated ")
        if (openNewsListening()) {
            //是否开启消息监听
            EventBus.getDefault().register(this)
        }
        mActivity = activity
        initView(rootView)
        initData()
        initListener()
        initSoftInput()

    }

    abstract fun initView(layout: View?)

    abstract fun initData()
    /**
     * 数据懒加载
     */
    protected open fun lazyLoadData() {}

    abstract fun initListener()

    private fun initSoftInput() {

        if (mActivity == null) return
        if (rootView != null) {
            rootView!!.setOnTouchListener(View.OnTouchListener { _, _ ->
                if (null != mActivity!!.currentFocus && mActivity!!.currentFocus!!.windowToken != null) {
                    /**
                     * 点击空白位置 隐藏软键盘
                     */
                    /**
                     * 点击空白位置 隐藏软键盘
                     */
                    val mInputMethodManager = mActivity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                    return@OnTouchListener mInputMethodManager.hideSoftInputFromWindow(mActivity!!.currentFocus!!.windowToken, 0)

                }
                false
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.w(TAG, "$SUB_FRAGMENT=========================== onDestroyView ")
        if (null != rootView) {
            (rootView!!.parent as ViewGroup).removeView(rootView)
        }
        hideSoftInput()
        if (openNewsListening()) {
            //销毁消息监听
            EventBus.getDefault().unregister(this)
        }
    }

    /**
     * 隐藏软键盘
     */
    private fun hideSoftInput() {
        if (mActivity == null) return
        val view = mActivity!!.window.decorView
        hideSoftInput(view)
    }


    /*  public void sendMessage(BaseEvent msg) {
        if (msg != null) {
//            EventBus.getDefault().post(msg);
        }
    }*/
    /**
     * 打开消息监听
     * @return
     */
    open fun openNewsListening(): Boolean {
        return false
    }


    override fun onStart() {
        super.onStart()
        LogUtils.w(TAG, "$SUB_FRAGMENT=========================== onStart ")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.w(TAG, "$SUB_FRAGMENT=========================== onResume ")
    }


    override fun onPause() {
        super.onPause()
        LogUtils.w(TAG, "$SUB_FRAGMENT=========================== onPause ")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.w(TAG, "$SUB_FRAGMENT=========================== onStop ")
    }


    override fun onDestroy() {
        super.onDestroy()
        //检测内存泄露
        //        RefWatcher refWatcher = GApp.getRefWatcher(_mActivity);
        //        refWatcher.watch(this);
        LogUtils.w(TAG, "$SUB_FRAGMENT=========================== onDestroy ")
        dismissProgressDialog()
    }

    companion object {
        private const val TAG = "BaseFragment"

        /**
         * 隐藏软键盘
         */
        fun hideSoftInput(view: View?) {
            if (view == null || view.context == null) return
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}




