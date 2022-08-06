package com.example.mycookbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycookbook.database.dao.ReceitaDAO
import com.example.mycookbook.databinding.ActivityFormularioReceitaBinding
import com.example.mycookbook.model.Receita

class FormularioReceitaActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFormularioReceitaBinding.inflate(layoutInflater) }

    private val campoNome by lazy { binding.edittextNomeReceita }
    private val campoIngrediente by lazy { binding.edittextIngredienteReceita }
    private val campoDescricao by lazy { binding.edittextDescricaoReceita }
    private val campoPorcao by lazy { binding.edittextPorcaoReceita }
    private val fabSalva by lazy { binding.fabSalvaReceita }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraFab()
    }

    private fun configuraFab() {
        fabSalva.setOnClickListener {
            val nome = campoNome.text.toString()
            val descricao = campoDescricao.toString()
            val porcao = campoPorcao.text.toString().toInt()

            val receita = Receita(
                nome = nome,
                ingredientes = ArrayList<String>(),
                descricao = descricao,
                categoria = "teste",
                porcao = porcao
            )

            finish()
        }

    }
}