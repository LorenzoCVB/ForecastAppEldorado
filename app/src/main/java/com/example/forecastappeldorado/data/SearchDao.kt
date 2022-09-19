package com.example.forecastappeldorado.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastappeldorado.model.SearchModel

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_table ORDER BY id DESC")
    fun getAll():LiveData<List<SearchModel>>

    @Query("DELETE FROM search_table WHERE id NOT IN (SELECT MIN(id) FROM search_table GROUP BY city_name, date, temperature)")
    suspend fun deleteDuplicates()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(search: SearchModel)
}