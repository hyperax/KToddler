package ru.ktoddler.di.module

import android.database.sqlite.SQLiteDatabase
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import ru.ktoddler.di.scope.PerApplication
import ru.ktoddler.model.jackson.JacksonConfigurator
import ru.ktoddler.model.storage.ToddlerSQLiteOpenHelper

@Module
class DataModule {

    @Provides
    @PerApplication
    internal fun provideMapper(): ObjectMapper {
        return JacksonConfigurator()
                .buildMapper()
                .registerModule(KotlinModule())
    }

    @Provides
    @PerApplication
    internal fun provideDatabase(sqLiteOpenHelper: ToddlerSQLiteOpenHelper): SQLiteDatabase {
        return sqLiteOpenHelper.writableDatabase
    }
}