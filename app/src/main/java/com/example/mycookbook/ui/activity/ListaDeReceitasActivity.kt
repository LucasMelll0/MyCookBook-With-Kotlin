package com.example.mycookbook.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycookbook.CHAVE_RECEITA_ID
import com.example.mycookbook.R
import com.example.mycookbook.databinding.ActivityListaDeReceitasBinding
import com.example.mycookbook.ui.activity.extensions.vaiPara
import com.example.mycookbook.ui.recyclerview.adapter.ListaDeReceitasAdapter
import com.example.mycookbook.ui.viewmodel.ReceitaViewModel
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListaDeReceitasActivity : AppCompatActivity() {

    private val binding by lazy { ActivityListaDeReceitasBinding.inflate(layoutInflater) }
    private val recyclerViewReceitas by lazy { binding.recyclerviewListaReceitas }
    private val fabAdicionarReceita by lazy { binding.fabAdicionarReceita }
    private val adapter by lazy { ListaDeReceitasAdapter(this) }
    private val model: ReceitaViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraToolbar()
        cofiguraFab()
        configuraRecyclerView()
    }

    private fun configuraToolbar() {
        val toolbar = binding.toolbarListaDeReceitas
        configuraMenu(toolbar)
    }


    private fun configuraMenu(toolbar: MaterialToolbar) {
        toolbar.inflateMenu(R.menu.menu_pesquisa)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> {
                    val searchView = menuItem.actionView as? SearchView
                    configuraPesquisa(searchView)
                    true
                }
                else -> false
            }

        }
    }

    private fun configuraPesquisa(search: SearchView?) {
        search?.let {
            search.isSubmitButtonEnabled = false
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        model.todasReceitasLiveData.value?.let { receitas ->
                            val listaFiltrada = receitas.filter { receita ->
                                receita.nome.contains(newText, true)
                            }
                            adapter.submitList(listaFiltrada)
                        }
                    } ?: buscaReceitas()
                    return false
                }
            }
            )
        }
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
