package com.winsky.winto.base.util

import android.app.Activity
import android.util.Log
import cn.winsky.travel.airporttravel.view.dialog.CommonDialog
import com.winsky.winto.base.dialog.OnItemClickListener
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Rationale
import com.yanzhenjie.permission.runtime.PermissionRequest

/**
 * <br></br>================================================
 * <br></br> @author：liu_bo
 * <br></br> @date：2018/4/16 13:59
 * <br></br> @describe：权限工具类，尽量在需要授权的地方调用 http://www.yanzhenjie.com/AndPermission/cn/usage.html
 * <br></br>================================================
 */
class PermissionUtil {

    private lateinit var mActivity: Activity

    private var mDialog: CommonDialog? = null

    private var mRequestDialog: CommonDialog? = null
    private val mRationale by lazy { Rationale < Any > { _, _, executor ->
        // 这里使用一个Dialog询问用户是否继续授权。
        Log.d(TAG, "--------------------------showRationale 询问用户是否继续授权 --------------------------------")
        if (mDialog == null) {
            mDialog =  CommonDialog.Builder(mActivity)
                    .setMessage("为保证功能正常使用，需要您授权以下权限")
                    .setLeftBtnClickListener(OnItemClickListener {
                        // 如果用户中断：
                        executor.cancel() })
                    .setRigthBtnClickListener(OnItemClickListener {
                        // 如果用户继续：
                        executor.execute()
                    })
                    .create()
        }

        mDialog?.show()

    }
}
    private var mRequest: PermissionRequest? = null

    private var mOnGrantedListener: OnGrantedListener? = null


    fun requestPermissions(activity: Activity, listener: OnGrantedListener, vararg permissions: String) {

        mActivity = activity
        mOnGrantedListener = listener

        mRequest = AndPermission.with(activity)
                .runtime()
                .permission(*permissions)

        doRequest()


    }


    fun requestPermissions(activity: Activity, listener: OnGrantedListener, vararg groups: Array<String>) {

        mActivity = activity
        mOnGrantedListener = listener

        mRequest = AndPermission.with(activity)
                .runtime()
                .permission(*groups)

        doRequest()


    }

    private fun doRequest() {
        mRequest!!
                //                .rationale(mRationale)
                .onGranted {
                    if (mOnGrantedListener != null) {
                        mOnGrantedListener!!.onGranted()
                    }

                    Log.d(TAG, "--------------------------onGranted --------------------------------")
                }
                .onDenied { data ->
                    Log.d(TAG, "--------------------------onDenied --------------------------------")

                    if (AndPermission.hasAlwaysDeniedPermission(mActivity, data)) {
                        // 这些权限被用户总是拒绝。
                        // 这里使用一个Dialog展示没有这些权限应用程序无法继续运行，询问用户是否去设置中授权。
                        Log.d(TAG, "--------------------------hasAlwaysDeniedPermission 询问用户是否去设置中授权--------------------------------")
                        // 这里使用一个Dialog展示没有这些权限应用程序无法继续运行，询问用户是否去设置中授权。

                        if (mRequestDialog == null) {
                            mRequestDialog =  CommonDialog.Builder(mActivity)
                                    .setMessage("为保证功能正常使用,需要您手动去设置中授权")
                                    .setRigthBtnClickListener(OnItemClickListener {
                                        // 如果用户同意去设置：
                                        AndPermission.with(mActivity)
                                                .runtime()
                                                .setting()
                                                .onComeback {
                                                    // 用户从设置回来了。
                                                }
                                                .start()
                                    })
                                    .create()
                        }

                        mRequestDialog?.show()

                    }
                }
                .start()

    }

    interface OnGrantedListener {
        fun onGranted()
    }

    companion object {

        private val TAG = "PermissionAddUtil"
    }


}
