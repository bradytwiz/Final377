package com.app.benhuntoon.final_project.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WordApi {
    @GET("api/v2/entries/en/{word}")
    suspend fun validateWord(@Path("word") word: String): Response<Any>
}