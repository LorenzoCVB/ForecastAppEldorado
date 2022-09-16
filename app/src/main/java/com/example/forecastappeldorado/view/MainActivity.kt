package com.example.forecastappeldorado.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.forecastappeldorado.R
import com.example.forecastappeldorado.model.SearchModel
import com.example.forecastappeldorado.data.SearchDatabase
import com.example.forecastappeldorado.viewmodel.SearchViewModel
import com.example.forecastappeldorado.databinding.ActivityMainBinding
import com.example.forecastappeldorado.viewmodel.MainViewModel


import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: MainViewModel

    private lateinit var appDB : SearchDatabase
    lateinit var binding : ActivityMainBinding

    private lateinit var mSearchViewModel: SearchViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
        val formatedDate = formatter.format(date)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewmodel = androidx.lifecycle.ViewModelProviders.of(this).get(MainViewModel::class.java)

        mSearchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        var cName = GET.getString("cityName", "moscow")?.toLowerCase()
        edt_city_name.setText(cName)
        viewmodel.refreshData(cName!!)
        getLiveDataAndAddToDB()

        swipe_refresh_layout.setOnRefreshListener {
            ll_data.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            var cityName = GET.getString("cityName", cName)?.toLowerCase()
            edt_city_name.setText(cityName)
            viewmodel.refreshData(cityName!!)
            swipe_refresh_layout.isRefreshing = false
        }

        img_search_city.setOnClickListener {
            val cityName = edt_city_name.text.toString()
            edt_city_name.onEditorAction(EditorInfo.IME_ACTION_DONE)
            SET.putString("cityName", cityName)
            SET.apply()
            viewmodel.refreshData(cityName)
            getLiveDataAndAddToDB()
            Log.i(TAG, "onCreate: " + cityName)
        }


        img_history.setOnClickListener {
            val intent = Intent(applicationContext, HistoryActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getLiveDataAndAddToDB() {

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())


        viewmodel.weather_data.observe(this, Observer { data ->
            data?.let {
                ll_data.visibility = View.VISIBLE

                tv_city_code.text = data.sys.country.toString()
                tv_city_name.text = data.name.toString()

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures)
                
                //atribuindo os valores das respostas
                tv_degree.text = data.main.temp.toString() + "°C"
                tv_humidity.text = data.main.humidity.toString() + "%"
                tv_wind_speed.text = data.wind.speed.toString()
                tv_lat.text = data.coord.lat.toString()
                tv_lon.text = data.coord.lon.toString()

                val search = SearchModel(data.name.toString(), data.main.temp.toString() + "°C", currentDate)
                mSearchViewModel.searchInsert(search)

            }
        })


    }
}