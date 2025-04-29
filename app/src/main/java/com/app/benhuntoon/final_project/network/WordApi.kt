package com.app.benhuntoon.final_project.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WordApi {
    @GET("your_word_validation_endpoint")
    suspend fun validateWord(@Query("word") word: String): Response<Boolean>
}