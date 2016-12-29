package ru.ktoddler.di

import dagger.Component
import ru.ktoddler.KToddlerApp
import ru.ktoddler.di.module.*
import ru.ktoddler.di.scope.PerApplication
import ru.ktoddler.view.MainActivity

@PerApplication
@Component(modules = arrayOf(ApiModule::class, AppModule::class, DataModule::class,
        NetworkModule::class, RepositoriesModule::class, NotificationsModule::class))
interface AppComponent {

    fun inject(toddlerApp: KToddlerApp)

    fun inject(mainActivity: MainActivity)

}