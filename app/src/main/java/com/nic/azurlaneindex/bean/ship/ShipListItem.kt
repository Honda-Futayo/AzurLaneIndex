package com.nic.azurlaneindex.bean.ship

data class ShipListItem (
    var id: String = "",
    var levelName: String = "",
    var shipName: String = "",
    var url: String = "",
    var type: ArrayList<String> = arrayListOf(),
    var rate: String = "",
    var camp: String = "",
    var armor: String = "",
    var maxCost: Int = 0,
    var mainCategory: ArrayList<String> = arrayListOf(),
    var subCategory: ArrayList<String> = arrayListOf(),
    var isReform: Boolean = false,
    var value: ShipValue = ShipValue()
)