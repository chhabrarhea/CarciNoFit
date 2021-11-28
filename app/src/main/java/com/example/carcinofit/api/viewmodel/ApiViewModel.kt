package com.example.carcinofit.api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carcinofit.api.repository.ApiRepository
import com.example.carcinofit.database.models.DummyApiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val myRepository: ApiRepository) :// hilt goes to the dependecy graph we created and automatically finds the one which is returning ApiRepository , in case of same return type conflict i.e. more than one dependency have same retrun type then use @Name() annotation
    ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            myRepository.getPost()// this do the network call and jaise hi response aega the live data will get notified and it will pass new data to all its observers automatically just see the implementation of the method we are calling you will understand everything
        }
    }

    val post: LiveData<DummyApiModel>
        get() = myRepository.post// this is the getter for above property 'post' i.e. whenever someone calls or uses 'post'  he/she will get 'myRepository.post' in return
}