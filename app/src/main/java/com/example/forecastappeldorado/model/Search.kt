package com.example.forecastappeldorado.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_table")
data class Search(
    @ColumnInfo(name="city_name") val cityName: String?,
    @ColumnInfo(name="temperature") val temperature: String?
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
