package com.nic.azurlaneindex.core

import android.util.Log
import com.nic.azurlaneindex.bean.NoticeListItem
import com.nic.azurlaneindex.tool.SettingSPUtil
import org.jsoup.Jsoup

object NoticeParser {

    fun getNotices(): ArrayList<NoticeListItem>? {
        kotlin.runCatching {
            val baseURL = SettingSPUtil.wikiURL
            val url = "${baseURL}/blhx/新闻公告"
            val doc = Jsoup.connect(url).get()
            val tables = doc.body().getElementsByTag("table")
            if (tables.isEmpty()){
                error("未找到table")
            }
            val table = tables[0]
            val trs = table.getElementsByTag("tr")
            if (trs.size < 2){
                error("未找到tr")
            }
            val tr = trs[1]
            val tds = tr.getElementsByTag("td")
            if (tds.isEmpty()){
                error("未找到td")
            }
            val td = tds[0]
            val divs = td.getElementsByTag("div")
            val result = ArrayList<NoticeListItem>(divs.size)
            for (div in divs){
                val a = div.getElementsByTag("a")
                result.add(
                    NoticeListItem(
                        title = a.attr("title"),
                        url = baseURL + a.attr("href")
                    )
                )
            }
            return result
        }.onFailure {
            Log.e(this::class.java.name,"获取公告目录异常:${it.message}")
        }
        return null
    }

}