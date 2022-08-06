package com.example.mycookbook.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.mycookbook.R
import com.example.mycookbook.databinding.ReceitaItemBinding
import com.example.mycookbook.model.Receita

class ListaDeReceitasAdapter(
    private val context: Context,
    listaDeReceitas: List<Receita> = emptyList(),
    var quandoClica: (receita: Receita) -> Unit = {},
    var quandoClicaEmEditar: (receita: Receita) -> Unit = {},
    var quandoClicaEmDeletar: (receita: Receita) -> Unit = {}
) : RecyclerView.Adapter<ListaDeReceitasAdapter.ViewHolderReceita>() {

    private val dataSet = listaDeReceitas.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaDeReceitasAdapter.ViewHolderReceita = ViewHolderReceita(
        ReceitaItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
    )


    inner class ViewHolderReceita(private val binding: ReceitaItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        PopupMenu.OnMenuItemClickListener {

        private lateinit var receita: Receita

        init {
            itemView.setOnClickListener {
                if (::receita.isInitialized) {
                    quandoClica(receita)
                }
            }
            itemView.setOnLongClickListener {
                    PopupMenu(context, itemView).apply {
                        menuInflater.inflate(R.menu.menu_receita, menu)
                        setOnMenuItemClickListener(this@ViewHolderReceita)
                    }.show()
                true
            }
        }

        fun vincula(receita: Receita) {
            this.receita = receita
            binding.apply {
                textviewItemNomeReceita.text = receita.nome
                textviewItemDescricaoReceita.text = receita.descricao
            }
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            item?.let {
                when (it.itemId) {
                    R.id.menu_editar_receita -> {
                        quandoClicaEmEditar(receita)
                    }
                    R.id.menu_deletar_receita -> {
                        quandoClicaEmDeletar(receita)
                    }
                }
            }
            return true
        }


    }

    override fun onBindViewHolder(holder: ListaDeReceitasAdapter.ViewHolderReceita, position: Int) {
        var receita = dataSet[position]
        holder.vincula(receita)
    }

    override fun getItemCount(): Int = dataSet.size

    fun atualiza(todas: List<Receita>) {
        dataSet.clear()
        dataSet.addAll(todas)
        notifyDataSetChanged()
    }

}