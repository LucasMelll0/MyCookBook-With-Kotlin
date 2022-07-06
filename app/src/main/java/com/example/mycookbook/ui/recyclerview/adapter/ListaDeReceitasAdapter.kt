package com.example.mycookbook.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycookbook.R
import com.example.mycookbook.databinding.ReceitaItemBinding
import com.example.mycookbook.model.Receita

class ListaDeReceitasAdapter(
    private val context: Context,
    private val listaDeReceitas: ArrayList<Receita>
) : RecyclerView.Adapter<ListaDeReceitasAdapter.ViewHolderReceita>() {

    private val dataSet = listaDeReceitas.toMutableList()

    class ViewHolderReceita(view: View) : RecyclerView.ViewHolder(view) {
        fun vincula(receita: Receita) {
            var nomeReceita =
                itemView.findViewById<AppCompatTextView>(R.id.textview_item_nome_receita)
            var categoriaReceita =
                itemView.findViewById<AppCompatTextView>(R.id.textview_item_categoria_receita)

            nomeReceita.text = receita.nome
            categoriaReceita.text = receita.categoria
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaDeReceitasAdapter.ViewHolderReceita {
        val inflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.receita_item, parent, false)

        return ViewHolderReceita(view)
    }

    override fun onBindViewHolder(holder: ListaDeReceitasAdapter.ViewHolderReceita, position: Int) {
        var receita = dataSet[position]
        holder.vincula(receita)
    }

    override fun getItemCount(): Int = dataSet.size

    fun atualiza(todas : ArrayList<Receita>){
        dataSet.clear()
        dataSet.addAll(todas)
        notifyDataSetChanged()
    }

}