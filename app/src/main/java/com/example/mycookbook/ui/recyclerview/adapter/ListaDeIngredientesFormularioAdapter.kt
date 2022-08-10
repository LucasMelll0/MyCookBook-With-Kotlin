package com.example.mycookbook.ui.recyclerview.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycookbook.databinding.IngredienteItemFormularioBinding

class ListaDeIngredientesFormularioAdapter(
    val context : Context,
    ingredientes : List<String> = emptyList(),
    var quandoClicaEmEditar : (position: String) -> Unit = {},
    var quandoClicaEmDeletar :(ingrediente: String) -> Unit = {}
) :
    RecyclerView.Adapter<ListaDeIngredientesFormularioAdapter.ViewHolderIngredientesFormulario>() {

    var listaDeIngredientes = ingredientes.toMutableList()

    inner class ViewHolderIngredientesFormulario
        (val binding : IngredienteItemFormularioBinding)
        : RecyclerView.ViewHolder(binding.root) {

        private lateinit var ingrediente : String

        init {
            binding.apply {
                imageviewEditIngredienteItemFormulario
                    .setOnClickListener {
                        quandoClicaEmEditar(ingrediente)
                    }
                imageviewDeleteIngredienteTemFormulario
                    .setOnClickListener {
                        quandoClicaEmDeletar(ingrediente)
                    }
            }
        }

            fun vincula(ingrediente : String){
                this.ingrediente = ingrediente
                ingrediente.let {
                    binding.textviewNomeIngredienteItemFormulario. text = it
                }
            }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderIngredientesFormulario {
        val binding = IngredienteItemFormularioBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolderIngredientesFormulario(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderIngredientesFormulario, position: Int) {
        val ingrediente = listaDeIngredientes[position]
        holder.vincula(ingrediente)
    }

    override fun getItemCount(): Int = listaDeIngredientes.size

    fun atualiza(ingredientes: List<String>) {
        listaDeIngredientes.clear()
        listaDeIngredientes.addAll(ingredientes)
        notifyDataSetChanged()
    }
}