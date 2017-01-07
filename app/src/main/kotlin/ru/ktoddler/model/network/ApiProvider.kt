package ru.ktoddler.model.network

interface ApiProvider {
    fun getRoutingApi(baseUrl: String): RoutingApi
    fun getWeatherHistoryApi(baseUrl: String): WeatherHistoryApi
}