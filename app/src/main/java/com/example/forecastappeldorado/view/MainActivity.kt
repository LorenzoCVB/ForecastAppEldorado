package com.example.forecastappeldorado.view


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.forecastappeldorado.App
import com.example.forecastappeldorado.databinding.ActivityMainBinding
import com.example.forecastappeldorado.model.SearchModel
import com.example.forecastappeldorado.viewmodel.MainViewModel
import com.example.forecastappeldorado.viewmodel.MainViewModelFactory
import com.example.forecastappeldorado.viewmodel.SearchViewModel
import com.example.forecastappeldorado.viewmodel.SearchViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(
            (this.applicationContext as App).searchRepository,
            this.applicationContext as App
        )
    }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (this.applicationContext as App).mainRepository,
            this.applicationContext as App
        )
    }

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        val cName = GET.getString("cityName", "moscow")?.lowercase()
        getLiveDataAndAddToDB()

        with(binding) {
            if (cName != null) {
                edtCityName.setText(cName)
                viewModel.getDataFromAPI(cName)
                swipeRefreshLayout.setOnRefreshListener {
                    llData.visibility = View.GONE
                    tvError.visibility = View.GONE
                    pbLoading.visibility = View.GONE

                    val cityName = GET.getString("cityName", cName)?.lowercase()
                    edtCityName.setText(cityName)
                    viewModel.getDataFromAPI(cName)
                    swipeRefreshLayout.isRefreshing = false
                }
            }

            imgSearchCity.setOnClickListener {
                val cityName = edtCityName.text.toString()
                edtCityName.onEditorAction(EditorInfo.IME_ACTION_DONE)
                SET.putString("cityName", cityName)
                SET.apply()
                viewModel.getDataFromAPI(cityName)
                Log.i(TAG, "onCreate: " + cityName)
            }

            imgHistory.setOnClickListener {
                val intent = Intent(applicationContext, HistoryActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun getLiveDataAndAddToDB() {

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())

        viewModel.weatherData.observe(this, Observer { data ->
            data?.let {
                with(binding) {
                    llData.visibility = View.VISIBLE

                    tvCityCode.text = data.sys.country.toString()
                    tvCityName.text = data.name.toString()

                    Glide.with(applicationContext)
                        .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                        .into(imgWeatherPictures)

                    //atribuindo os valores das respostas
                    tvDegree.text = data.main.temp.toString() + "°C"
                    tvHumidity.text = data.main.humidity.toString() + "%"
                    tvWindSpeed.text = data.wind.speed.toString()
                    tvLat.text = data.coord.lat.toString()
                    tvLon.text = data.coord.lon.toString()
                }

                val search =
                    SearchModel(data.name, data.main.temp.toString() + "°C", currentDate)

                //inserindo no DB mais de uma vez
                searchViewModel.searchInsert(search)
                searchViewModel.deleteDuplicates()
            }
        })
    }
}