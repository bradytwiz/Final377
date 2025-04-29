package com.app.benhuntoon.final_project.network

import com.app.benhuntoon.final_project.data.api.WordValidationResponse
import retrofit2.Response

class WordApi(private val wordApiService: WordApi) {
    suspend fun validateWord(word: String): Response<WordValidationResponse> {
        return wordApiService.validateWord(word)
    }
}