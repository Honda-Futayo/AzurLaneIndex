package com.nic.azurlaneindex.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.nic.azurlaneindex.R
import com.nic.azurlaneindex.fragment.SettingsFragment
import com.nic.azurlaneindex.tool.SettingSPUtil
import com.xuexiang.xui.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(SettingSPUtil.themeID)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
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
}