package com.example.mycookbook.utilities

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun verificaPermissao(context: Context, permissao: String) =
    ContextCompat.checkSelfPermission(context, permissao) == PackageManager.PERMISSION_GRANTED

