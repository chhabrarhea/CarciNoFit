package com.example.carcinofit.ui.viewmodels

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carcinofit.data.preferences.PrefsImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel
@Inject constructor
    (private val prefsImpl: PrefsImpl) : ViewModel(), Observable {
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
        height = prefsImpl.getHeight()
        weight = prefsImpl.getWeight()
        age = prefsImpl.getAge()
        gender = prefsImpl.getGender()
        heightText.value = height.toString()
        weightText.value = weight.toString()
        ageText.value = age.toString()

    }

    fun progressHeight(progress: Int) {
        height = 100 + progress
        heightText.value = height.toString()
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
        prefsImpl.apply {
            setAge(age)
            setGender(gender)
            setHeight(height)
            setWeight(weight)
        }
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