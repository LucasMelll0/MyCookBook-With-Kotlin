package com.example.mycookbook.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.mycookbook.CHAVE_RECEITA_ID
import com.example.mycookbook.R
import com.example.mycookbook.TAG
import com.example.mycookbook.database.AppDataBase
import com.example.mycookbook.databinding.ActivityFormularioReceitaBinding
import com.example.mycookbook.model.Receita
import com.example.mycookbook.repository.ReceitaRepository
import com.example.mycookbook.ui.activity.extensions.adapterPadrao
import com.example.mycookbook.ui.recyclerview.adapter.ListaDeIngredientesFormularioAdapter
import kotlinx.coroutines.launch

class FormularioReceitaActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFormularioReceitaBinding.inflate(layoutInflater) }
    private var receitaId: String? = null
    private var imagem: String? = null
    private val fabSalva by lazy { binding.fabSalvaReceita }
    private val ingredientes by lazy { mutableListOf<String>() }
    private val adapterIngredientes by lazy {
        ListaDeIngredientesFormularioAdapter(
            this,
            ingredientes
        )
    }
    private val spinnerIngredientes by lazy { binding.spinnerCategoriaReceita }
    private val receitaRepository by lazy {
        ReceitaRepository(
            AppDataBase.instancia(this).ReceitaDAO()
        )
    }

    companion object {
        // Pega a permissão de leitura de armazenamento
        private val permissaoGaleria = Manifest.permission.READ_EXTERNAL_STORAGE
    }

    private lateinit var dialogPermissaoGaleria: AlertDialog

    // Verifica se a permissão foi aceita atráves do requestGaleria.launch('permissao')
    private val requestGaleria = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissao ->
        if (permissao) {
            resultGaleria.launch(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            )
        } else {
            mostraDialogPermissao()
        }

    }

    private val resultGaleria = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        if (resultado.data?.data != null) {
            val uri = resultado.data?.data
            Log.i(TAG, "resultado da galeria: ${resultado.data?.data}")
            binding.imageviewFormularioReceita.load(uri)
            imagem = uri.toString()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        verificaSeTemExtras()
        configuraImagem()
        configuraListaIngredientes()
        configuraSpinnerCategoria()
        configuraFab()
    }

    private fun verificaSeTemExtras() {
        intent.getStringExtra(CHAVE_RECEITA_ID)?.let { idReceita ->
            lifecycleScope.launch {
                buscaReceitaNoBd(idReceita)
            }
        }
    }

    private suspend fun buscaReceitaNoBd(idReceita: String) {
        receitaRepository.buscaPorId(idReceita).collect {
            it?.let { receita ->
                preencheCampos(receita)
            }
        }
    }

    private fun preencheCampos(receita: Receita) {
        binding.apply {
            receitaId = receita.id
            imageviewFormularioReceita.load(receita.imagem) {
                error(R.drawable.imagem_padrao)
            }
            imagem = receita.imagem
            edittextNomeReceita.setText(receita.nome)
            ingredientes.addAll(receita.ingredientes)
            edittextDescricaoReceita.setText(receita.descricao)
            val categorias = resources.getStringArray(R.array.categorias_array)
            spinnerCategoriaReceita.setSelection(categorias.indexOf(receita.categoria))
            edittextPorcaoReceita.setText(receita.porcao.toString())
            adapterIngredientes.atualiza(ingredientes)
        }
    }

    private fun configuraImagem() {
        binding.imageviewFormularioReceita.setOnClickListener {
            verificaPermissaoGaleria()
        }
    }

    private fun configuraListaIngredientes() {
        binding.recyclerViewIngredientesFormulario.apply {
            adapter = adapterIngredientes
            layoutManager = LinearLayoutManager(this@FormularioReceitaActivity)
            configuraFuncoesAdapter()
        }
        configuraBotaoAdicionaIngrediente()
    }

    private fun configuraFuncoesAdapter() {
        adapterIngredientes.apply {
            quandoClicaEmEditar = { ingrediente ->
                binding.edittextIngredienteReceita.setText(ingrediente)
                ingredientes.remove(ingrediente)
                atualiza(ingredientes)
            }
            quandoClicaEmDeletar = { ingrediente ->
                ingredientes.remove(ingrediente)
                atualiza(ingredientes)
            }
        }
    }

    private fun configuraBotaoAdicionaIngrediente() {
        binding.apply {
            imagebuttonAdicionarIngrediente.setOnClickListener {
                retornaIngredienteSeForValido()?.let { ingrediente ->
                    ingredientes.add(ingrediente)
                    adapterIngredientes.atualiza(ingredientes)
                    edittextIngredienteReceita.text?.clear()
                    Log.i(TAG, "configuraCampoIngredientes: $ingredientes")
                }
            }
        }
    }

    private fun retornaIngredienteSeForValido() =
        binding.edittextIngredienteReceita.text.toString().ifBlank {
            null
        }

    private fun configuraSpinnerCategoria() {
        val categorias = resources.getStringArray(R.array.categorias_array)
        spinnerIngredientes.adapterPadrao(this, categorias)
    }

    private fun configuraFab() {
        fabSalva.setOnClickListener {
            salvaReceita()
            finish()
        }

    }

    private fun salvaReceita() {
        val receita = criaReceita()
        lifecycleScope.launch {
            receitaRepository.salva(receita)
        }
    }

    private fun criaReceita(): Receita {
        receitaId?.let {
            binding.apply {
                return Receita(
                    id = receitaId.toString(),
                    imagem = imagem ?: "",
                    nome = edittextNomeReceita.text.toString(),
                    ingredientes = ingredientes.toList(),
                    descricao = edittextDescricaoReceita.text.toString(),
                    categoria = spinnerIngredientes.selectedItem.toString(),
                    porcao = verificaSePorcaoEstaVazio()
                )
            }
        }
        binding.apply {
            return Receita(
                imagem = imagem ?: "",
                nome = edittextNomeReceita.text.toString(),
                ingredientes = ingredientes.toList(),
                descricao = edittextDescricaoReceita.text.toString(),
                categoria = spinnerIngredientes.selectedItem.toString(),
                porcao = verificaSePorcaoEstaVazio()
            )
        }
    }

    private fun verificaSePorcaoEstaVazio() =
        if (!binding.edittextPorcaoReceita.text.isNullOrBlank()) {
            binding.edittextPorcaoReceita.text.toString().toInt()
        } else {
            0
        }

    private fun verificaPermissaoGaleria() {
        val permissaoGaleriaAceita = verificaPermissao(permissaoGaleria)

        when {
            // Se a permissão da galeria tiver sido aceita, abre a galeria, ee pega o resultado da galeria através do resultGaleria.
            permissaoGaleriaAceita -> {
                resultGaleria.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            }
            //Mostra o dialog para aceitar a permissão da galeria
            shouldShowRequestPermissionRationale(permissaoGaleria) -> mostraDialogPermissao()

            //Se não tiver sido aceito faz um request da permissão da galeria através do requestGaleria.
            else -> requestGaleria.launch(permissaoGaleria)
        }
    }

    private fun mostraDialogPermissao() {
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.atencao))
            .setMessage(getString(R.string.mensagem_permissão_galeria))
            .setPositiveButton(getString(R.string.permitir)) { _, _ ->
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(this)
                }
                dialogPermissaoGaleria.dismiss()
            }
            .setNegativeButton(getString(R.string.Negar)) { _, _ ->
                dialogPermissaoGaleria.dismiss()
            }

        dialogPermissaoGaleria = builder.create()
        dialogPermissaoGaleria.show()
    }


    // Verificador de Permissão genérico
    private fun verificaPermissao(permissao: String) =
        ContextCompat.checkSelfPermission(this, permissao) == PackageManager.PERMISSION_GRANTED


}