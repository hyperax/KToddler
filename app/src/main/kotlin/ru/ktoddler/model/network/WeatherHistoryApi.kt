package ru.ktoddler.model.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import ru.ktoddler.model.network.entity.WeatherInfo

interface WeatherHistoryApi {

    @GET("/api/location/{location}/{year}/{month}/{day}/")
    fun getWeatherHistory(
            @Path("location") cityCode: String,
            @Path("year") year: String,
            @Path("month") month: String,
            @Path("day") day: String): Observable<Array<WeatherInfo>>
}