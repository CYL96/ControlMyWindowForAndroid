package com.kkyy_96.controlmywindow.src.config

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.kkyy_96.controlmywindow.MainActivity
import com.kkyy_96.controlmywindow.src.log.MyLog
import org.json.JSONArray

object MyControlConfig {
    var serverList = ""

    private lateinit var sharedPref: SharedPreferences

    fun readConfig(activity: MainActivity) {
        MyLog.info("读取配置")
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val tmp = sharedPref.getString("serverList", "")
        serverList = tmp ?: ""
    }

    fun updateServerList(serverList: String) {
        this.serverList = serverList
    }

    fun saveConfig() {
        MyLog.info("保存配置")
        with(sharedPref.edit()) {
            MyLog.info("serverList:$serverList")
            putString("serverList", serverList)
            apply()
        }
    }
}

