package com.kkyy_96.controlmywindow.src.log


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kkyy_96.controlmywindow.MainActivity

object MyLog {
    var activity: MainActivity? = null
    private var tag: String = "MyApp"
    fun info(mes: String) {
        Log.i(tag, mes)
    }
    fun info(tag:String ,mes: String) {
        Log.i(tag, mes)
    }

    fun err(mes: String) {
        Log.e(tag, mes)
    }
    fun err(tag:String ,mes: String) {
        Log.e(tag, mes)
    }

    fun show(mes: String) {
        if (activity != null) {
            MyToast.show(activity!!, mes)
        }
    }


    fun infoShow(tag:String ,mes: String) {
        Log.i(tag, mes)
        show(mes)
    }

    fun infoShow(mes: String) {
        Log.i(tag, mes)
        show(mes)
    }
    fun errShow(tag:String ,mes: String) {
        Log.e(tag, mes)
        show(mes)
    }


    fun errShow(mes: String) {
        Log.e(tag, mes)
        show(mes)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun diaLog(
        title: String,
        confirm: String = "确认",
        cancel: String = "取消",
        onDismiss: () -> Unit,
        onConfirm: () -> Unit,
        onCancel: () -> Unit,
    ) {

        AlertDialog(
            onDismissRequest = onDismiss,
            modifier = Modifier
                .background(Color.Transparent)
                .clip(shape = RoundedCornerShape(16.dp)),
            confirmButton= {
                Button(
                    onClick = {
                        onConfirm()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(confirm)
                }
            },
            dismissButton={
                Button(
                    onClick = {
                        onCancel()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(cancel)
                }

            },
            text = {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(10.dp)
                )

            }

        )
    }
}