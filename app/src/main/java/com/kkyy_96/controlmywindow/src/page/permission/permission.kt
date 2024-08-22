package com.kkyy_96.controlmywindow.src.page.permission

import android.Manifest
import android.util.Log
import com.kkyy_96.controlmywindow.MainActivity
import com.kkyy_96.controlmywindow.src.log.MyLog
import com.kkyy_96.controlmywindow.src.log.MyToast
import com.permissionx.guolindev.PermissionX

fun requestPermission(activity: MainActivity) {
    // 请求权限
    PermissionX.init(activity)
        .permissions(
            Manifest.permission.INTERNET,
        ).request { allGranted, grantedList, deniedList ->
            if (allGranted) {
                MyLog.info("所有申请的权限都已通过")
            } else {
                MyLog.info("您拒绝了如下权限：$deniedList")
            }
        }
}