package com.nic.azurlaneindex.tool

import android.app.Activity
import android.content.SharedPreferences
import com.nic.azurlaneindex.R
import com.xuexiang.xui.XUI

object SettingSPUtil {

    private val sp: SharedPreferences by lazy {
        XUI.getContext().getSharedPreferences("settings",Activity.MODE_PRIVATE)
    }

    var isFirstRun: Boolean
        get() {
            return sp.getBoolean("isFirstRun",true)
        }
        set(value) {
            sp.edit().putBoolean("isFirstRun",value).apply()
        }

    /**
     * 上次更新时间
     */
    var lastUpdateTimestamp: Long
        get() {
            return sp.getLong("lastUpdateTimestamp",0L)
        }
        set(value) {
            sp.edit().putLong("lastUpdateTimestamp",value).apply()
        }

    /**
     * 主题
     */
    var theme: String
        get() {
            return sp.getString("theme","BLHX_LIGHT")?:"BLHX_LIGHT"
        }
        set(value) {
            sp.edit().putString("theme",value).apply()
        }

    /**
     * 启动标题字体颜色
     */
    var isLauncherDarkFont: Boolean
        get() {
            return sp.getBoolean("isLauncherDarkFont",false)
        }
        set(value) {
            sp.edit().putBoolean("isLauncherDarkFont",value).apply()
        }

    /**
     * 深色状态栏
     */
    var isDarkStatusBar: Boolean
        get() {
            return sp.getBoolean("isDarkStatusBar",false)
        }
        set(value) {
            sp.edit().putBoolean("isDarkStatusBar",value).apply()
        }

    val themeID: Int
        get() {
            return when(theme){
                "BLHX_LIGHT" -> R.style.XUITheme_Phone_Light_BLHX
                "BLHX_DARK" -> R.style.XUITheme_Phone_Dark_BLHX
                "CSZZ_LIGHT" -> R.style.XUITheme_Phone_Light_CSZZ
                "CSZZ_DARK" -> R.style.XUITheme_Phone_Dark_CSZZ
                else -> R.style.XUITheme_Phone_Light_BLHX
            }
        }

    val wikiURL: String
        get() {
            return sp.getString("wikiURL","https://wiki.biligame.com") ?: "https://wiki.biligame.com"
        }
}