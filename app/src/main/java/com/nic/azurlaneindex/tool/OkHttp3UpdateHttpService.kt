package com.nic.azurlaneindex.tool

import com.xuexiang.xupdate.proxy.IUpdateHttpService
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import com.xuexiang.xupdate.utils.UpdateUtils
import com.zhy.http.okhttp.request.RequestCall
import com.zhy.http.okhttp.callback.FileCallBack
import okhttp3.Request
import java.io.File


object OkHttp3UpdateHttpService: IUpdateHttpService {

    var isPostJson: Boolean = false

    /**
     * 文件下载
     *
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 文件下载回调
     */
    override fun download(
        url: String,
        path: String,
        fileName: String,
        callback: IUpdateHttpService.DownloadCallback
    ) {
        OkHttpUtils.get()
            .url(url)
            .tag(url)
            .build()
            .execute(object : FileCallBack(path, fileName) {
                override fun inProgress(progress: Float, total: Long, id: Int) {
                    callback.onProgress(progress, total)
                }

                override fun onError(call: Call, e: Exception, id: Int) {
                    callback.onError(e)
                }

                override fun onResponse(response: File, id: Int) {
                    callback.onSuccess(response)
                }

                override fun onBefore(request: Request, id: Int) {
                    super.onBefore(request, id)
                    callback.onStart()
                }
            })
    }

    /**
     * 异步get
     *
     * @param url      get请求地址
     * @param params   get参数
     * @param callBack 回调
     */
    override fun asyncGet(
        url: String,
        params: MutableMap<String, Any>,
        callBack: IUpdateHttpService.Callback
    ) {
        OkHttpUtils.get()
            .url(url)
            .params(params.mapValues {it.value.toString()})
            .build()
            .execute(object : StringCallback() {
                override fun onError(call: Call, e: Exception, id: Int) {
                    callBack.onError(e)
                }

                override fun onResponse(response: String, id: Int) {
                    callBack.onSuccess(response)
                }
            })
    }

    /**
     * 取消文件下载
     *
     * @param url      下载地址
     */
    override fun cancelDownload(url: String) {
        OkHttpUtils.getInstance().cancelTag(url)
    }

    /**
     * 异步post
     *
     * @param url      post请求地址
     * @param params   post请求参数
     * @param callBack 回调
     */
    override fun asyncPost(
        url: String,
        params: MutableMap<String, Any>,
        callBack: IUpdateHttpService.Callback
    ) {
        //这里默认post的是Form格式，使用json格式的请修改 post -> postString
        val requestCall: RequestCall = if (isPostJson) {
            OkHttpUtils.postString()
                .url(url)
                .content(UpdateUtils.toJson(params))
                .build()
        } else {
            OkHttpUtils.post()
                .url(url)
                .params(params.mapValues { it.value.toString() })
                .build()
        }
        requestCall.execute(object : StringCallback() {
            override fun onError(call: Call, e: Exception, id: Int) {
                callBack.onError(e)
            }

            override fun onResponse(response: String, id: Int) {
                callBack.onSuccess(response)
            }
        })
    }
}