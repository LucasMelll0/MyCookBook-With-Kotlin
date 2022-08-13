package com.example.mycookbook.ui.activity.extensions

import android.content.Context
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import com.google.android.material.R

fun AppCompatSpinner.adapterPadrao(context: Context, lista: Array<String>){
    adapter = ArrayAdapter<String>(
        context,
        R.layout.support_simple_spinner_dropdown_item,
        lista)
}