package com.app.benhuntoon.final_project.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WordApiService {
    @GET("api/v2/entries/en/{word}")
    suspend fun validateWord(@Path("word") word: String): Response<WordValidationResponse>
}