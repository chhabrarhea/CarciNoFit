package com.example.carcinofit.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carcinofit.data.remote.ApiServiceInterface
import com.example.carcinofit.database.models.DummyApiModel

class ApiRepository(private val apiService: ApiServiceInterface) {


    private var postLiveData = MutableLiveData<DummyApiModel>()

    val post: LiveData<DummyApiModel>
        get() = postLiveData


    suspend fun getPost() {

        val result = apiService.getPost()

        if (result?.body() != null) {
            postLiveData.postValue(result.body())
        }
    }
}