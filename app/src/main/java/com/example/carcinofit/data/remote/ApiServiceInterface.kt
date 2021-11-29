package com.example.carcinofit.data.remote

import com.example.carcinofit.database.models.DummyApiModel
import retrofit2.Response
import retrofit2.http.GET


interface ApiServiceInterface {
    @GET("/posts/4")
    suspend fun getPost(): Response<DummyApiModel>
}