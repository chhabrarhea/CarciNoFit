package com.example.carcinofit.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carcinofit.api.ApiServiceInterface
import com.example.carcinofit.database.models.DummyApiModel

class ApiRepository(private val apiService: ApiServiceInterface) {
    // we passing referene of our ApiService interface so that we can get the communicate with web using retrofit

    private var postLiveData = MutableLiveData<DummyApiModel>()

    val post: LiveData<DummyApiModel>
        // this is how we create getter in kotlin like jis bhi property ka getter or setter bnana hai uske neech 'get()' or 'set()' likhdo if doubt see this vid "https://www.youtube.com/watch?v=tOCXISsfgUg"
        get() = postLiveData// its a getter for above property 'post' i.e. whenever someone calls post he/she will get 'postLiveData'


    suspend fun getPost() {// suspend cause we will call it in coroutine so that we can fetch data asynchronously

        val result = apiService.getPost()// this will do the call and return the result

        if (result?.body() != null) {// just a check to see whether we got response and if yes then is it null or valid data
            postLiveData.postValue(result.body())// simply giving the data to live data so that it can update it to all it's observers
        }
    }
}