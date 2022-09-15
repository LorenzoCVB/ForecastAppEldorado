package com.example.forecastappeldorado.repository

import androidx.lifecycle.LiveData
import com.example.forecastappeldorado.model.SearchModel
import com.example.forecastappeldorado.data.SearchDao

class SearchRepository(private val searchDao: SearchDao) {
    val getAllSearch:LiveData<List<SearchModel>> = searchDao.getAll()

    suspend fun insert(search: SearchModel){
        searchDao.insert(search)
    }
}