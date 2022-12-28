package com.example.mycookbook.repository

import androidx.lifecycle.LiveData
import com.example.mycookbook.database.dao.ReceitaDAO
import com.example.mycookbook.model.Receita

class ReceitaRepository(private val dao : ReceitaDAO) {

    val todasReceitas = dao.buscaTodas()

    suspend fun salva(receita: Receita) = dao.addReceita(receita)

    suspend fun deleta(receita: Receita) = dao.deletaReceita(receita)

    fun buscaPorId(id: String) : LiveData<Receita?> = dao.buscaPorId(id)


}