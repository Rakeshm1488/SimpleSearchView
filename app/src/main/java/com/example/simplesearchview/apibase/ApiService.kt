package com.example.simplesearchview.apibase

import com.example.simplesearchview.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("dictionary.py")
    fun getAbbreviations(@Query("sf") searchStr: String): Call<List<SearchResponse>>
}