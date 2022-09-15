package com.example.forecastappeldorado.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastappeldorado.model.SearchModel

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_table ORDER BY id ASC")
    fun getAll():LiveData<List<SearchModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(search: SearchModel)
}