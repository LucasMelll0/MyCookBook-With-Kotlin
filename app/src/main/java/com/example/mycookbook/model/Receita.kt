package com.example.mycookbook.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
@Entity
data class Receita(
    @PrimaryKey
    val id : String = UUID.randomUUID().toString(),
    val imagem: String,
    val nome: String,
    val ingredientes: List<String>,
    val descricao: String,
    val categoria: String,
    val porcao: Int
) : Parcelable