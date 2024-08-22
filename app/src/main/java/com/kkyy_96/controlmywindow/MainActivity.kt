package com.kkyy_96.controlmywindow

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.kkyy_96.controlmywindow.src.config.MyControlConfig
import com.kkyy_96.controlmywindow.src.log.MyLog
import com.kkyy_96.controlmywindow.src.page.NavGraph
import com.kkyy_96.controlmywindow.src.page.permission.requestPermission

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyControlConfig.readConfig(this)
        MyLog.activity = this

        requestPermission(this)
        setContent {
            NavGraph(activity =this)
        }
    }
}
