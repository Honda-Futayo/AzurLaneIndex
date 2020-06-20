package com.nic.azurlaneindex.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.nic.azurlaneindex.R
import com.nic.azurlaneindex.activity.MainActivity
import com.nic.azurlaneindex.tool.SettingSPUtil
import com.nic.azurlaneindex.tool.UpdateUtil
import com.xuexiang.xui.utils.StatusBarUtils
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xupdate.utils.UpdateUtils

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //sp信息
        preferenceManager.sharedPreferencesName = "settings"
        preferenceManager.sharedPreferencesMode = Activity.MODE_PRIVATE
        //绑定UI
        setPreferencesFromResource(R.xml.setting_preferences, rootKey)
        findPreference<Preference>("currentVersion")?.title =
            "当前版本：${UpdateUtils.getVersionName(XUpdate.getContext())}"
        //事件注册
        register()
    }

    private fun register(){
        //选择主题
        findPreference<ListPreference>("theme")?.setOnPreferenceChangeListener { _, newValue ->
            //保存主题
            SettingSPUtil.theme = newValue as String
            //重启主页面
            startActivity(Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            return@setOnPreferenceChangeListener false
        }
        //切换状态栏颜色
        findPreference<SwitchPreference>("isDarkStatusBar")?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue is Boolean && newValue) {
                StatusBarUtils.setStatusBarLightMode(activity)
            } else {
                StatusBarUtils.setStatusBarDarkMode(activity)
            }
            return@setOnPreferenceChangeListener true
        }
        //检查更新
        findPreference<Preference>("checkUpdate")?.setOnPreferenceClickListener {
            UpdateUtil.checkUpdate(context,false)
            return@setOnPreferenceClickListener true
        }
    }

}