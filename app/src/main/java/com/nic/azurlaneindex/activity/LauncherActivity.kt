package com.nic.azurlaneindex.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.KeyEvent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.nic.azurlaneindex.R
import com.nic.azurlaneindex.tool.SettingSPUtil
import com.xuexiang.xui.utils.KeyboardUtils
import com.xuexiang.xui.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_launcher.*
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
class LauncherActivity : AppCompatActivity(),CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(SettingSPUtil.themeID)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        //字体颜色
        if (SettingSPUtil.isLauncherDarkFont){
            launcher_title.setTextColor(Color.BLACK)
        }
        else {
            launcher_title.setTextColor(Color.WHITE)
        }
        //虚拟按键颜色
        val themeBGColor = TypedValue()
        theme.resolveAttribute(R.attr.themeBGColor,themeBGColor,true)
        window.navigationBarColor = getColor(themeBGColor.resourceId)
        //初始化APP
        onPrepare()
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

    private fun onPrepare(){
        launch(Dispatchers.Main) {
            launch(Dispatchers.IO) {
                delay(1000)
            }.join()
            //启动主页面
            val intent= Intent()
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setClass(this@LauncherActivity,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event)
    }
}
