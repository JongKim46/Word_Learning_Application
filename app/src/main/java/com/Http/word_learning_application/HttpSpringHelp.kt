package com.Http.word_learning_application

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpSpringHelp  {
    private const val baseUrl:String = "http://10.0.2.2:8080/wordAPI/"
    private val gson = GsonBuilder()
        .setLenient()
        .create()
    private val getRetrofit by lazy{
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clientBuilder.build())
            .build()
    }


    private val clientBuilder = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().apply {
            // bodyログを出力するためのinterceptor
            level = HttpLoggingInterceptor.Level.BODY
        }
    )

    fun getRetrofit(): Retrofit {
        return getRetrofit
    }

}