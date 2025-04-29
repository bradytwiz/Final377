package com.app.benhuntoon.final_project.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MadLibDao {
    @Insert
    fun insertMadLib(madLib: MadLibEntity): Long

    @Query("SELECT * FROM madlibs")
    suspend fun getAllMadLibs(): List<MadLibEntity>

    @Query("DELETE FROM madlibs WHERE id = :id")
    suspend fun deleteMadLib(id: Int)
}