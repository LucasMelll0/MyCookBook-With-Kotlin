package com.example.mycookbook.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycookbook.R
import com.example.mycookbook.TAG
import com.example.mycookbook.database.AppDataBase
import com.example.mycookbook.databinding.ActivityFormularioReceitaBinding
import com.example.mycookbook.model.Receita
import com.example.mycookbook.repository.ReceitaRepository
import com.example.mycookbook.ui.activity.extensions.adapterPadrao
import com.example.mycookbook.ui.recyclerview.adapter.ListaDeIngredientesFormularioAdapter
import kotlinx.coroutines.launch

class FormularioReceitaActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFormularioReceitaBinding.inflate(layoutInflater) }

    private val campoNome by lazy { binding.edittextNomeReceita }
    private val campoIngrediente by lazy { binding.edittextIngredienteReceita }
    private val campoDescricao by lazy { binding.edittextDescricaoReceita }
    private val campoPorcao by lazy { binding.edittextPorcaoReceita }
    private val fabSalva by lazy { binding.fabSalvaReceita }
    private val ingredientes by lazy { mutableListOf<String>() }
    private val adapterIngredientes by lazy {
        ListaDeIngredientesFormularioAdapter(
            this,
            ingredientes
        )
    }
    private val spinnerIngredientes by lazy { binding.spinnerCategoriaReceita }
    private val receitaRepository by lazy {
        ReceitaRepository(
            AppDataBase.instancia(this).ReceitaDAO()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraListaIngredientes()
        configuraSpinnerCategoria()
        configuraFab()
    }

    private fun configuraListaIngredientes() {
        binding.recyclerViewIngredientesFormulario.apply {
            adapter = adapterIngredientes
            layoutManager = LinearLayoutManager(this@FormularioReceitaActivity)
            configuraFuncoesAdapter()
        }
        configuraBotaoAdicionaIngrediente()
    }

    private fun configuraFuncoesAdapter() {
        adapterIngredientes.apply {
            quandoClicaEmEditar = { ingrediente ->
                binding.edittextIngredienteReceita.setText(ingrediente)
                ingredientes.remove(ingrediente)
                atualiza(ingredientes)
            }
            quandoClicaEmDeletar = { ingrediente ->
                ingredientes.remove(ingrediente)
                atualiza(ingredientes)
            }
        }
    }

    private fun configuraBotaoAdicionaIngrediente() {
        binding.apply {
            imagebuttonAdicionarIngrediente.setOnClickListener {
                retornaIngredienteSeForValido()?.let { ingrediente ->
                    ingredientes.add(ingrediente)
                    adapterIngredientes.atualiza(ingredientes)
                    campoIngrediente.text?.clear()
                    Log.i(TAG, "configuraCampoIngredientes: $ingredientes")
                }
            }
        }
    }

    private fun retornaIngredienteSeForValido(): String? {
        return campoIngrediente.text.toString().ifBlank {
            null
        }
    }

    private fun configuraSpinnerCategoria() {
        val categorias = resources.getStringArray(R.array.categorias_array)
        Log.i(TAG, "configuraSpinnerCategoria: $categorias")
        spinnerIngredientes.adapterPadrao(this, categorias)

    }

    private fun configuraFab() {
        fabSalva.setOnClickListener {
            salvaReceita()
            finish()
        }

    }

    private fun salvaReceita() {
        val receita = criaReceita()
        lifecycleScope.launch {
            receitaRepository.salva(receita)
        }
    }

    private fun criaReceita(): Receita {
        val nome = campoNome.text.toString()
        val descricao = campoDescricao.text.toString()
        val porcao = verificaSePorcaoEstaVazio()
        return Receita(
            nome = nome,
            ingredientes = ingredientes.toList(),
            descricao = descricao,
            categoria = spinnerIngredientes.selectedItem.toString(),
            porcao = porcao
        )
    }

    private fun verificaSePorcaoEstaVazio() = if (!campoPorcao.text.isNullOrBlank()) {
        campoPorcao.text.toString().toInt()
    } else {
        0
    }
}