package com.kkyy_96.controlmywindow.src.page.common

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Button1(
    onClick: () -> Unit,
    modifier: Modifier=Modifier,
    content:@Composable  () -> Unit) {
    Button(

        onClick = onClick,
        modifier = modifier
    ) {
        content
    }
}