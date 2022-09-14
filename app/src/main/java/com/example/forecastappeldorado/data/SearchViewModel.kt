package com.example.forecastappeldorado.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {
    val getAllSearch:LiveData<List<Search>>
    private val searchRepository: SearchRepository

    init {
        val dao = SearchDatabase.getDatabase(application).getSearchDao()
        searchRepository = SearchRepository(dao)
        getAllSearch = searchRepository.getAllSearch
    }

    fun searchInsert(search: Search) = viewModelScope.launch {
        searchRepository.insert(search)
    }
}