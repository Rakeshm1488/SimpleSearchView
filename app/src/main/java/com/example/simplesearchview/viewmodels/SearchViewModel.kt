package com.example.simplesearchview.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplesearchview.Lfs
import com.example.simplesearchview.apibase.ApiHelper
import com.example.simplesearchview.apibase.ApiService
import com.example.simplesearchview.repository.SearchRepository
import kotlinx.coroutines.*

class SearchViewModel() : ViewModel() {

    private var apiService: ApiService
    private var searchRepository: SearchRepository

    init {
        apiService = ApiHelper.getInstance().create(ApiService::class.java)
        searchRepository = SearchRepository(apiService)
    }

    val resultList: MutableLiveData<List<Lfs>>
        get() = searchRepository.searchResults as MutableLiveData<List<Lfs>>

    val errorMsg: MutableLiveData<String>
        get() = searchRepository.showErrorMsg as MutableLiveData<String>

    val errorMsgVisibility: MutableLiveData<Int>
        get() = searchRepository.errorMsgVisibility as MutableLiveData<Int>

    val isLoading: LiveData<Int>
        get() = searchRepository.isDataLoading

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