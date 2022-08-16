package com.example.mycookbook.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2){

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Receita ADD COLUMN 'imagem' TEXT")
    }

}