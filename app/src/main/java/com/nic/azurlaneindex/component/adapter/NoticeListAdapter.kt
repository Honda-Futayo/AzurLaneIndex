package com.nic.azurlaneindex.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nic.azurlaneindex.bean.NoticeListItem
import com.nic.azurlaneindex.R
import kotlinx.android.synthetic.main.item_notice.view.*

object NoticeListAdapter: RecyclerView.Adapter<NoticeListAdapter.ViewHolder>() {

    val noticeList: ArrayList<NoticeListItem> = arrayListOf()
    var clickListener: ClickListener? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        val title: TextView = itemView.title
    }

    interface ClickListener{
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notice,parent,false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = noticeList[position]
        holder.title.text = item.title
        holder.view.setOnClickListener {
            clickListener?.onClick(position)
        }
    }

    fun onClick(listener: ClickListener){
        clickListener = listener
    }
}