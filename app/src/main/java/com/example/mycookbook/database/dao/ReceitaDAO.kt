package com.example.mycookbook.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mycookbook.model.Receita
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceitaDAO {


    @Query("SELECT * FROM Receita")
    fun buscaTodas() : Flow<List<Receita>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReceita(receita: Receita)


}