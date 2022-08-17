package com.example.simplesearchview.repository

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simplesearchview.model.Lfs
import com.example.simplesearchview.model.SearchResponse
import com.example.simplesearchview.apibase.ApiService
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* Repository class Implemented to do data related calls */
class SearchRepository(private val apiService: ApiService) {

    private val searchLiveData = MutableLiveData<List<Lfs>>()
    private val errorMsgVal = MutableLiveData<String>()
    private val errorMsgVisibilityVal = MutableLiveData<Int>(View.GONE)
    private val dataLoading = MutableLiveData<Int>(View.GONE)

    val searchResults: LiveData<List<Lfs>>
        get() = searchLiveData

    val showErrorMsg: LiveData<String>
        get() = errorMsgVal

    val errorMsgVisibility: LiveData<Int>
        get() = errorMsgVisibilityVal

    val isDataLoading: LiveData<Int>
        get() = dataLoading

    /* API call to fetch search results */
    suspend fun getSearchItems(searchString: String) {
        try {
            dataLoading.postValue(View.VISIBLE)
            delay(1000) // added just to show the progress bar prominently
            val response = apiService.getAbbreviations(searchString)

            response.enqueue(object : Callback<List<SearchResponse>> {
                override fun onResponse(
                    call: Call<List<SearchResponse>>,
                    response: Response<List<SearchResponse>>
                ) {
                    if (response.body() != null) {
                        val rList = response.body()
                        if (rList != null && rList.isNotEmpty()) {
                            setMsgDetails("")
                            searchLiveData.postValue(rList[0].lfs)
                        } else {
                            setMsgDetails("No Data Found")
                            searchLiveData.postValue(listOf())
                        }
                    } else {
                        setMsgDetails("No Data Found")
                        searchLiveData.postValue(listOf())
                    }
                }

                override fun onFailure(call: Call<List<SearchResponse>>, t: Throwable) {
                    Log.d("SearchView", "Request failed... ${t.message}")
                    setMsgDetails("Somthing went wrong")
                    dataLoading.postValue(View.GONE)
                }

            })
        } catch (e: Exception){
            setMsgDetails("Somthing went wrong")
            dataLoading.postValue(View.GONE)
            e.printStackTrace()
        } finally {
            dataLoading.postValue(View.GONE)
        }
    }

    private fun setMsgDetails(msg: String){
        if(msg.isNotEmpty()){
            errorMsgVisibilityVal.postValue(View.VISIBLE)
            errorMsgVal.postValue(msg)
            searchLiveData.postValue(listOf())
        } else {
            errorMsgVisibilityVal.postValue(View.GONE)
        }
    }
}