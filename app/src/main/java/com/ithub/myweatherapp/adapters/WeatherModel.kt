package com.ithub.myweatherapp.adapters

data class WeatherModel(
    val cityName: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imageURL: String,
    val hours: String
)
