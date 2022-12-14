package com.example.forecastappeldorado.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecastappeldorado.App
import com.example.forecastappeldorado.adapter.ListAdapter
import com.example.forecastappeldorado.databinding.ActivityHistoryBinding
import com.example.forecastappeldorado.viewmodel.SearchViewModel
import com.example.forecastappeldorado.viewmodel.SearchViewModelFactory
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    //private lateinit var searchViewModel: SearchViewModel

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(
            (this.applicationContext as App).searchRepository,
            this.applicationContext as App
        )
    }

    private val binding: ActivityHistoryBinding by lazy {
        ActivityHistoryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val searchAdapter = ListAdapter()
        recyclerView.adapter = searchAdapter

        //searchViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[SearchViewModel::class.java]
        searchViewModel.allSearches.observe(this) { list ->
            list?.let {
                searchAdapter.updateList(it)
            }
        }

    }
}