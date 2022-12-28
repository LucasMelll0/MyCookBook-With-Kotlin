package com.example.mycookbook.di

import com.example.mycookbook.ui.recyclerview.adapter.ListaDeIngredientesFormularioAdapter
import com.example.mycookbook.ui.viewmodel.FormReceitaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val formReceitaModule = module {

    factory {
        mutableListOf<String>()
    }

    factory {
        ListaDeIngredientesFormularioAdapter(
            androidContext()
        )
    }

    viewModel {
        FormReceitaViewModel()
    }
}