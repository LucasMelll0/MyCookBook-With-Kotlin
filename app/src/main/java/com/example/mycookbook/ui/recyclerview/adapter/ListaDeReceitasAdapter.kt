package com.example.mycookbook.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycookbook.databinding.ReceitaItemBinding
import com.example.mycookbook.model.Receita

class ListaDeReceitasAdapter(
    private val context: Context,
    private val listaDeReceitas: ArrayList<Receita>
) : RecyclerView.Adapter<ListaDeReceitasAdapter.ViewHolderReceita>() {

    private val dataSet = listaDeReceitas.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaDeReceitasAdapter.ViewHolderReceita
        = ViewHolderReceita(ReceitaItemBinding.inflate(
        LayoutInflater.from(context),
        parent,
        false
    ))


    class ViewHolderReceita(private val binding: ReceitaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun vincula(receita: Receita) {
            binding.apply {
                textviewItemNomeReceita.text = receita.nome
                textviewItemDescricaoReceita.text = receita.descricao
            }
        }

    }

    override fun onBindViewHolder(holder: ListaDeReceitasAdapter.ViewHolderReceita, position: Int) {
        var receita = dataSet[position]
        holder.vincula(receita)
    }

    override fun getItemCount(): Int = dataSet.size

    fun atualiza(todas: ArrayList<Receita>) {
        dataSet.clear()
        dataSet.addAll(todas)
        notifyDataSetChanged()
    }

}