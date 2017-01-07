package ru.ktoddler.di.module

import dagger.Binds
import dagger.Module
import ru.ktoddler.di.scope.PerApplication
import ru.ktoddler.model.network.ApiImpl
import ru.ktoddler.model.network.ApiProvider

@Module
abstract class ApiModule {

    @Binds
    @PerApplication
    internal abstract fun bindNetworkApi(networkApiImpl: ApiImpl): ApiProvider

}