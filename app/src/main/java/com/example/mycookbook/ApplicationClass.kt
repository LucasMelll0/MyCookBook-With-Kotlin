package com.example.mycookbook

import android.app.Application
import com.example.mycookbook.di.detalhesReceitaModule
import com.example.mycookbook.di.formReceitaModule
import com.example.mycookbook.di.listaReceitasModule
import com.example.mycookbook.di.receitaModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ApplicationClass)

            modules(listOf(
                receitaModule,
                listaReceitasModule,
                formReceitaModule,
                detalhesReceitaModule
            ) )
        }

    }
}