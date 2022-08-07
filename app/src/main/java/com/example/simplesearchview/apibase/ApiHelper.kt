package com.example.simplesearchview.apibase

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {
    private const val baseURL = "http://www.nactem.ac.uk/software/acromine/"

    private val logging : HttpLoggingInterceptor = HttpLoggingInterceptor()
    private val httpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

    fun getInstance(): Retrofit{

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient: OkHttpClient = httpBuilder.addInterceptor(logging).build()

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}