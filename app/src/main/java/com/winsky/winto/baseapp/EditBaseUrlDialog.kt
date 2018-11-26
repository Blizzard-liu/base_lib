package com.winsky.winto.baseapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.support.v7.widget.LinearLayoutManager
import android.view.WindowManager
import com.winsky.winto.base.BaseApp
import com.winsky.winto.base.util.SPUtil
import kotlinx.android.synthetic.main.dialog_edit_base_url_layout.*

/**
 * <br/>================================================
 * <br/> @author：Blizzard-liu
 * <br/> @date：2018/8/9 9:25
 * <br/> @describe：
 * <br/>================================================
 */

class EditBaseUrlDialog(
                        context: Context, themeId: Int = R.style.custom_dialog_style) : Dialog(context, themeId) {

    init {
        val window = window
        val params: WindowManager.LayoutParams?
        if (window != null) {
            window.decorView.setPadding(0, 0, 0, 0)
            val m = window.windowManager
            // 获取屏幕宽、高用
            val display = m.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width = size.x
            // val height = size.y
            params = window.attributes
            //            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params!!.width = (width * 0.9).toInt()
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = params
//                window.setGravity(Gravity.BOTTOM)
        }
        // setCanceledOnTouchOutside(false);
        // setCancelable(false);

        setContentView(R.layout.dialog_edit_base_url_layout)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        val list = arrayListOf<String>().apply {
           add("https://app.win-sky.com.cn:9001")
           add("http://wp.win-sky.com.cn:16903")
           add("http://hefei.win-sky.com.cn:2180")
           add("http://120.234.32.69:16903")
           add("https://app.win-sky.com.cn:10000")
        }
        val adapter = EditBaseUrlAdapter(list)
        recyclerView!!.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->

            val url = adapter.data[position] as String
            edit_text.setText(url)
        }

        btn_left.setOnClickListener { dismiss() }
        btn_right.setOnClickListener {
            val url = edit_text.text.toString().trim()
            if (url.isEmpty()) {
//                ToastUtils.show(context,"请输入url")
                return@setOnClickListener
            }
            SPUtil.getInstance().put(NetworkPort.SP_KEY_IP,url)

//            SharedPreferencesUtils.setParam(context, StatusCode.islogin, false)

            dismiss()
            relaunchApp()

        }

        show()

    }

    /**
     * Relaunch the application.
     */
    fun relaunchApp() {
        val packageManager = BaseApp.instance.applicationContext.packageManager
        val intent = packageManager.getLaunchIntentForPackage(BaseApp.instance.applicationContext.packageName)
                ?: return
        val componentName = intent.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        BaseApp.instance.applicationContext.startActivity(mainIntent)
        System.exit(0)
    }

}
