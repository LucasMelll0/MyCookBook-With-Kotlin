package com.example.mycookbook.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mycookbook.model.Receita
import com.example.mycookbook.repository.ReceitaRepository


@Suppress("UNCHECKED_CAST")
class ReceitaViewModelFactory(

    private val repository: ReceitaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReceitaViewModel::class.java)) {
            return ReceitaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ReceitaViewModel(private val repository: ReceitaRepository) :
    ViewModel() {

    internal val todasReceitasLiveData: LiveData<List<Receita>> = repository.buscaTodasLiveData()

    suspend fun todasReceitas() = repository.buscaTodas()

    suspend fun salva(receita: Receita) = repository.salva(receita)

    suspend fun deleta(receita: Receita) = repository.deleta(receita)

    fun buscaPorId(id: String) = repository.buscaPorId(id)
}