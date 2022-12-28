package com.example.mycookbook.di

import com.example.mycookbook.ui.recyclerview.adapter.ListaDeIngredientesDetalhesAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val detalhesReceitaModule = module {

    factory {
        ListaDeIngredientesDetalhesAdapter(androidContext())
    }
}