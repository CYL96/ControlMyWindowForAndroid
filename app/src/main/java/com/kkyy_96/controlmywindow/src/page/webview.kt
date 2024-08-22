package com.kkyy_96.controlmywindow.src.page

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.kkyy_96.controlmywindow.MainActivity


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContainer( navController: NavHostController, url : String) {
    // var url = "http://10.5.10.87:5174"
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                // webViewClient = WebViewClient()
                // webChromeClient = WebChromeClient()

                WebView.setWebContentsDebuggingEnabled(true);
                // webChromeClient=WebChromeClient()


                // settings.loadWithOverviewMode = true
                // settings.useWideViewPort = true
                // settings.setSupportZoom(false)
            }
        },
        update = { webView ->
            webView.clearHistory()
            webView.clearMatches()
            webView.clearFormData()
            webView.clearCache(true)


            webView.loadUrl(url)
        },
        modifier = Modifier
            .fillMaxSize()
    )
}

