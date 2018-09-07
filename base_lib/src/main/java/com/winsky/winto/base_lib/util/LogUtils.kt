package cn.winsky.travel.airporttravel.utils

import android.util.Log
import cn.winsky.travel.airporttravel.BuildConfig

/**
 * <br/>================================================
 * <br/> @author：Blizzard-liu
 * <br/> @date：2018/7/20 9:39
 * <br/> @describe：
 * <br/>================================================
 */


object LogUtils {
    private val TAG = "LogUtil"

    private var isShow = BuildConfig.DEBUG

    fun isShow(): Boolean {
        return isShow
    }

    fun setShow(show: Boolean) {
        isShow = show
    }

    fun i(tag: String, msg: String) {
        if (isShow) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (isShow) {
            Log.w(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (isShow) {
            Log.e(tag, msg)
        }
    }


    fun all(msg: String) {
        if (isShow) {
            Log.e("all", msg)
        }
    }


    fun i(msg: String) {
        if (isShow) {
            Log.i(TAG, msg)
        }
    }

    fun w(msg: String) {
        if (isShow) {
            Log.w(TAG, msg)
        }
    }

    fun e(msg: String) {
        if (isShow) {
            Log.e(TAG, msg)
        }
    }

    fun v(msg: String) {
        e(msg)
    }


    fun d(msg: String) {
        v(msg)
    }
}