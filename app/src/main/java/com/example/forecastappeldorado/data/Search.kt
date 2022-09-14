package com.example.forecastappeldorado.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_table")
data class Search(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name="city_name") val cityName: String?,
    @ColumnInfo(name="temperature") val temperature: String?
)
