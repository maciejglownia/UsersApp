package com.glownia.maciej.usersapp.di

import com.glownia.maciej.usersapp.data.network.DailymotionApi
import com.glownia.maciej.usersapp.data.network.GithubApi
import com.glownia.maciej.usersapp.utils.Constants.Companion.BASE_URL_DAILYMOTION
import com.glownia.maciej.usersapp.utils.Constants.Companion.BASE_URL_GITHUB
import com.glownia.maciej.usersapp.utils.Dailymotion
import com.glownia.maciej.usersapp.utils.Github
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    @Github
    fun provideRetrofitInstanceGithub(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_GITHUB)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Dailymotion
    fun provideRetrofitInstanceDailymotion(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_DAILYMOTION)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiServiceGithub(@Github retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApiServiceDailymotion(@Dailymotion retrofit: Retrofit): DailymotionApi {
        return retrofit.create(DailymotionApi::class.java)
    }
}