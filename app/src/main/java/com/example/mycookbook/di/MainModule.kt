package com.example.mycookbook.di

import com.example.mycookbook.database.AppDataBase
import com.example.mycookbook.repository.ReceitaRepository
import com.example.mycookbook.ui.viewmodel.ReceitaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val receitaModule = module {
    factory {
        ReceitaRepository(get())
    }
    factory {
        AppDataBase.instancia(androidContext()).ReceitaDAO()
    }
    viewModel {
        ReceitaViewModel(
            get()
        )
    }
}