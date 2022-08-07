package com.example.simplesearchview.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simplesearchview.Lfs
import com.example.simplesearchview.apibase.ApiService

class SearchRepository(private val apiService: ApiService) {

    private val searchLiveData = MutableLiveData<List<Lfs>>()

    val searchResults: LiveData<List<Lfs>>
        get() = searchLiveData

    suspend fun getSearchItems(searchString: String) {
        val result = apiService.getAbbreviations(searchString)

        if (result?.body() != null) {
            val rList = result.body()
            if (rList != null && rList.isNotEmpty()) {
                searchLiveData.postValue(rList[0].lfs)
            } else {
                searchLiveData.postValue(listOf())
            }
        } else {
            searchLiveData.postValue(listOf())
        }
    }
}