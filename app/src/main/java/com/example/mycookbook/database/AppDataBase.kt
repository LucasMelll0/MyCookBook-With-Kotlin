package com.example.mycookbook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mycookbook.database.converter.Converters
import com.example.mycookbook.database.dao.ReceitaDAO
import com.example.mycookbook.model.Receita


@Database(
    version = 2,
    entities = [Receita::class],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract  fun ReceitaDAO(): ReceitaDAO

    companion object{
        @Volatile
        private var db: AppDataBase? = null

        fun instancia(context: Context): AppDataBase{
            return db ?: Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "mycookbook.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}