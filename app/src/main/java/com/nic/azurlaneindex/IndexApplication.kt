package com.nic.azurlaneindex

import android.app.Application
import com.nic.azurlaneindex.tool.OkHttp3UpdateHttpService
import com.xuexiang.xui.XUI
import com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION
import com.xuexiang.xupdate.utils.UpdateUtils
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xui.widget.toast.XToast


class IndexApplication: Application() {

    override fun onCreate() {
        initUI()
        initUpdate()
        super.onCreate()
    }

    private fun initUI(){
        XUI.init(this)
        XUI.debug(isDebug())
    }

    private fun initUpdate() {
        XUpdate.get()
            .debug(true)
            .isWifiOnly(false)
            .isGet(true)
            .isAutoMode(false)
            .param("versionCode", UpdateUtils.getVersionCode(this))
            .param("appKey", packageName)
            .setOnUpdateFailureListener { error ->
                if (error.code != CHECK_NO_NEW_VERSION) {
                    XToast.error(this,"更新失败").show()
                }
            }
            .supportSilentInstall(true)
            .setIUpdateHttpService(OkHttp3UpdateHttpService)
            .init(this)
    }

    private fun isDebug() = true
}