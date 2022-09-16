package com.example.forecastappeldorado

import android.app.Application
import com.example.forecastappeldorado.data.SearchDatabase
import com.example.forecastappeldorado.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
class App: Application(){

    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { SearchDatabase.getDatabase(this, applicationScope) }
    val searchRepository by lazy { SearchRepository(database.getSearchDao()) }


}