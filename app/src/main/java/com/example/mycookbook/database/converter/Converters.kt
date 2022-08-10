package com.example.mycookbook.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters(){

    @TypeConverter
    fun fromString(value: String) : List<String>{
        val listType = object : TypeToken<List<String>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String>) : String = Gson().toJson(list)
}