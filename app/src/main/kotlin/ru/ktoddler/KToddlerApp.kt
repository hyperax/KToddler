package ru.ktoddler

import android.support.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import ru.ktoddler.di.AppComponent
import ru.ktoddler.di.DaggerAppComponent
import ru.ktoddler.di.module.AppModule

class KToddlerApp : MultiDexApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        buildComponent()

        appComponent.inject(this)

        initAppInspection()
    }

    private fun initAppInspection() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    protected fun buildComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
