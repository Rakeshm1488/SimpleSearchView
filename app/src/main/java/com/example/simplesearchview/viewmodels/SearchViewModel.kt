package com.example.simplesearchview.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplesearchview.Lfs
import com.example.simplesearchview.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchViewModel(val searchRepository: SearchRepository) : ViewModel() {

    val resultList: LiveData<List<Lfs>>
        get() = searchRepository.searchResults

    val errorLoading: LiveData<Boolean>
        get() = searchRepository.errorLoading

    var isLoading = MutableLiveData<Boolean>()

    fun getSearchResults(searchStr: String) {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val asyncJob = async { searchRepository.getSearchItems(searchStr) }
            asyncJob.await()
            isLoading.postValue(false)
        }
    }
}