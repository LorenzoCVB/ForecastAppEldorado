package com.example.forecastappeldorado.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.forecastappeldorado.model.Search
import com.example.forecastappeldorado.data.SearchDatabase
import com.example.forecastappeldorado.repository.SearchRepository
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