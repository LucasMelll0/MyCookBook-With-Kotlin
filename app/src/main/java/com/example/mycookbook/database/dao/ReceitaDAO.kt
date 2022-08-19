package com.example.mycookbook.database.dao

import androidx.room.*
import com.example.mycookbook.model.Receita
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceitaDAO {


    @Delete
    suspend fun deletaReceita(receita: Receita)

    @Query("SELECT * FROM Receita")
    fun buscaTodas() : Flow<List<Receita>>

    @Query("SELECT * FROM Receita WHERE id = :id")
    fun buscaPorId(id: String) : Flow<Receita?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReceita(receita: Receita)


}