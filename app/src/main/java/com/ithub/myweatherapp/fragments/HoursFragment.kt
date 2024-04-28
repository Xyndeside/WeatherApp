package com.ithub.myweatherapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ithub.myweatherapp.MainViewModel
import com.ithub.myweatherapp.R
import com.ithub.myweatherapp.adapters.WeatherAdapter
import com.ithub.myweatherapp.adapters.WeatherModel
import com.ithub.myweatherapp.databinding.FragmentHoursBinding
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject

class HoursFragment : Fragment() {

    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()

    }

    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter(null)
        rcView.adapter = adapter

        initHoursRv()
    }

    private fun initHoursRv() {
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            adapter.submitList(getHoursList(it))
        }
    }

    private fun getHoursList(wItem: WeatherModel) : List<WeatherModel> {
        val result = ArrayList<WeatherModel>()
        val hoursArray = JSONArray(wItem.hours)

        for (i in 0 until hoursArray.length()) {
            val cityName = wItem.cityName
            val date = (hoursArray[i] as JSONObject).getString("time")
            val splitDate: List<String> = date.split(" ")
            val time: String = splitDate[splitDate.lastIndex]
            val condition = (hoursArray[i] as JSONObject).getJSONObject("condition").getString("text")
            val temp_c = "${(hoursArray[i] as JSONObject).getString("temp_c")}ºC"
            val maxTemp = ""
            val minTemp = ""
            val imageUrl = (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon")
            val hours = ""

            val item = WeatherModel (
                cityName, time, condition, temp_c, maxTemp, minTemp, imageUrl, hours
            )

            result.add(item)
        }

        return result
    }

    companion object {

        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}