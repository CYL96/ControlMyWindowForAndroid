package com.kkyy_96.controlmywindow.src.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.gson.Gson
import com.kkyy_96.controlmywindow.src.config.MyControlConfig
import com.kkyy_96.controlmywindow.src.log.MyLog

class ServerItem(var name: String = "", var ip: String = "", var port: String = "55001") {
    var id: Long = 0

}

object ServerInfo {
    fun getServerList(): MutableList<ServerItem> {
        var serverList = mutableListOf<ServerItem>()
        if (MyControlConfig.serverList == "") {
            return serverList
        }
        try {
            MyLog.info("解析配置")
            Gson().fromJson(MyControlConfig.serverList, Array<ServerItem>::class.java).forEach {
                serverList.add(it)
            }
        } catch (e: Exception) {
            MyLog.err("解析配置失败 err:$e")
        }
        return serverList
    }

    fun saveServerList(list: MutableList<ServerItem>) {
        MyControlConfig.updateServerList(Gson().toJson(list))
        MyControlConfig.saveConfig()
    }

}


@Composable
fun ServerItemUi(item: ServerItem, onClick: () -> Unit = {}, modifier: Modifier=Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { onClick() }

    ) {
        Text(text = item.name)
        Text(text = "${item.ip}:${item.port}")
    }
}