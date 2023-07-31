package com.Http.word_learning_application

import com.example.word_learning_application.WordResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WordAPI {
    @GET("word")
    fun getWordList(@Query("wordLevel")wordLevel:String):Call<ArrayList<WordResult>>
}