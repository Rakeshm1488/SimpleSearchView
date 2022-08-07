package com.example.simplesearchview.apibase

import junit.framework.TestCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.junit.Test

class ApiHelperTest : TestCase(){

    @Test
    fun testApi(){
        val apiService = ApiHelper.getInstance().create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response =
                CoroutineScope(Dispatchers.IO).async { apiService.getAbbreviations("abc") }
            assertEquals(true, response.await() != null)
        }
    }

}