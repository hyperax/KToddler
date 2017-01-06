package ru.ktoddler.model.network.entity

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Class describes the result of request to
 * https://www.metaweather.com/api/

 */
data class WeatherInfo(@JsonProperty("wind_speed") val windSpeed: Float,
                       @JsonProperty("the_temp") val temp: Float)