package com.example.forecastappeldorado.data

import androidx.lifecycle.LiveData

class SearchRepository(private val searchDao: SearchDao) {
    val getAllSearch:LiveData<List<Search>> = searchDao.getAll()

    suspend fun insert(search: Search){
        searchDao.insert(search)
    }
}