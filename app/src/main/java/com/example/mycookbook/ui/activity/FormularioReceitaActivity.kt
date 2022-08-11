package com.example.mycookbook.ui.activity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycookbook.R
import com.example.mycookbook.database.AppDataBase
import com.example.mycookbook.databinding.ActivityFormularioReceitaBinding
import com.example.mycookbook.model.Receita
import com.example.mycookbook.repository.ReceitaRepository
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

    private fun retornaIngredienteSeForValido() : String? {
        return campoIngrediente.text.toString().ifBlank {
            null
        }
    }

    private fun configuraSpinnerCategoria(){
        binding.spinnerCategoriaReceita.apply {
            val categorias = resources.getStringArray(R.array.categorias_array)
            Log.i(TAG, "configuraSpinnerCategoria: $categorias")
            adapter = configuraAdapterSpinner(categorias)
            //terminar de implementar

        }
    }

    private fun configuraAdapterSpinner(categorias: Array<out String>) =
        ArrayAdapter<String>(
            this@FormularioReceitaActivity,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            categorias
        )

    private fun configuraFab() {
        fabSalva.setOnClickListener {
            val receita = criaReceita()
            lifecycleScope.launch {
                receitaRepository.salva(receita)
            }
            finish()
        }

    }

    private fun criaReceita(): Receita {
        val nome = campoNome.text.toString()
        val descricao = campoDescricao.text.toString()
        val porcao = if (!campoPorcao.text.isNullOrBlank()) {
            campoPorcao.text.toString().toInt()
        } else {
            0
        }

        val receita = Receita(
            nome = nome,
            ingredientes = ingredientes.toList(),
            descricao = descricao,
            categoria = "teste",
            porcao = porcao
        )
        return receita
    }
}