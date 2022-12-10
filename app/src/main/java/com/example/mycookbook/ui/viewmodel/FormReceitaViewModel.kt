package com.example.mycookbook.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FormReceitaState(
    val listaIngredienteValue: MutableList<String>? = null,
)


class FormReceitaViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(FormReceitaState())
    val uiState: StateFlow<FormReceitaState> = _uiState.asStateFlow()


    fun salvaListaIngredientes(listaIngredientes: MutableList<String>){
        _uiState.update { currentState ->
            currentState.copy(
                listaIngredienteValue = listaIngredientes
            )
        }
    }
}