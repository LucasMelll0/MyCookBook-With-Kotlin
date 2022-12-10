package com.example.mycookbook.repository

import androidx.lifecycle.LiveData
import com.example.mycookbook.database.dao.ReceitaDAO
import com.example.mycookbook.model.Receita

class ReceitaRepository(private val dao : ReceitaDAO) {

    suspend fun buscaTodas(): List<Receita> = dao.buscaTodas()

    fun buscaTodasLiveData() : LiveData<List<Receita>> = dao.buscaTodasLiveData()

    suspend fun salva(receita: Receita) = dao.addReceita(receita)

    suspend fun deleta(receita: Receita) = dao.deletaReceita(receita)

    fun buscaPorId(id: String) : LiveData<Receita?> = dao.buscaPorId(id)


}