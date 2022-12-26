package com.example.mycookbook.di

import com.example.mycookbook.ui.viewmodel.FormReceitaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val formReceitaModule = module {

    viewModel {
        FormReceitaViewModel()
    }
}