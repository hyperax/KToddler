package ru.ktoddler.model.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.ktoddler.model.network.entity.RouteResponse

interface RoutingApi {

    @GET("/maps/api/directions/json")
    fun getRoute(
            @Query(value = "origin") position: String,
            @Query(value = "destination") destination: String): Observable<RouteResponse>
}