package com.winsky.winto.baseapp

import com.winsky.winto.base.activity.BaseActivity

class MainActivity : BaseActivity() {
    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun enableSwipeBack(): Boolean {
        return false
    }


}
