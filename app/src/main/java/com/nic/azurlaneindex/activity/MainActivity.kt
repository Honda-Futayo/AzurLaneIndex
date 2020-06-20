package com.nic.azurlaneindex.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.nic.azurlaneindex.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.nic.azurlaneindex.fragment.WikiFragment
import com.nic.azurlaneindex.fragment.NoticeFragment
import com.nic.azurlaneindex.tool.SettingSPUtil
import com.nic.azurlaneindex.tool.UpdateUtil
import com.xuexiang.xui.utils.StatusBarUtils


class MainActivity : AppCompatActivity() {

    private val noticeContainer = NoticeFragment()
    private val wikiContainer = WikiFragment()

    private var currentContainer: Fragment = noticeContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(SettingSPUtil.themeID)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initFragment()
        register()
        UpdateUtil.checkUpdate(this,true)
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

    /**
     * 注册事件
     */
    private fun register(){
        //toolbar
        toolbar.setNavigationOnClickListener { drawer.openDrawer(side_nav_view) }
        //侧边导航栏
        side_nav_view.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.github -> {
                    val uri = Uri.parse("https://github.com/Nic41981")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            }
            return@setNavigationItemSelectedListener true
        }
        setting_btn.setOnClickListener{
            startActivity(
                Intent(
                    this@MainActivity,
                    SettingsActivity::class.java
                )
            )
        }
        exit_btn.setOnClickListener { exitProcess(0) }
        //底部导航栏
        bottom_nav_view.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.bottom_nav_notice -> {
                    switchFragment(noticeContainer)
                }
                R.id.bottom_nav_wiki -> {
                    switchFragment(wikiContainer)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun initFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.container, noticeContainer)
            .add(R.id.container, wikiContainer)
            .hide(wikiContainer)
            .commit()
    }

    private fun switchFragment(targetContainer: Fragment){
        if (currentContainer != targetContainer){
            supportFragmentManager.beginTransaction()
                .hide(currentContainer)
                .show(targetContainer)
                .commit()
            currentContainer = targetContainer
        }
    }
}
