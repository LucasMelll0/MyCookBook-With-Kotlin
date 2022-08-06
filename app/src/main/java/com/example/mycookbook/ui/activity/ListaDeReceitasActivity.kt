package com.example.mycookbook.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycookbook.FormularioReceitaActivity
import com.example.mycookbook.database.dao.ReceitaDAO
import com.example.mycookbook.databinding.ActivityListaDeReceitasBinding
import com.example.mycookbook.model.Receita
import com.example.mycookbook.ui.recyclerview.adapter.ListaDeReceitasAdapter

class ListaDeReceitasActivity : AppCompatActivity() {

    private val binding by lazy{ ActivityListaDeReceitasBinding.inflate(layoutInflater) }

    private val recyclerViewReceitas by lazy{ binding.recyclerviewListaReceitas }

    private  val fabAdicionarReceita by lazy{ binding.fabAdicionarReceita}

    private val adapter by lazy { ListaDeReceitasAdapter(this, ArrayList<Receita>()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        cofiguraFab()
    }

    override fun onResume() {
        super.onResume()

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