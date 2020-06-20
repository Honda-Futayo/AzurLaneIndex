package com.nic.azurlaneindex.activity

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.webkit.*
import android.webkit.WebView.WebViewTransport
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.nic.azurlaneindex.R
import com.nic.azurlaneindex.tool.SettingSPUtil
import com.xuexiang.xui.utils.StatusBarUtils
import com.xuexiang.xui.widget.progress.materialprogressbar.MaterialProgressBar
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.activity_settings.toolbar
import kotlinx.android.synthetic.main.activity_web_view.*
import java.net.URLDecoder


class WebViewActivity : AppCompatActivity() {

    private lateinit var url: String
    private lateinit var webView: WebView

    private val webChromeClient = object : WebChromeClient(){
        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            val transport = resultMsg!!.obj as WebViewTransport
            transport.webView = view
            resultMsg.sendToTarget()
            return true
        }
    }

    private val webViewClient = object : WebViewClient(){

        var isError = false

        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            val jumpPrefix = "https://game.bilibili.com/linkfilter/?url="
            if (url == null){
                return true
            }
            if (url.startsWith(jumpPrefix) || !url.startsWith("http")){
                startActivity(
                    Intent().apply {
                        action = "android.intent.action.VIEW"
                        data = Uri.parse(url.replace(jumpPrefix,""))
                    }
                )
            }
            else {
                state_layout.showLoading("正在加载")
                view.loadUrl(url)
            }
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            //重置异常状态
            isError = false
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            if (request?.isForMainFrame == true){
                isError = true
                state_layout.showError("加载失败"){
                    state_layout.showLoading("正在加载")
                    webView.loadUrl(webView.url)
                }
            }
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            //取消刷新状态
            if (!isError){
                state_layout.showContent()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(SettingSPUtil.themeID)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        toolbar.setNavigationOnClickListener { finish() }
        //获取信息
        url = intent.getStringExtra("url") ?: ""
        if (url.isBlank()) {
            finish()
        }
        var title = intent.getStringExtra("title")
        if (title == null){
            title = url.substringAfterLast("/")
            title = URLDecoder.decode(title,"UTF-8")
        }
        //toolbar初始化
        toolbar.title = title
        setSupportActionBar(toolbar)
        //创建webView
        initFreshLayout()
        initStatefulLayout()
        initWebView()
        //加载网页
        state_layout.showLoading("正在加载")
        webView.loadUrl(url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_activity_toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refreshPage -> {
                state_layout.showLoading("正在加载")
                webView.loadUrl(url)
            }
            R.id.copyLink -> {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val data = ClipData.newPlainText("碧蓝图鉴",url)
                clipboard.setPrimaryClip(data)
                XToast.success(this,"复制成功").show()
            }
            R.id.shareLink -> {
                val data = Intent(Intent.ACTION_SEND)
                data.type = "text/plain"
                data.putExtra(Intent.EXTRA_TEXT, "${url}\n——分享自碧蓝图鉴APP")
                startActivity(Intent.createChooser(data, "分享"))
            }
            R.id.openByBrowser -> {
                val data = Intent(Intent.ACTION_VIEW)
                data.data = Uri.parse(url)
                startActivity(data)
            }
        }
        return true
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

    private fun initFreshLayout(){
        refresh_layout.setOnRefreshListener{
            refresh_layout.isRefreshing = false
            state_layout.showLoading("正在加载")
            webView.loadUrl(webView.url)
        }
    }

    private fun initStatefulLayout(){
        try {
            theme?.let { theme ->
                val themeBGColor = TypedValue()
                theme.resolveAttribute(R.attr.themeBGColor, themeBGColor, true)
                val clazz = state_layout.javaClass
                //进度条颜色
                val progressField = clazz.getDeclaredField("stProgress")
                progressField.isAccessible = true
                val progress = progressField.get(state_layout) as? MaterialProgressBar
                progress?.supportIndeterminateTintList = ColorStateList.valueOf(themeBGColor.data)
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
        } catch (th: Throwable){
            Log.e(this.javaClass.name,"statefulLayout初始化失败",th)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(){
        webView = WebView(applicationContext).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        web_view_container.addView(webView)
        webView.settings.apply {
            //Http和Https混用
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            //窗口缩放
            loadWithOverviewMode = true
            //图片缩放
            useWideViewPort = true
            //允许调用JavaScript
            javaScriptEnabled = true
            //允许DOM存储
            domStorageEnabled = true
            //JavaScript打开窗口
            javaScriptCanOpenWindowsAutomatically = true
        }
        webView.webChromeClient = this.webChromeClient
        webView.webViewClient = this.webViewClient
        webView.setOnScrollChangeListener { _, _, y, _, _ ->
            refresh_layout.isEnabled = y == 0
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.run {
            clearHistory()
            web_view_container.removeView(this)
            loadUrl("about:blank")
            stopLoading()
            webChromeClient = null
            webViewClient = null
            destroy()
        }
    }
}
