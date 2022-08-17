package com.example.simplesearchview.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplesearchview.model.Lfs
import com.example.simplesearchview.apibase.ApiHelper
import com.example.simplesearchview.apibase.ApiService
import com.example.simplesearchview.repository.SearchRepository
import kotlinx.coroutines.*

/* ViewModel Implementation for Search Activity*/
class SearchViewModel() : ViewModel() {

    private var apiService: ApiService
    private var searchRepository: SearchRepository

    init {
        apiService = ApiHelper.getInstance().create(ApiService::class.java)
        searchRepository = SearchRepository(apiService)
    }

    /* LiveData to observe the result */
    val resultList: MutableLiveData<List<Lfs>>
        get() = searchRepository.searchResults as MutableLiveData<List<Lfs>>

    /* LiveData to observe error message */
    val errorMsg: MutableLiveData<String>
        get() = searchRepository.showErrorMsg as MutableLiveData<String>

    /* LiveData to observe error and update the view visibility */
    val errorMsgVisibility: MutableLiveData<Int>
        get() = searchRepository.errorMsgVisibility as MutableLiveData<Int>

    /* LiveData to observe API call and show the progress bar */
    val isLoading: LiveData<Int>
        get() = searchRepository.isDataLoading

    /* This function creates coroutine and invoke repository class API to fetch results */
    fun getSearchResults(searchStr: String) {
        if(searchStr.length >= 3) {
            viewModelScope.launch(Dispatchers.IO) {
                searchRepository.getSearchItems(searchStr)
            }
        } else {
            errorMsgVisibility.postValue(View.VISIBLE)
            errorMsg.postValue("Please enter atleast 3 characters")
            resultList.postValue(listOf())
        }
    }
}