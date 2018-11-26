package com.winsky.winto.baseapp

import android.os.SystemClock
import com.winsky.winto.base.activity.BaseActivity
import java.util.*

class MainActivity : BaseActivity() {
    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun enableSwipeBack(): Boolean {
        return false
    }

    // 需要点击几次 就设置几
    private var mHits: LongArray? = null

    fun onDisplaySettingButton() {
        if (mHits == null) {
            mHits = LongArray(3)
        }
        System.arraycopy(mHits!!, 1, mHits!!, 0, mHits!!.size - 1)//把从第二位至最后一位之间的数字复制到第一位至倒数第一位
        mHits!![mHits!!.size - 1] = SystemClock.uptimeMillis()//记录一个时间
        if (SystemClock.uptimeMillis() - mHits!![0] <= 1000) {//一秒内连续点击。
            mHits = null    //这里说明一下，我们在进来以后需要还原状态第六次，第七次 都会不断进来触发该效果。重新开始计数即可
            EditBaseUrlDialog(this@MainActivity, R.style.custom_dialog_style)
            if (checkPhone("19956518671")) {
                EditBaseUrlDialog(this@MainActivity, R.style.custom_dialog_style)
            }
        }
    }

    /**
     * 内部测试手机号
     * @param phone
     * @return
     */
    fun checkPhone(phone: String): Boolean {

        val list = ArrayList<String>()
        list.add("19956518671")
        return list.contains(phone)

    }


}
