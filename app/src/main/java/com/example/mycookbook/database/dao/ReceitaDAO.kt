package com.example.mycookbook.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mycookbook.model.Receita

@Dao
interface ReceitaDAO {


    @Delete
    suspend fun deletaReceita(receita: Receita)

    @Query("SELECT * FROM Receita")
    fun buscaTodasLiveData(): LiveData<List<Receita>>

    @Query("SELECT * FROM Receita")
    suspend fun buscaTodas(): List<Receita>

    @Query("SELECT * FROM Receita WHERE id = :id")
    fun buscaPorId(id: String): LiveData<Receita?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReceita(receita: Receita)


}