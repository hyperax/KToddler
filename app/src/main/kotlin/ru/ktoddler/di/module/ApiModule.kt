package ru.ktoddler.di.module

import dagger.Binds
import dagger.Module
import ru.ktoddler.di.scope.PerApplication
import ru.ktoddler.model.network.ApiProvider
import ru.ktoddler.model.network.RoutingApiImpl

@Module
abstract class ApiModule {

    @Binds
    @PerApplication
    internal abstract fun bindNetworkApi(networkApiImpl: RoutingApiImpl): ApiProvider

}