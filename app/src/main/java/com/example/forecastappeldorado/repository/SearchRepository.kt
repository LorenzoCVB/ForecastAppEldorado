package com.example.forecastappeldorado.repository

import androidx.lifecycle.LiveData
import com.example.forecastappeldorado.model.Search
import com.example.forecastappeldorado.data.SearchDao

class SearchRepository(private val searchDao: SearchDao) {
    val getAllSearch:LiveData<List<Search>> = searchDao.getAll()

    suspend fun insert(search: Search){
        searchDao.insert(search)
    }
}