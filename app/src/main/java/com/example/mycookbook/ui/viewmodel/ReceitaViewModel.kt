package com.example.mycookbook.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mycookbook.model.Receita
import com.example.mycookbook.repository.ReceitaRepository


class ReceitaViewModel(private val repository: ReceitaRepository) :
    ViewModel() {

    internal val todasReceitasLiveData: LiveData<List<Receita>> =
        repository.todasReceitas.asLiveData()

    fun todasReceitas(): List<Receita> {
        return todasReceitasLiveData.value ?: emptyList()
    }

    suspend fun salva(receita: Receita) = repository.salva(receita)

    suspend fun deleta(receita: Receita) = repository.deleta(receita)

    fun buscaPorId(id: String) = repository.buscaPorId(id)
}