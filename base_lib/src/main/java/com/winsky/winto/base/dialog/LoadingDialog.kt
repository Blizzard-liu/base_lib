package cn.winsky.travel.airporttravel.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.TextView
import com.winsky.winto.base.R


/**
 * <br></br>================================================
 * <br></br> @author：Blizzard-liu
 * <br></br> @date：2018/5/24 13:25
 * <br></br> @describe：
 * <br></br>================================================
 */
class LoadingDialog(activity: Activity,strMessage: String = "正在加载...") {

    private var dialog: Dialog? = null

    init {
        createLoadingDialog(activity,strMessage)
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @return
     */
    private fun createLoadingDialog(context: Context,strMessage: String = "正在加载...") {

        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_loading_view, null)
        dialog = builder.setView(dialogView).create()
        val message = dialogView.findViewById<TextView>(R.id.message)
        message.text = strMessage

    }

    fun show() {
        dialog?.run {
            if(!isShowing) show()
        }
    }

    fun dismiss() {
        dialog?.run {
            if(isShowing) dismiss()
        }
    }
}
