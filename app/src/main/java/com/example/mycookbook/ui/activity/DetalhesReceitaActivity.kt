package com.example.mycookbook.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.mycookbook.CHAVE_RECEITA_ID
import com.example.mycookbook.database.AppDataBase
import com.example.mycookbook.databinding.ActivityDetalhesReceitaBinding
import com.example.mycookbook.model.Receita
import com.example.mycookbook.repository.ReceitaRepository
import com.example.mycookbook.ui.activity.extensions.vaiPara
import com.example.mycookbook.ui.recyclerview.adapter.ListaDeIngredientesDetalhesAdapter
import kotlinx.coroutines.launch

class DetalhesReceitaActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetalhesReceitaBinding.inflate(layoutInflater) }
    private lateinit var idReceita: String
    private val adapterIngredientes by lazy {
        ListaDeIngredientesDetalhesAdapter(this@DetalhesReceitaActivity)
    }
    private val repository by lazy {
        ReceitaRepository(
            AppDataBase.instancia(this).ReceitaDAO()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        verificaSeVeioExtras()
        configuraFabEditar()
    }

    override fun onResume() {
        super.onResume()
        buscaReceitaNoDb(idReceita)
    }

    private fun configuraFabEditar() {
        binding.fabEditarDetalhes.setOnClickListener {
            vaiPara(FormularioReceitaActivity::class.java) {
                putExtra(CHAVE_RECEITA_ID, idReceita)
            }
        }
    }

    private fun verificaSeVeioExtras() {
        intent.getStringExtra(CHAVE_RECEITA_ID)?.let { receitaId ->
            idReceita = receitaId
            buscaReceitaNoDb(receitaId)
        } ?: finish()
    }

    private fun buscaReceitaNoDb(receitaId: String) = lifecycleScope.launch {
        repository.buscaPorId(receitaId).collect { it ->
            it?.let { receita ->
                preencheReceita(receita)
            }
        }
    }

    private fun preencheReceita(receita: Receita) {
        binding.imageviewDetalhesReceita.load(receita.imagem)
        binding.textviewNomeReceitaDetalhes.text = receita.nome
        configuraRecyclerViewIngredientes()
        adapterIngredientes.atualiza(receita.ingredientes)
        binding.textviewModoDePreparoReceitaDetalhes.text = receita.descricao
        binding.textviewCategoriaReceitaDetalhes.text = receita.categoria
        binding.textviewPorcaoReceitaDetalhes.text = receita.porcao.toString()
    }

    private fun configuraRecyclerViewIngredientes() {
        binding.recyclerviewIngredientesReceitaDetalhes.apply {
            adapter = adapterIngredientes
            layoutManager = LinearLayoutManager(this@DetalhesReceitaActivity)
        }
    }
}