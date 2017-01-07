package ru.ktoddler.model.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import ru.ktoddler.di.scope.PerApplication
import javax.inject.Inject

@PerApplication
class ApiImpl
@Inject
constructor(private val client: OkHttpClient,
            private val converter: Converter.Factory,
            private val stubRoutingApi: StubRoutingApi) : ApiProvider {

    override fun getWeatherHistoryApi(baseUrl: String): WeatherHistoryApi {
        /*if (BuildConfig.DEBUG) {
            return stubRoutingApi;
        }*/

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converter)
                .build()
                .create(WeatherHistoryApi::class.java)
    }

    override fun getRoutingApi(baseUrl: String): RoutingApi {
        /*if (BuildConfig.DEBUG) {
            return stubRoutingApi;
        }*/

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converter)
                .build()
                .create(RoutingApi::class.java)
    }
}