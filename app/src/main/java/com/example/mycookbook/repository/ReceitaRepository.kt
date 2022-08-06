package com.example.mycookbook.repository

import com.example.mycookbook.database.dao.ReceitaDAO
import com.example.mycookbook.model.Receita
import kotlinx.coroutines.flow.Flow

class ReceitaRepository(private val dao : ReceitaDAO) {

    fun buscaTodas() : Flow<List<Receita>> = dao.buscaTodas()

    suspend fun salva(receita: Receita) = dao.addReceita(receita)

    suspend fun deleta(receita: Receita) = dao.deletaReceita(receita)

    fun buscaPorId(id: String) : Flow<Receita> = dao.buscaPorId(id)


}