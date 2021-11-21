package com.chuify.cleanxoomclient.data.remote.di


import com.chuify.cleanxoomclient.data.prefrences.SharedPrefs
import com.chuify.cleanxoomclient.data.remote.network.ApiInterface
import com.chuify.cleanxoomclient.data.remote.network.RequestAuthenticationInterceptor
import com.chuify.cleanxoomclient.data.remote.network.RequestUserIdInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Network {

    private const val BASE_URL = "https://staging-xoomgas-api.herokuapp.com/api/v1/"

    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder().apply {
            addConverterFactory(ScalarsConverterFactory.create())
            addConverterFactory(GsonConverterFactory.create(gson))
            client(okHttp)
            baseUrl(BASE_URL)
        }.build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        requestInterceptor: RequestAuthenticationInterceptor,
        requestUserIdInterceptor: RequestUserIdInterceptor,
        logger: HttpLoggingInterceptor,
    ): OkHttpClient {


        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(requestInterceptor)
            addInterceptor(requestUserIdInterceptor)
            addInterceptor(logger)
        }.build()
    }


    @Singleton
    @Provides
    fun provideLoggerInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideRequestAuthenticationInterceptor(prefs: SharedPrefs): RequestAuthenticationInterceptor {
        return RequestAuthenticationInterceptor(prefs)
    }

    @Singleton
    @Provides
    fun provideRequestUserIdInterceptor(prefs: SharedPrefs): RequestUserIdInterceptor {
        return RequestUserIdInterceptor(prefs)
    }

    @Singleton
    @Provides
    fun provideClient(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}