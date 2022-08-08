package com.example.simplesearchview.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.simplesearchview.R
import com.example.simplesearchview.adapter.SearchAdapter
import com.example.simplesearchview.apibase.ApiHelper
import com.example.simplesearchview.apibase.ApiService
import com.example.simplesearchview.databinding.ActivitySearchBinding
import com.example.simplesearchview.repository.SearchRepository
import com.example.simplesearchview.utils.CheckNetwork
import com.example.simplesearchview.viewmodels.SearchViewModel
import com.example.simplesearchview.viewmodels.SearchViewModelFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel
    lateinit var searchBinding: ActivitySearchBinding
    val searchAdapter = SearchAdapter()

    private val queryListener = object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let { getResult(it) }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let { getResult(it) }
            return true
        }

    }

    private fun getResult(sStr: String) {
        if(CheckNetwork.isNetworkAvailable(this) && sStr.length >= 3) {
            searchViewModel.getSearchResults(sStr)
        } else {
            searchAdapter?.setSearchResult(listOf())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        searchBinding.searchRecyclerView.adapter = searchAdapter
        searchBinding.searchView.setOnQueryTextListener(queryListener)

        val apiService = ApiHelper.getInstance().create(ApiService::class.java)
        val repository = SearchRepository(apiService)

        searchViewModel = ViewModelProvider(this, SearchViewModelFactory(repository)).get(SearchViewModel::class.java)

        searchViewModel.resultList.observe(this, {
            searchAdapter.setSearchResult(it)
        })

        searchViewModel.isLoading.observe(this, {
            it?.let {
                searchBinding.searchProgressBar.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        searchViewModel.errorLoading.observe(this, {
            if(it){
                Toast.makeText(this@SearchActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }
}