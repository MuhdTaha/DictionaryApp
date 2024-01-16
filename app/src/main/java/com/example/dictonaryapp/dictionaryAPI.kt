package com.example.dictonaryapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface dictionaryAPI {
    @GET("en/{word}")
    suspend fun getMeaning(@Path("word") word : String) : Response<List<wordResult>>
}