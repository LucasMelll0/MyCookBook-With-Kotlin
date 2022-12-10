package com.example.mycookbook.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mycookbook.database.AppDataBase
import com.example.mycookbook.model.Receita
import com.example.mycookbook.repository.ReceitaRepository


class ReceitaViewModelFactory(var application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReceitaViewModel::class.java)){
            return ReceitaViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ReceitaViewModel(var application: Application): ViewModel(){
    private val db: AppDataBase = AppDataBase.instancia(application)
    private val repository: ReceitaRepository = ReceitaRepository(db.ReceitaDAO())

    internal val todasReceitasLiveData: LiveData<List<Receita>> = repository.buscaTodasLiveData()

    suspend fun todaReceitas() = repository.buscaTodas()

    suspend fun salva(receita: Receita) = repository.salva(receita)

    suspend fun deleta(receita: Receita) = repository.deleta(receita)

    fun buscaPorId(id: String) = repository.buscaPorId(id)
}