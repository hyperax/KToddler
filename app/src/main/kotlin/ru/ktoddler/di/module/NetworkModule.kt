package ru.ktoddler.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.converter.jackson.JacksonConverterFactory
import ru.ktoddler.BuildConfig
import ru.ktoddler.di.scope.PerApplication
import java.util.concurrent.TimeUnit



@Module
class NetworkModule {

    companion object {
        private val CONNECT_TIMEOUT_SECONDS: Long = 5
        private val READ_TIMEOUT_SECONDS: Long = 10
        private val WRITE_TIMEOUT_SECONDS: Long = 10
    }

    @Provides
    @PerApplication
    internal fun provideClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))

            builder.addNetworkInterceptor(StethoInterceptor())
        }

        return builder.build()
    }

    @Provides
    @PerApplication
    internal fun provideJacksonFactory(mapper: ObjectMapper): Converter.Factory {
        return JacksonConverterFactory.create(mapper)
    }

}