package cn.winsky.travel.airporttravel.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.winsky.winto.base.R
import com.winsky.winto.base.dialog.OnItemClickListener
import kotlinx.android.synthetic.main.dialog_common.*

/**
  * <br/>================================================
  * <br/> @author：Blizzard-liu
  * <br/> @date：2018/7/25 18:14
  * <br/> @describe：通用dialog
  * <br/>================================================
  */
class CommonDialog(context: Context?, themeId: Int) : Dialog(context,themeId) {

    fun setMessage(msg: String) {
        tv_msg?.text = msg
    }

    fun setTitle(title: String) {
        tv_title?.text = title
    }

    class Builder(private var mContext: Context) {
        private var mTitleTv: TextView? = null
        private var mMessageTv: TextView? = null
        private var mLeftBtn: TextView? = null
        private var mRightBtn: TextView? = null
        private var mOnLeftClickListener: OnItemClickListener? = null
        private var mOnRightClickListener: OnItemClickListener? = null
        private var title: String = ""
        private var msg: String = ""
        private var msgSize: Int = 16
        private var mLeftText: String = ""
        private var mRightText: String =""
        private var mIsOneBtn: Boolean = false
        private var mIsShowTitle: Boolean = false
        private var msgColor: Int = -1
        private var leftBtnColor: Int = -1
        private var rightBtnColor: Int = -1

        fun setTitle(title: String): Builder {

            this.title = title
            return this
        }

        fun setMessage(msg: String): Builder {

            this.msg = msg
            return this
        }

        fun setMessageSize(size: Int): Builder {

            this.msgSize = size
            return this
        }

        fun setLeftText(leftstr: String): Builder {

            this.mLeftText = leftstr
            return this
        }
        fun setRightText(rightstr: String): Builder {

            this.mRightText = rightstr
            return this
        }

        fun setOneBtnText(midstr: String): Builder {

            this.mRightText = midstr
            return this
        }

        fun setOneBtn(isOneBtn: Boolean): Builder {

            this.mIsOneBtn = isOneBtn
            return this
        }

        fun setShowTitle(mIsShowTitle: Boolean): Builder {

            this.mIsShowTitle = mIsShowTitle
            return this
        }

        fun setMessageColor(msgColor: Int): Builder{
            this.msgColor = msgColor
            return this
        }

        fun setLeftBtnColor(leftBtnColor: Int): Builder{
            this.leftBtnColor = leftBtnColor
            return this
        }

        fun setRightBtnColor(rightBtnColor: Int): Builder{
            this.rightBtnColor = rightBtnColor
            return this
        }


        fun setLeftBtnClickListener(listener: OnItemClickListener): Builder {
            mOnLeftClickListener = listener
            return this
        }

        fun setRigthBtnClickListener(listener: OnItemClickListener): Builder {
            mOnRightClickListener = listener
            return this
        }

        fun setOneBtnClickListener(listener: OnItemClickListener): Builder {
            mOnRightClickListener = listener
            return this
        }


        fun create(): CommonDialog {
            val dialog = CommonDialog(mContext, R.style.custom_dialog_style)
            val inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_common, null)
            mTitleTv = inflate.findViewById(R.id.tv_title)
            mMessageTv = inflate.findViewById(R.id.tv_msg)
            mLeftBtn = inflate.findViewById(R.id.btn_left)
            mRightBtn = inflate.findViewById(R.id.btn_right)
            initData(dialog)
            dialog.setContentView(inflate)
            return dialog
        }

        private fun initData(dialog: CommonDialog) {

            if (title.isNotEmpty()) {

                mTitleTv!!.text = title
            }

            if (msg.isNotEmpty()) {

                mMessageTv!!.text = msg
                mMessageTv!!.textSize = msgSize.toFloat()
            }

            if (mLeftText.isNotEmpty()) {
                mLeftBtn!!.text = mLeftText
            }

            if (mRightText.isNotEmpty()) {
                mRightBtn!!.text = mRightText
            }

            if (msgColor != -1) {
                mMessageTv?.setTextColor(msgColor)
            }

            if (leftBtnColor != -1) {
                mLeftBtn?.setTextColor(leftBtnColor)
            }

            if (rightBtnColor != -1) {
                mRightBtn?.setTextColor(rightBtnColor)
            }

            if (mIsOneBtn) {
                //一个按钮
                mLeftBtn?.visibility = View.GONE
            }

            if (mIsShowTitle) {
                //显示标题
                mTitleTv?.visibility = View.VISIBLE
            }

            mRightBtn!!.setOnClickListener {
                dialog.dismiss()
                if (mOnRightClickListener != null) {
                    mOnRightClickListener!!.onItemClick()
                }
            }

            mLeftBtn!!.setOnClickListener { dialog.dismiss() }


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
               // val height = size.y
                params = window.attributes
                //            params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params!!.width = (width * 0.7).toInt()
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
                window.attributes = params
//                window.setGravity(Gravity.BOTTOM)
            }


            //            dialog.setCanceledOnTouchOutside(false);
            // dialog.setCancelable(false);
        }
    }


}
