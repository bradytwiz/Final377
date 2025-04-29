package com.app.benhuntoon.final_project.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "madlibs")
data class MadLibEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val story: String,
    val filledWords: String,
    val timestamp: Long = System.currentTimeMillis()
)