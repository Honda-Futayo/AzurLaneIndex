package com.nic.azurlaneindex.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nic.azurlaneindex.R
import com.nic.azurlaneindex.bean.ship.ShipListItem
import com.xuexiang.xui.widget.layout.ExpandableLayout
import kotlinx.android.synthetic.main.item_ship_list.view.*

object ShipListAdapter: RecyclerView.Adapter<ShipListAdapter.ViewHolder>() {

    val shipList: ArrayList<ShipListItem> = arrayListOf()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val id: TextView = itemView.ship_id
        val level: TextView = itemView.level_name
        val name: TextView = itemView.ship_name
        val valueTableBtn: ImageView = itemView.value_table_btn
        val valueTable: ExpandableLayout = itemView.value_table

        fun bind(data: ShipListItem){
            id.text = data.id
            level.text = data.levelName
            name.text = data.shipName
            valueTableBtn.setOnClickListener {
                valueTable.toggle()
            }
            valueTable.setOnExpansionChangedListener { expansion, state ->
                if (state == ExpandableLayout.State.EXPANDING){

                }
                valueTableBtn.rotation = expansion * 90
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notice,parent,false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}