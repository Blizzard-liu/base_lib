//package cn.winsky.travel.airporttravel.view.dialog
//
//import android.app.Dialog
//import android.content.Context
//import android.graphics.Point
//import android.support.v7.widget.LinearLayoutManager
//import android.view.WindowManager
//import com.winsky.winto.base.R
//import kotlinx.android.synthetic.main.dialog_id_card_type.*
//
///**
// * <br/>================================================
// * <br/> @author：Blizzard-liu
// * <br/> @date：2018/8/9 9:25
// * <br/> @describe：证件类型
// * <br/>================================================
// */
//
//class IDTypeDialog(listener: OnIdSelectListener,
//                   context: Context, themeId: Int = R.style.custom_dialog_style) : Dialog(context, themeId) {
//
//    init {
//        val window = window
//        val params: WindowManager.LayoutParams?
//        if (window != null) {
//            window.decorView.setPadding(0, 0, 0, 0)
//            val m = window.windowManager
//            // 获取屏幕宽、高用
//            val display = m.defaultDisplay
//            val size = Point()
//            display.getSize(size)
//            val width = size.x
//            // val height = size.y
//            params = window.attributes
//            //            params.width = WindowManager.LayoutParams.MATCH_PARENT;
//            params!!.width = (width * 0.7).toInt()
//            params.height = WindowManager.LayoutParams.WRAP_CONTENT
//            window.attributes = params
////                window.setGravity(Gravity.BOTTOM)
//        }
//        // setCanceledOnTouchOutside(false);
//        // setCancelable(false);
//
//        setContentView(R.layout.dialog_id_card_type)
//        recyclerView!!.layoutManager = LinearLayoutManager(context)
//        //1:身份证 2:海外护照 3:军官证 4:台胞证 5: 港澳居民来往内地通行证
//        val list = arrayListOf<ScreenItemEntity>().apply {
//            add(ScreenItemEntity("身份证",true,"1"))
//            add(ScreenItemEntity("海外护照",false,"2"))
//            add(ScreenItemEntity("军官证",false,"3"))
//            add(ScreenItemEntity("台胞证",false,"4"))
//            add(ScreenItemEntity("港澳居民来往内地通行证",false,"5"))
//        }
//        val adapter = DialogIDAdapter(list)
//        recyclerView!!.adapter = adapter
//        adapter.setOnItemClickListener { adapter, view, position ->
//
//            val itemEntity = adapter.getItem(position) as ScreenItemEntity
//            if (itemEntity.isChecked) {
//                return@setOnItemClickListener
//            }
//            list.forEach {
//                it.isChecked = false
//            }
//            itemEntity.isChecked = true
//            adapter.notifyDataSetChanged()
//
//        }
//
//        btn_left.setOnClickListener { dismiss() }
//        btn_right.setOnClickListener {
//            adapter.data.forEach {
//                if (it.isChecked) {
//                    listener.onSelect(it)
//                    return@forEach
//                }
//            }
//            dismiss()
//        }
//
//    }
//
//    interface OnIdSelectListener{
//        fun onSelect(data: ScreenItemEntity)
//
//    }
//
//
//}
