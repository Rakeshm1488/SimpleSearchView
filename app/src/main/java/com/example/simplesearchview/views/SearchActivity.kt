package com.example.simplesearchview.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.simplesearchview.R
import com.example.simplesearchview.adapter.SearchAdapter
import com.example.simplesearchview.databinding.ActivitySearchBinding
import com.example.simplesearchview.utils.CheckNetwork
import com.example.simplesearchview.viewmodels.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel
    lateinit var searchBinding: ActivitySearchBinding
    private val searchAdapter = SearchAdapter()

    private val queryListener = object : SearchView.OnQueryTextListener {
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
        if (CheckNetwork.isNetworkAvailable(this)) {
            searchViewModel.getSearchResults(sStr)
        } else {
            Toast.makeText(this, "Please check the internet", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        searchBinding.searchRecyclerView.adapter = searchAdapter
        searchBinding.searchView.setOnQueryTextListener(queryListener)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchBinding.searchViewModel = searchViewModel
        searchBinding.lifecycleOwner = this

        searchViewModel.resultList.observe(this, {
            searchAdapter.setSearchResult(it)
        })
    }
}