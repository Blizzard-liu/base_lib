package cn.winsky.travel.airporttravel.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.winsky.winto.base.R
import com.winsky.winto.base.dialog.OnItemClickListener

/**
  * <br/>================================================
  * <br/> @author：Blizzard-liu
  * <br/> @date：2018/7/26 9:51
  * <br/> @describe：底部弹框
  * <br/>================================================
  */
class BottomCommonDialog(context: Context?, themeId: Int) : Dialog(context,themeId) {

    class Builder(private var mContext: Context) {
        private lateinit var mTitleTv: TextView
        private lateinit var mMessageTv: TextView
        private lateinit var mSecondMessageTv: TextView
        private lateinit var mCancelTv: TextView
        private var mOnItemClickListener: OnItemClickListener? = null
        private var mOnItemSecondClickListener: OnItemClickListener? = null
        private var title: String = ""
        private var msg: String = ""
        private var secondMsg: String = ""
        private var cancelText: String = ""
        private var msgColor: Int = -1
        private var secondMsgColor: Int = -1
        private var btnColor: Int = -1
        private var titleColor: Int = -1
        private var titleSize: Int = -1

        fun setTitle(title: String): Builder {

            this.title = title
            return this
        }

        fun setMessage(msg: String): Builder {

            this.msg = msg
            return this
        }

        fun setSecondMessage(secondMsg: String): Builder {

            this.secondMsg = secondMsg
            return this
        }

        fun setCancelText(cancelText: String): Builder {

            this.cancelText = cancelText
            return this
        }

        fun setMessageColor(msgColor: Int): Builder {
            this.msgColor = msgColor
            return this
        }

        fun setSecondMessageColor(secondMsgColor: Int): Builder {
            this.secondMsgColor = secondMsgColor
            return this
        }

        fun setTitleColor(titleColor: Int): Builder {
            this.titleColor = titleColor
            return this
        }

        fun setBtnColor(btnColor: Int): Builder {
            this.btnColor = btnColor
            return this
        }

        fun setTitleSize(titleSize: Int): Builder {
            this.titleSize = titleSize
            return this
        }



        fun setBtnClickListener(listener: OnItemClickListener): Builder {
            mOnItemClickListener = listener
            return this
        }

        fun setSecondBtnClickListener(listener: OnItemClickListener): Builder {
            mOnItemSecondClickListener = listener
            return this
        }

        fun create(): BottomCommonDialog {
            val dialog = BottomCommonDialog(mContext, R.style.bottom_dialog)
            val inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_bottom_view, null)
            mTitleTv = inflate.findViewById(R.id.tv_title)
            mMessageTv = inflate.findViewById(R.id.tv_message)
            mSecondMessageTv = inflate.findViewById(R.id.tv_second_message)
            mCancelTv = inflate.findViewById(R.id.tv_cancel)
            initData(dialog)
            dialog.setContentView(inflate)
            return dialog
        }

        private fun initData(dialog: BottomCommonDialog) {
            if (!TextUtils.isEmpty(title)) {
                mTitleTv.text = title
            } else {
                mTitleTv.visibility = View.GONE
            }

            mMessageTv.text = msg

            if (!TextUtils.isEmpty(secondMsg)) {
                mSecondMessageTv.text = secondMsg
                mSecondMessageTv.visibility = View.VISIBLE
            }

            if (!TextUtils.isEmpty(cancelText)) {
                mCancelTv.text = cancelText
            }
            if (msgColor != -1) {
                mMessageTv.setTextColor(msgColor)
            }

            if (secondMsgColor != -1) {
                mSecondMessageTv.setTextColor(secondMsgColor)
            }

            if (titleColor != -1) {
                mTitleTv.setTextColor(titleColor)
            }

            if (titleSize != -1) {
                mTitleTv.textSize = titleSize.toFloat()
            }

            if (btnColor != -1) {
                mCancelTv.setTextColor(btnColor)
            }


            mMessageTv.setOnClickListener {
                dialog.dismiss()
                mOnItemClickListener?.onItemClick()

            }

            mSecondMessageTv.setOnClickListener {
                dialog.dismiss()
                mOnItemSecondClickListener?.onItemClick()

            }

            mCancelTv.setOnClickListener { dialog.dismiss() }


            val window = dialog.window

            val params: WindowManager.LayoutParams?
            if (window != null) {
                window.decorView.setPadding(0, 0, 0, 0)
                val m = window.windowManager
                // 获取屏幕宽、高用
                val display = m.defaultDisplay
                val size = Point()
                display.getSize(size)
                val width = size.x
              //  val height = size.y
                params = window.attributes
                //            params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params!!.width = (width * 0.9).toInt()
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
                window.attributes = params
                window.setGravity(Gravity.BOTTOM)
            }


            //            dialog.setCanceledOnTouchOutside(false);
            // dialog.setCancelable(false);
        }
    }


}
