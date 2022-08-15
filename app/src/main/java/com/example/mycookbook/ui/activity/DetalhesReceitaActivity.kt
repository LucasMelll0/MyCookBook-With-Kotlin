package com.example.mycookbook.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycookbook.CHAVE_RECEITA_ID
import com.example.mycookbook.database.AppDataBase
import com.example.mycookbook.databinding.ActivityDetalhesReceitaBinding
import com.example.mycookbook.repository.ReceitaRepository
import com.example.mycookbook.ui.recyclerview.adapter.ListaDeIngredientesDetalhesAdapter
import kotlinx.coroutines.launch

class DetalhesReceitaActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetalhesReceitaBinding.inflate(layoutInflater) }
    private val repository by lazy { ReceitaRepository(
        AppDataBase.instancia(this).ReceitaDAO()
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preencheCampos()
    }

    private fun preencheCampos() {
        intent.getStringExtra(CHAVE_RECEITA_ID)?.let { receitaId ->
            lifecycleScope.launch {
                repository.buscaPorId(receitaId).collect { receita ->
                    binding.textviewNomeReceitaDetalhes.text = receita.nome
                    configuraRecyclerViewIngredientes(receita.ingredientes)
                    binding.textviewModoDePreparoReceitaDetalhes.text = receita.descricao
                    binding.textviewCategoriaReceitaDetalhes.text = receita.categoria
                    binding.textviewPorcaoReceitaDetalhes.text = receita.porcao.toString()
                }
            }
        } ?: finish()
    }

    private fun configuraRecyclerViewIngredientes(ingredientes: List<String>) {
        binding.recyclerviewIngredientesReceitaDetalhes.apply {
            adapter = ListaDeIngredientesDetalhesAdapter(this@DetalhesReceitaActivity, ingredientes)
            layoutManager = LinearLayoutManager(this@DetalhesReceitaActivity)
        }
    }
}