package com.kkyy_96.controlmywindow.src.page

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kkyy_96.controlmywindow.MainActivity
import com.kkyy_96.controlmywindow.src.log.MyLog
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Router {
    const val Home = "home"
    const val WebView = "webview"
}

@Composable
fun NavGraph(startDestination: String = Router.Home,activity: MainActivity) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Router.Home) {
            Home(navController)
        }
        composable(Router.WebView+"?url={url}") {
            var url = it.arguments?.getString("url")?:""
            url = URLDecoder.decode(
                url,
                StandardCharsets.UTF_8.toString()
            )
            MyLog.info(url)
            // url="http://10.5.10.87:5174/#/Home"
            WebViewContainer(navController,url)
        }
    }
}