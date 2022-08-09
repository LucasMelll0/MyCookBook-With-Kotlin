package com.example.mycookbook.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.mycookbook.CHAVE_RECEITA_ID
import com.example.mycookbook.database.AppDataBase
import com.example.mycookbook.databinding.ActivityDetalhesReceitaBinding
import com.example.mycookbook.repository.ReceitaRepository
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
                    binding.textviewModoDePreparoReceitaDetalhes.text = receita.descricao
                    binding.textviewCategoriaReceitaDetalhes.text = receita.categoria
                    binding.textviewPorcaoReceitaDetalhes.text = receita.porcao.toString()
                }
            }
        } ?: finish()
    }
}