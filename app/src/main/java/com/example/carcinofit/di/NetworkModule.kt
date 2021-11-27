package com.example.carcinofit.di

import com.example.carcinofit.api.ApiServiceInterface
import com.example.carcinofit.api.repository.ApiRepository
import com.example.carcinofit.other.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAPIInstanceService(): Retrofit { // stays as long as app is alive cause i declared it 'Singleton'
        return Retrofit.Builder()
            .baseUrl(Constants.dummyURL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiServiceInterface {
        return retrofit.create(ApiServiceInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideApiRepository(
        apiService: ApiServiceInterface
    ): ApiRepository {
        return ApiRepository(apiService)
    }


}