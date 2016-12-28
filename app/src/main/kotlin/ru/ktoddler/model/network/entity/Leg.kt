package ru.ktoddler.model.network.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class Leg(val distance: Distance,
               val duration: Duration,
               @JsonProperty("end_location") val endLocation: EndLocation)
