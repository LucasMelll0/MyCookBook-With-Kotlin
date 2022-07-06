package com.example.mycookbook

import android.app.Application
import com.example.mycookbook.dao.ReceitaDAO
import com.example.mycookbook.model.Receita

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        val dao = ReceitaDAO()
        dao.adicionarReceita(
            Receita(
                nome = "Teste Receita",
                categoria = "Teste",
                ingredientes = ArrayList(),
                descricao = "Testes",
                porcao = 2,
            )
        )
    }
}