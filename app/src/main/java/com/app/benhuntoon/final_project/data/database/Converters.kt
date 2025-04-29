package com.app.benhuntoon.final_project.data.database

// app/src/main/java/com/app/benhuntoon/final_project/data/database/Converters.kt

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    // Convert List<String> to JSON String
    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        println("Converting list to JSON: $list")
        return list?.let { gson.toJson(it) }
    }

    // Convert JSON String back to List<String>
    @TypeConverter
    fun toStringList(json: String?): List<String>? {
        return json?.let {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        }
    }
}