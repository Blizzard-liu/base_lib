package com.winsky.winto.baseapp

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.util.*

/**
 * <br></br>================================================
 * <br></br> @author：Blizzard-liu
 * <br></br> @date：2018/5/21 9:36
 * <br></br> @describe：
 * <br></br>================================================
 */
class EditBaseUrlAdapter(data: ArrayList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.dialog_base_url_list_item, data) {

    override fun convert(helper: BaseViewHolder, item: String) {

              helper.setText(R.id.tv_url, item)


    }


}
