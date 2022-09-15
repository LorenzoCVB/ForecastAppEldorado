package com.example.forecastappeldorado.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.forecastappeldorado.model.SearchModel
import com.example.forecastappeldorado.data.SearchDatabase
import com.example.forecastappeldorado.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {
    val getAllSearch:LiveData<List<SearchModel>>
    private val searchRepository: SearchRepository

    init {
        val dao = SearchDatabase.getDatabase(application).getSearchDao()
        searchRepository = SearchRepository(dao)
        getAllSearch = searchRepository.getAllSearch
    }

    fun searchInsert(search: SearchModel) = viewModelScope.launch {
        searchRepository.insert(search)
    }
}