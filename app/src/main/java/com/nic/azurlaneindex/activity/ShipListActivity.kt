package com.nic.azurlaneindex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.WindowManager
import android.widget.TextView
import com.google.gson.Gson
import com.nic.azurlaneindex.R
import com.nic.azurlaneindex.component.adapter.ShipListFilterFlowTagAdapter
import com.nic.azurlaneindex.tool.SettingSPUtil
import com.xuexiang.xui.XUI
import com.xuexiang.xui.utils.StatusBarUtils
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.activity_ship_list.*
import kotlinx.android.synthetic.main.activity_ship_list.toolbar

class ShipListActivity : AppCompatActivity() {

    private val filters = HashMap<String,ArrayList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(SettingSPUtil.themeID)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ship_list)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.filter){
                filter_table.toggle()
            }
            true
        }
        initFilter()
    }

    override fun onStart() {
        super.onStart()
        //透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //状态栏深色
        if (SettingSPUtil.isDarkStatusBar){
            StatusBarUtils.setStatusBarLightMode(this)
        }
        else {
            StatusBarUtils.setStatusBarDarkMode(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.wiki_ship_toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initFilter(){
        filter_tag_all.apply {
            adapter =
                ShipListFilterFlowTagAdapter(
                    this@ShipListActivity
                )
            addTags(resources.getStringArray(R.array.ship_list_filter_all))
            setOnTagClickListener { _, view, _ ->
                val filter = filters["all"] ?: ArrayList()
                val tag = view.findViewById<TextView>(R.id.tag_item)
                filter.add(tag.text.toString())
                filters["all"] = filter
                XToast.info(XUI.getContext(), Gson().toJson(filter)).show()
            }
        }
        filter_tag_type.apply {
            adapter =
                ShipListFilterFlowTagAdapter(
                    this@ShipListActivity
                )
            addTags(resources.getStringArray(R.array.ship_list_filter_type))
            setOnTagClickListener { _, view, _ ->
                val filter = filters["type"] ?: ArrayList()
                val tag = view.findViewById<TextView>(R.id.tag_item)
                filter.add(tag.text.toString())
                filters["type"] = filter
                XToast.info(XUI.getContext(), Gson().toJson(filter)).show()
            }
        }
        filter_tag_rate.apply {
            adapter =
                ShipListFilterFlowTagAdapter(
                    this@ShipListActivity
                )
            addTags(resources.getStringArray(R.array.ship_list_filter_rate))
            setOnTagClickListener { _, view, _ ->
                val filter = filters["rate"] ?: ArrayList()
                val tag = view.findViewById<TextView>(R.id.tag_item)
                filter.add(tag.text.toString())
                filters["rate"] = filter
                XToast.info(XUI.getContext(), Gson().toJson(filter)).show()
            }
        }
        filter_tag_camp.apply {
            adapter =
                ShipListFilterFlowTagAdapter(
                    this@ShipListActivity
                )
            addTags(resources.getStringArray(R.array.ship_list_filter_camp))
            setOnTagClickListener { _, view, _ ->
                val filter = filters["camp"] ?: ArrayList()
                val tag = view.findViewById<TextView>(R.id.tag_item)
                filter.add(tag.text.toString())
                filters["camp"] = filter
                XToast.info(XUI.getContext(), Gson().toJson(filter)).show()
            }
        }
        filter_tag_armor.apply {
            adapter =
                ShipListFilterFlowTagAdapter(
                    this@ShipListActivity
                )
            addTags(resources.getStringArray(R.array.ship_list_filter_armor))
            setOnTagClickListener { _, view, _ ->
                val filter = filters["armor"] ?: ArrayList()
                val tag = view.findViewById<TextView>(R.id.tag_item)
                filter.add(tag.text.toString())
                filters["armor"] = filter
                XToast.info(XUI.getContext(), Gson().toJson(filter)).show()
            }
        }
        filter_tag_max_cost.apply {
            adapter =
                ShipListFilterFlowTagAdapter(
                    this@ShipListActivity
                )
            addTags(resources.getStringArray(R.array.ship_list_filter_max_cost))
            setOnTagClickListener { _, view, _ ->
                val filter = filters["maxCost"] ?: ArrayList()
                val tag = view.findViewById<TextView>(R.id.tag_item)
                filter.add(tag.text.toString())
                filters["maxCost"] = filter
                XToast.info(XUI.getContext(), Gson().toJson(filter)).show()
            }
        }
        filter_tag_main_category.apply {
            adapter =
                ShipListFilterFlowTagAdapter(
                    this@ShipListActivity
                )
            addTags(resources.getStringArray(R.array.ship_list_filter_main_category))
            setOnTagClickListener { _, view, _ ->
                val filter = filters["mainCategory"] ?: ArrayList()
                val tag = view.findViewById<TextView>(R.id.tag_item)
                filter.add(tag.text.toString())
                filters["mainCategory"] = filter
                XToast.info(XUI.getContext(), Gson().toJson(filter)).show()
            }
        }
        filter_tag_sub_category.apply {
            adapter =
                ShipListFilterFlowTagAdapter(
                    this@ShipListActivity
                )
            addTags(resources.getStringArray(R.array.ship_list_filter_sub_category))
            setOnTagClickListener { _, view, _ ->
                val filter = filters["subCategory"] ?: ArrayList()
                val tag = view.findViewById<TextView>(R.id.tag_item)
                filter.add(tag.text.toString())
                filters["subCategory"] = filter
                XToast.info(XUI.getContext(), Gson().toJson(filter)).show()
            }
            setOnTagSelectListener { parent, _, selectedList ->
                val filter = filters["subCategory"] ?: ArrayList()
                filter.clear()
                for (i in selectedList){
                    filter.add(parent.adapter.getItem(i) as String)
                }
                filters["subCategory"] = filter
                XToast.info(XUI.getContext(), Gson().toJson(filter)).show()
            }
        }
    }
}
