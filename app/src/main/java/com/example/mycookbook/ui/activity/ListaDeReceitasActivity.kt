package com.example.mycookbook.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycookbook.FormularioReceitaActivity
import com.example.mycookbook.R
import com.example.mycookbook.dao.ReceitaDAO
import com.example.mycookbook.databinding.ActivityListaDeReceitasBinding
import com.example.mycookbook.model.Receita
import com.example.mycookbook.ui.recyclerview.adapter.ListaDeReceitasAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ListaDeReceitasActivity : AppCompatActivity() {

    private val binding by lazy{ ActivityListaDeReceitasBinding.inflate(layoutInflater) }

    private val recyclerViewReceitas by lazy{ binding.recyclerviewListaReceitas }

    private  val fabAdicionarReceita by lazy{ binding.fabAdicionarReceita}

    private val dao by lazy { ReceitaDAO() }

    private val adapter by lazy { ListaDeReceitasAdapter(this, dao.todas()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        cofiguraFab()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.todas())

    }

    private fun cofiguraFab() {
        fabAdicionarReceita.setOnClickListener {
            val formularioParaNovaReceita = Intent(this, FormularioReceitaActivity::class.java)
            startActivity(formularioParaNovaReceita)
        }
    }

    private fun configuraRecyclerView() {
        recyclerViewReceitas.adapter = adapter
        recyclerViewReceitas.layoutManager = LinearLayoutManager(this)
    }
}