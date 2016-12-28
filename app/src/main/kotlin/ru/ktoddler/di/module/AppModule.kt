package ru.ktoddler.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.ktoddler.di.scope.PerApplication

@Module
class AppModule(context: Context) {

    private val context: Context

    init {
        this.context = context.applicationContext
    }

    @Provides
    @PerApplication
    internal fun provideAppContext(): Context {
        return context
    }
}