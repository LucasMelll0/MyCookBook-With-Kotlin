package com.example.mycookbook.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycookbook.databinding.IngredienteItemDetalhesBinding

class ListaDeIngredientesDetalhesAdapter(
    val context: Context,
    ingredientes: List<String> = emptyList()
) : RecyclerView.Adapter<ListaDeIngredientesDetalhesAdapter.ViewHolderIngredientesDetalhes>() {
    val ingredientesDetalhes = ingredientes.toMutableList()


    class ViewHolderIngredientesDetalhes(val binding: IngredienteItemDetalhesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun vincula(ingrediente: String) {
            binding.textviewIngredienteNomeItemDetalhe.text = ingrediente

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderIngredientesDetalhes {
        val binding =
            IngredienteItemDetalhesBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolderIngredientesDetalhes(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderIngredientesDetalhes, position: Int) {
        val ingrediente = ingredientesDetalhes[position]
        holder.vincula(ingrediente)
    }

    override fun getItemCount(): Int = ingredientesDetalhes.size

    fun atualiza(ingredientes: List<String>){
        ingredientesDetalhes.clear()
        ingredientesDetalhes.addAll(ingredientes)
        notifyDataSetChanged()
    }
}