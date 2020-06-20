package com.nic.azurlaneindex.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nic.azurlaneindex.R
import com.nic.azurlaneindex.activity.WebViewActivity
import com.nic.azurlaneindex.component.adapter.NoticeListAdapter
import com.nic.azurlaneindex.core.NoticeParser
import com.nic.azurlaneindex.tool.SettingSPUtil
import com.xuexiang.xui.XUI
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.fragment_notice.*
import kotlinx.coroutines.*

class NoticeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,CoroutineScope by MainScope() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.cloneInContext(ContextThemeWrapper(activity,SettingSPUtil.themeID))
            .inflate(R.layout.fragment_notice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFreshLayout()
        initStatefulLayout()
        initNoticeList()
        onRefresh()
    }

    private fun initFreshLayout(){
        refresh_layout.setOnRefreshListener(this)
    }

    private fun initStatefulLayout(){
        try {
            activity?.let { activity ->
                activity.theme?.let { theme ->
                    val themeBGColor = TypedValue()
                    theme.resolveAttribute(R.attr.themeBGColor,themeBGColor,true)
                    val clazz = state_layout.javaClass
                    //图片颜色
                    val imageField = clazz.getDeclaredField("stImage")
                    imageField.isAccessible = true
                    val image = imageField.get(state_layout) as? ImageView
                    image?.setColorFilter(themeBGColor.data)
                    //按钮颜色
                    val buttonField = clazz.getDeclaredField("stButton")
                    buttonField.isAccessible = true
                    val button = buttonField.get(state_layout) as? Button
                    button?.setBackgroundColor(themeBGColor.data)
                }
            }
        } catch (th: Throwable){
            Log.e(this::class.java.name,"statefulLayout初始化失败",th)
        }
    }


    private fun initNoticeList(){
        notice_list.layoutManager = LinearLayoutManager(this.context)
        NoticeListAdapter.clickListener = object : NoticeListAdapter.ClickListener{
            override fun onClick(position: Int) {
                val title = NoticeListAdapter.noticeList[position].title
                val url = NoticeListAdapter.noticeList[position].url
                startActivity(
                    Intent(
                        this@NoticeFragment.context,
                        WebViewActivity::class.java
                    ).apply {
                        putExtra("title",title)
                        putExtra("url", url)
                    }
                )
            }
        }
        notice_list.adapter =
            NoticeListAdapter
    }

    override fun onRefresh() {
        launch(Dispatchers.Main) {
            //设置刷新状态
            if (!refresh_layout.isRefreshing){
                refresh_layout.isRefreshing = true
            }
            //获取数据
            val notices = withContext(Dispatchers.IO) {
                NoticeParser.getNotices()
            }
            //设置数据
            if (notices.isNullOrEmpty()){
                if (notices == null){
                    XToast.error(XUI.getContext(),"获取失败").show()
                }
                state_layout.showEmpty("暂无公告")
            }
            else {
                NoticeListAdapter.run {
                    noticeList.clear()
                    noticeList.addAll(notices)
                    notifyDataSetChanged()
                }
                state_layout.showContent()
            }
            if (refresh_layout.isRefreshing){
                refresh_layout.isRefreshing = false
            }
        }
    }
}