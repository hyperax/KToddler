package ru.ktoddler.di

import dagger.Component
import ru.ktoddler.KToddlerApp
import ru.ktoddler.di.module.*
import ru.ktoddler.di.scope.PerApplication
import ru.ktoddler.view.MainActivity
import ru.ktoddler.view.activity.BaseActivity
import ru.ktoddler.view.fragment.BaseFragment

@PerApplication
@Component(modules = arrayOf(ApiModule::class, AppModule::class, DataModule::class,
        NetworkModule::class, RepositoriesModule::class, NotificationsModule::class))
interface AppComponent {

    fun inject(toddlerApp: KToddlerApp)

    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(baseFragment: BaseFragment)

}