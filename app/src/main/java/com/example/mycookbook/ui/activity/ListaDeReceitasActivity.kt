package com.example.mycookbook.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycookbook.CHAVE_RECEITA_ID
import com.example.mycookbook.database.AppDataBase
import com.example.mycookbook.databinding.ActivityListaDeReceitasBinding
import com.example.mycookbook.repository.ReceitaRepository
import com.example.mycookbook.ui.activity.extensions.vaiPara
import com.example.mycookbook.ui.recyclerview.adapter.ListaDeReceitasAdapter
import com.example.mycookbook.ui.viewmodel.ReceitaViewModel
import com.example.mycookbook.ui.viewmodel.ReceitaViewModelFactory
import kotlinx.coroutines.launch

class ListaDeReceitasActivity : AppCompatActivity() {

    private val binding by lazy { ActivityListaDeReceitasBinding.inflate(layoutInflater) }
    private val recyclerViewReceitas by lazy { binding.recyclerviewListaReceitas }
    private val fabAdicionarReceita by lazy { binding.fabAdicionarReceita }
    private val adapter by lazy { ListaDeReceitasAdapter(this) }
    private lateinit var model: ReceitaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setsUpViewModel()
        cofiguraFab()
    }

    private fun setsUpViewModel() {
        val repository = ReceitaRepository(AppDataBase.instancia(this).ReceitaDAO())
        val modelFactory = ReceitaViewModelFactory(repository)
        model = ViewModelProvider(this, modelFactory)[ReceitaViewModel::class.java]
        configuraRecyclerView()
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
        buscaReceitas()
        configuraFuncoesAdapter()


    }

    private fun buscaReceitas() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                lifecycleScope.launch {
                    model.todasReceitas().also { receitas -> adapter.submitList(receitas) }
                }
                model.todasReceitasLiveData.observe(this@ListaDeReceitasActivity) { receitas ->
                    adapter.submitList(receitas)
                }
            }
        }
    }

    private fun configuraFuncoesAdapter() {
        adapter.apply {
            quandoClica = { receita ->
                vaiPara(DetalhesReceitaActivity::class.java) {
                    putExtra(CHAVE_RECEITA_ID, receita.id)
                }
            }
            quandoClicaEmDeletar = { receita ->
                lifecycleScope.launch {
                    model.deleta(receita)
                }
            }
            quandoClicaEmEditar = { receita ->
                vaiPara(FormularioReceitaActivity::class.java) {
                    putExtra(CHAVE_RECEITA_ID, receita.id)
                }
            }
        }
    }
}
