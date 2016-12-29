package ru.ktoddler.model.network.entity

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Class describes the result of request to
 * https://developers.google.com/maps/documentation/directions/

 */
data class RouteResponse(val routes: List<Route>) {
    data class Route(val legs: List<Leg>)

    data class Leg(val distance: Distance,
                   val duration: Duration,
                   @JsonProperty("end_location") val endLocation: EndLocation)

    data class EndLocation(val lat: Double, val lng: Double)

    data class Distance(val text: String, val value: Int)

    data class Duration(val text: String, val value: Int)
}