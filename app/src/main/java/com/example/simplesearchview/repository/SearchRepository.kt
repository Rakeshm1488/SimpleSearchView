package com.example.simplesearchview.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simplesearchview.Lfs
import com.example.simplesearchview.SearchResponse
import com.example.simplesearchview.apibase.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository(private val apiService: ApiService) {

    private val searchLiveData = MutableLiveData<List<Lfs>>()
    private val isError = MutableLiveData<Boolean>()

    val searchResults: LiveData<List<Lfs>>
        get() = searchLiveData

    val errorLoading: LiveData<Boolean>
        get() = isError

    suspend fun getSearchItems(searchString: String) {
        try {
            val response = apiService.getAbbreviations(searchString)

            response.enqueue(object : Callback<List<SearchResponse>> {
                override fun onResponse(
                    call: Call<List<SearchResponse>>,
                    response: Response<List<SearchResponse>>
                ) {
                    if (response?.body() != null) {
                        val rList = response.body()
                        if (rList != null && rList.isNotEmpty()) {
                            searchLiveData.postValue(rList[0].lfs)
                        } else {
                            searchLiveData.postValue(listOf())
                        }
                    } else {
                        searchLiveData.postValue(listOf())
                    }
                }

                override fun onFailure(call: Call<List<SearchResponse>>, t: Throwable) {
                    isError.postValue(true)
                }

            })
        } catch (e: Exception){
            isError.postValue(true)
            e.printStackTrace()
        }
    }
}