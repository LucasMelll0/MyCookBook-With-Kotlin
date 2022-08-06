package com.example.mycookbook.ui.activity.extensions

import android.content.Context
import android.content.Intent

fun Context.vaiPara(
    clazz: Class<*>,
    intent: Intent.() -> Unit = {}
) {
    Intent(
        this,
        clazz
    )
        .apply {
            intent()
            startActivity(this)
        }
}