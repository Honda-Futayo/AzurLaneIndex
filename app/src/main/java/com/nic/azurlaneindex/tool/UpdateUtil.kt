package com.nic.azurlaneindex.tool

import android.content.Context
import com.xuexiang.xupdate.XUpdate
import java.util.concurrent.TimeUnit

object UpdateUtil {

    private const val updateUrl = "http://0.0.0.0"

    fun checkUpdate(context: Context?,isAuto: Boolean){
        val mContext = context ?: XUpdate.getContext()
        val currentTimestamp = System.currentTimeMillis()
        if (isAuto){
            val lastTimestamp = SettingSPUtil.lastUpdateTimestamp
            if (lastTimestamp - currentTimestamp < TimeUnit.DAYS.toMillis(1)){
                return
            }
        }
        XUpdate.newBuild(mContext, updateUrl).update()
        SettingSPUtil.lastUpdateTimestamp = currentTimestamp
    }
}