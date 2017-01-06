package ru.ktoddler.presenter

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.ktoddler.di.scope.PerApplication
import ru.ktoddler.model.entity.KToddlerEntity
import ru.ktoddler.model.network.ApiProvider
import ru.ktoddler.model.preference.UiPrefs
import ru.ktoddler.model.repository.LocalRepository
import ru.ktoddler.view.KToddlerView
import ru.ktoddler.view.model.KToddlerVM
import javax.inject.Inject

@PerApplication
class KToddlerPresenter
@Inject
constructor(context: Context,
            private val uiPrefs: UiPrefs,
            private val localRepo: LocalRepository,
            private val networkRepo: ApiProvider) : BasePresenter<KToddlerView>(context) {

    private val API_URL = "https://maps.googleapis.com"
    private var routeRequestInProgress = false

    private val WEATHER_API_URL = "http://www.metaweather.com"
    private var weatherRequestInProgress = false

    private val SPbCityCode: String = "2123260";

    init {
        if (uiPrefs.firstRun()) {
            val toddler = KToddlerEntity()
            toddler.id = 1
            toddler.name = "Toddler"
            toddler.age = 2

            localRepo.put(toddler)
        }
    }

    override fun bindView(view: KToddlerView) {
        super.bindView(view)
        showToddler()
        showWelcome()
    }

    private fun showWelcome() {
        if (uiPrefs.firstRun()) {
            showConfirm("Ура! Первый запуск приложения!")
            uiPrefs.setFirstRun(false)
        }
    }

    private fun showToddler() {
        val t = localRepo[KToddlerEntity::class.java][0]

        getView().ifPresent {
            it.showToddler(KToddlerVM(t.id, t.name, t.age))
        }
    }

    fun loadWeatherHistory(year: Int, month: Int, day: Int) {
        if (!weatherRequestInProgress) {
            weatherRequestInProgress = true
            networkRepo.getWeatherHistoryApi(WEATHER_API_URL).getWeatherHistory(SPbCityCode,
                    year.toString(), month.inc().toString(), day.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate { weatherRequestInProgress = false }
                    .subscribe({ weatherResponse ->
                        val msg : String = if (weatherResponse.isEmpty()) "no weather data"
                        else weatherResponse.get(0).temp.toString();
                        getView()
                                .ifPresent { v -> v.showInfo(msg) }
                    }
                    ) { throwable -> this@KToddlerPresenter.handleError(throwable) }
        }
    }

    fun loadDistance() {
        if (!routeRequestInProgress) {
            routeRequestInProgress = true
            networkRepo.getRoutingApi(API_URL).getRoute("Saint-Petersburg", "Praga")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate { routeRequestInProgress = false }
                    .subscribe({ routeResponse ->
                        val msg : String = if (routeResponse.routes.isEmpty()) "no routing data, maybe need api key?"
                            else routeResponse.routes[0].legs[0].distance.text;
                        getView()
                                .ifPresent { v -> v.showInfo(msg) }
                    }
                    ) { throwable -> this@KToddlerPresenter.handleError(throwable) }
        }
    }
}
