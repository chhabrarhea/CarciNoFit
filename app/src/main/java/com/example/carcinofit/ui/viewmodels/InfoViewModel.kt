package com.example.carcinofit.ui.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carcinofit.other.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel
@Inject constructor
    (private val sharedPreferences: SharedPreferences) : ViewModel(), Observable {
    var height = 0
    var weight = 0
    var age = 0
    var gender = 0

    @Bindable
    val heightText = MutableLiveData<String>()

    @Bindable
    val weightText = MutableLiveData<String>()

    @Bindable
    val ageText = MutableLiveData<String>()

    init {
        height = sharedPreferences.getInt(Constants.userHeightKey, 140)
        weight = sharedPreferences.getInt(Constants.userWeightKey, 50)
        age = sharedPreferences.getInt(Constants.userAge, 20)
        gender = sharedPreferences.getInt(Constants.userGender, 0)
        heightText.value = height.toString()
        weightText.value = weight.toString()
        ageText.value = age.toString()

    }

    fun progressHeight(progress: Int) {
        height = 100 + progress
        heightText.value = height.toString()
        Log.i("height", heightText.value.toString())
    }

    fun incrementWeight() {
        weight++
        weightText.value = weight.toString()
    }

    fun decrementWeight() {
        weight--
        weightText.value = weight.toString()
    }

    fun incrementAge() {
        age++
        ageText.value = age.toString()
    }

    fun decrementAge() {
        age--
        ageText.value = age.toString()
    }

    fun saveData() {
        sharedPreferences.edit().putInt(Constants.userHeightKey, height)
            .putInt(Constants.userWeightKey, weight)
            .putInt(Constants.userAge, age)
            .putInt(Constants.userGender, gender)
            .apply()
    }

    fun getProgressBarProgress(): Int {
        return height - 100
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        //Intentionally left empty
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        //Intentionally left empty
    }
}