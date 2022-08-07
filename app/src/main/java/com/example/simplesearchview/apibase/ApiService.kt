package com.example.simplesearchview.apibase

import com.example.simplesearchview.Lfs
import com.example.simplesearchview.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("dictionary.py")
    suspend fun getAbbreviations(@Query("sf") searchStr: String): Response<List<SearchResponse>>
}