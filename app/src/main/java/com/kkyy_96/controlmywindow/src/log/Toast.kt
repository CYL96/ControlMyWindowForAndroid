package com.kkyy_96.controlmywindow.src.log

import android.content.Context
import android.widget.Toast

object MyToast {
    var toast: Toast? = null
    fun show(ctx: Context, text: String?) {
        if( toast == null){
            toast = Toast.makeText(ctx, "", Toast.LENGTH_SHORT) //正常执行
        }else{
            toast?.cancel()
            toast = Toast.makeText(ctx, "", Toast.LENGTH_SHORT) //正常执行
        }
        toast?.setText(text)
        toast?.show()
    }
}
