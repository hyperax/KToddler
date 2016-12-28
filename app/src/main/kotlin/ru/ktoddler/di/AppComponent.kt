package ru.ktoddler.di

import dagger.Component
import ru.ktoddler.KToddlerApp
import ru.ktoddler.di.module.AppModule
import ru.ktoddler.di.scope.PerApplication

@PerApplication
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(toddlerApp: KToddlerApp)
}