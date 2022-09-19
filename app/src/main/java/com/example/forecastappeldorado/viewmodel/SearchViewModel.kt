package com.example.forecastappeldorado.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.forecastappeldorado.App
import com.example.forecastappeldorado.model.SearchModel
import com.example.forecastappeldorado.data.SearchDatabase
import com.example.forecastappeldorado.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class SearchViewModel(private val searchRepository: SearchRepository, application: Application): AndroidViewModel(application) {
    val allSearches = searchRepository.getAllSearch

    fun searchInsert(search: SearchModel) = viewModelScope.launch {
        searchRepository.insert(search)
    }

    fun deleteDuplicates() = viewModelScope.launch {
        searchRepository.deleteDuplicates()
    }
}

class SearchViewModelFactory(
    private val repository: SearchRepository,
    private val app: Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository, app) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}