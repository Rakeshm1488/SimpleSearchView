package com.example.simplesearchview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesearchview.model.Lfs
import com.example.simplesearchview.databinding.SearchItemBinding

/* This is a adapter class for recycler view to show the results*/
class SearchAdapter : RecyclerView.Adapter<MainViewHolder>() {
    private var searchList = listOf<Lfs>()
    fun setSearchResult(searchList: List<Lfs>) {
        this.searchList = searchList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.lfsItem = searchList[position]
    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}

class MainViewHolder(val binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root) {
}