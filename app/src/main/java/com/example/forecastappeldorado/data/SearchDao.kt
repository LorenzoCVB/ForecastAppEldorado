package com.example.forecastappeldorado.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_table ORDER BY id ASC")
    fun getAll():List<Search>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(search: Search)
}