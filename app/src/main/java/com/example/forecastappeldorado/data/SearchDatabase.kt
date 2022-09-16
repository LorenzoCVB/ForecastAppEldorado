package com.example.forecastappeldorado.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forecastappeldorado.model.SearchModel
import kotlinx.coroutines.CoroutineScope

@Database(entities = [SearchModel::class], version = 1)
abstract class SearchDatabase : RoomDatabase(){

    abstract fun getSearchDao() : SearchDao

    companion object {

        @Volatile
        private var INSTANCE : SearchDatabase? = null

        fun getDatabase(context: Context, applicationScope: CoroutineScope): SearchDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SearchDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}