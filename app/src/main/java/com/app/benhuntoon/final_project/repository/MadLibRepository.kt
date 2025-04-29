package com.app.benhuntoon.final_project.repository

import com.app.benhuntoon.final_project.data.database.MadLibDao
import com.app.benhuntoon.final_project.data.database.MadLibEntity
import com.app.benhuntoon.final_project.network.WordApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MadLibRepository(
    private val wordApi: WordApi,
    private val madLibDao: MadLibDao
) {
    suspend fun validateWord(word: String) = withContext(Dispatchers.IO) {
        wordApi.validateWord(word)
    }

    suspend fun saveMadLib(madLib: MadLibEntity) = withContext(Dispatchers.IO) {
        madLibDao.insertMadLib(madLib)
    }

    suspend fun getAllMadLibs() = withContext(Dispatchers.IO) {
        madLibDao.getAllMadLibs()
    }

    suspend fun deleteMadLib(id: Int) = withContext(Dispatchers.IO) {
        madLibDao.deleteMadLib(id)
    }
}