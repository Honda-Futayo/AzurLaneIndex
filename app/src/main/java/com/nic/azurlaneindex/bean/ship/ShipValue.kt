package com.nic.azurlaneindex.bean.ship

data class ShipValue (
    //耐久
    var durable: Int = 0,
    //装填
    var reload: Int = 0,
    //炮击
    var shelling: Int = 0,
    //雷击
    var torpedo: Int = 0,
    //机动
    var maneuver: Int = 0,
    //防空
    var antiAircraft: Int = 0,
    //航空
    var aviation: Int = 0,
    //消耗
    var cost: Int = 0,
    //反潜
    var antiSubmarine: Int = 0,
    //幸运
    var lucky: Int = 0,
    //航速
    var speed: Int = 0,
    //氧气
    var oxygen: Int = 0
)