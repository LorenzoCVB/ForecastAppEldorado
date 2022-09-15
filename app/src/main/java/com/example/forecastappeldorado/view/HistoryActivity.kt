package com.example.forecastappeldorado.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecastappeldorado.R
import com.example.forecastappeldorado.adapter.ListAdapter
import com.example.forecastappeldorado.data.SearchViewModel
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val searchAdapter = ListAdapter()
        recyclerView.adapter = searchAdapter

        searchViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[SearchViewModel::class.java]
        searchViewModel.getAllSearch.observe(this) { list ->
            list?.let {
                searchAdapter.updateList(it)
            }
        }

    }
}