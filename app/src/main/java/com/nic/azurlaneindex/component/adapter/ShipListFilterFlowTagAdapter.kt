package com.nic.azurlaneindex.component.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.nic.azurlaneindex.R
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter

class ShipListFilterFlowTagAdapter(context: Context): BaseTagAdapter<String,TextView>(context) {

    override fun getLayoutId(): Int {
        return R.layout.item_ship_list_filter_tag
    }

    override fun newViewHolder(convertView: View?): TextView {
        return convertView!!.findViewById(R.id.tag_item)
    }

    override fun convert(holder: TextView?, item: String?, position: Int) {
        holder!!.text = item
    }
}