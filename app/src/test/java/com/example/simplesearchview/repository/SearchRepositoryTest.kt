package com.example.simplesearchview.repository

import android.util.Log
import com.example.simplesearchview.apibase.ApiHelper
import com.example.simplesearchview.apibase.ApiService
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchRepositoryTest {

    private lateinit var apiService: ApiService

    @Before
    fun setup(){
        apiService = ApiHelper.getInstance().create(ApiService::class.java)
    }

    @Test
    fun testApi(){
        runBlocking {
            val response = apiService.getAbbreviations("abc")
            assertEquals(true, response != null)
        }
    }
}