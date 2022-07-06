package com.example.mycookbook.dao

import com.example.mycookbook.model.Receita

class ReceitaDAO {

    companion object{
        private val receitas = mutableListOf<Receita>()
    }

    fun adicionarReceita(receita : Receita){ receitas.add(receita)}

    fun todas() : ArrayList<Receita> { return ArrayList<Receita>(receitas) }
}