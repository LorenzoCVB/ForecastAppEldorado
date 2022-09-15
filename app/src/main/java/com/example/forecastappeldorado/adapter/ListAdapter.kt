package com.example.forecastappeldorado.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastappeldorado.R
import com.example.forecastappeldorado.model.Search
import kotlinx.android.synthetic.main.list_history.view.*


class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var searchList = ArrayList<Search>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_history, parent, false))
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = searchList[position]
        holder.itemView.list_cityName.text = currentItem.cityName
        holder.itemView.list_cityTemperature.text = currentItem.temperature
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(myList: List<Search>) {
        searchList.clear()
        searchList.addAll(myList)
        notifyDataSetChanged()
    }
}