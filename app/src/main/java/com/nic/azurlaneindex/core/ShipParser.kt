package com.nic.azurlaneindex.core

import com.nic.azurlaneindex.bean.ship.ShipListItem
import com.nic.azurlaneindex.tool.SettingSPUtil
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object ShipParser {

    fun parserList(){
        kotlin.runCatching {
            val baseURL = SettingSPUtil.wikiURL
            val listURL = "${baseURL}/blhx/舰娘定位筛选"
            val doc = Jsoup.connect(listURL).timeout(50000).get()
            val shipList = doc.getElementsByClass("divsort")
            if (shipList.isNullOrEmpty()){
                error("未找到列表！")
            }
            for (tr in shipList){
                val item = parserListItem(tr)
            }
        }
    }

    fun parserListItem(tr: Element): ShipListItem?{
        val ship = ShipListItem()
        val attrs = tr.attributes()
        val tds = tr.getElementsByTag("td")
        kotlin.runCatching {
            //基本属性
            ship.apply {
                //获取ID
                ship.id = tds[0].ownText()
                //获取船名及URL
                val nameRoot = tds[1].getElementsByClass("itemhover")[0]
                val nameLink = nameRoot.getElementsByTag("a")[0]
                levelName = nameRoot.ownText()
                shipName = nameLink.ownText()
                url = nameLink.attr("href")
                //获取类型
                type.addAll(attrs["data-param1"].split(','))
                //稀有度
                rate = attrs["data-param2"]
                //阵营
                camp = attrs["data-param3"]
                //装甲类型
                armor = attrs["data-param4"]
                //最大消耗
                maxCost = attrs["data-param5"].toIntOrNull() ?: 0
                //主分类
                mainCategory.addAll(attrs["data-param6"].split(","))
                //次分类
                subCategory.addAll(attrs["data-param7"].split(","))
                //改造信息
                isReform = attrs["data-param8"].isNotBlank()
            }
            //数值数据
            ship.value.apply {
                //耐久
                durable = tds[6].ownText().toIntOrNull() ?: 0
                //装填
                reload = tds[7].ownText().toIntOrNull() ?: 0
                //炮击
                shelling = tds[8].ownText().toIntOrNull() ?: 0
                //雷击
                torpedo = tds[9].ownText().toIntOrNull() ?: 0
                //机动
                maneuver = tds[10].ownText().toIntOrNull() ?: 0
                //防空
                antiAircraft = tds[11].ownText().toIntOrNull() ?: 0
                //航空
                aviation = tds[12].ownText().toIntOrNull() ?: 0
                //消耗
                cost = tds[13].ownText().toIntOrNull() ?: 0
                //反潜
                antiSubmarine = tds[14].ownText().toIntOrNull() ?: 0
                //幸运
                lucky = tds[15].ownText().toIntOrNull() ?: 0
                //航速
                speed = tds[16].ownText().toIntOrNull() ?: 0
                //氧气
                oxygen = tds[17].ownText().toIntOrNull() ?: 0
            }
        }.onFailure {
            return null
        }
        return ship
    }
}