package com.example.mycookbook

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycookbook.database.AppDataBase
import com.example.mycookbook.databinding.ActivityFormularioReceitaBinding
import com.example.mycookbook.model.Receita
import com.example.mycookbook.repository.ReceitaRepository
import com.example.mycookbook.ui.recyclerview.adapter.ListaDeIngredientesFormularioAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FormularioReceitaActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFormularioReceitaBinding.inflate(layoutInflater) }

    private val campoNome by lazy { binding.edittextNomeReceita }
    private val campoIngrediente by lazy { binding.edittextIngredienteReceita }
    private val campoDescricao by lazy { binding.edittextDescricaoReceita }
    private val campoPorcao by lazy { binding.edittextPorcaoReceita }
    private val fabSalva by lazy { binding.fabSalvaReceita }
    private val ingredientes by lazy { mutableListOf<String?>() }
    private val adapterIngredientes by lazy {
        ListaDeIngredientesFormularioAdapter(
            this,
            ingredientes
        )
    }
    private val receitaRepository by lazy {
        ReceitaRepository(
            AppDataBase.instancia(this).ReceitaDAO()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraListaIngredientes()
        configuraFab()
    }

    private fun configuraListaIngredientes() {
        binding.recyclerViewIngredientesFormulario.apply {
            adapter = adapterIngredientes
            layoutManager = LinearLayoutManager(this@FormularioReceitaActivity)
        }


        configuraCampoIngredientes()


    }

    private fun configuraCampoIngredientes() {
        binding.apply {
            imagebuttonAdicionarIngrediente.setOnClickListener {
                val ingrediente = campoIngrediente.text.toString()
                ingredientes.add(ingrediente)
                adapterIngredientes.atualiza(ingredientes)
                Log.i(TAG, "configuraCampoIngredientes: $ingredientes")
            }
        }
    }

    private fun configuraFab() {
        fabSalva.setOnClickListener {
            val nome = campoNome.text.toString()
            val descricao = campoDescricao.text.toString()
            val porcao = if (!campoPorcao.text.isNullOrBlank()) {
                campoPorcao.text.toString().toInt()
            } else {
                0
            }

            val receita = Receita(
                nome = nome,
                ingredientes = ingredientes.toList() as ArrayList<String>,
                descricao = descricao,
                categoria = "teste",
                porcao = porcao
            )

            lifecycleScope.launch {
                receitaRepository.salva(receita)
            }
            finish()
        }

    }
}