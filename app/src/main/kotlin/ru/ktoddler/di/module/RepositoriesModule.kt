package ru.ktoddler.di.module

import dagger.Binds
import dagger.Module
import ru.ktoddler.di.scope.PerApplication
import ru.ktoddler.model.repository.LocalRepository
import ru.ktoddler.model.repository.LocalRepositoryImpl

@Module
abstract class RepositoriesModule {

    @Binds
    @PerApplication
    internal abstract fun provideLocalRepository(localRepository: LocalRepositoryImpl): LocalRepository
}