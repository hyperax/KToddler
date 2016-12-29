package ru.ktoddler.model.network

import io.reactivex.Observable
import retrofit2.http.Query
import ru.ktoddler.model.network.entity.RouteResponse
import javax.inject.Inject

class StubRoutingApi
@Inject
constructor()
    : RoutingApi {

    override fun getRoute(@Query(value = "origin") position: String,
                          @Query(value = "destination") destination: String): Observable<RouteResponse> {

        return Observable.just(RouteResponse(listOf(RouteResponse.Route(listOf(RouteResponse.Leg(
                RouteResponse.Distance("very far from here!", 999999),
                RouteResponse.Duration("very long!", 11111111),
                RouteResponse.EndLocation(0.0, 0.0)))))))
    }
}