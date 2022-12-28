package com.example.mycookbook.di

import com.example.mycookbook.ui.recyclerview.adapter.ListaDeReceitasAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val listaReceitasModule = module {

    factory {
        ListaDeReceitasAdapter(androidContext())
    }
}