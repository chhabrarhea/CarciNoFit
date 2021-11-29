package com.example.carcinofit.ui.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.carcinofit.data.Repository
import com.example.carcinofit.data.local.models.ChartStats
import com.example.carcinofit.other.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel(), LifecycleObserver {

    private val _weeklyWorkouts = MutableLiveData<List<Date>>()
    val workouts = _weeklyWorkouts as LiveData<List<Date>>

    val caloriesBurned = MutableLiveData<List<ChartStats>>()
    val totalData = repository.getStatsHeaderData()

    fun getChartStats() {
        viewModelScope.launch {
            caloriesBurned.value = repository.caloriesBurned()
        }
    }

    fun getWeeklyGoal(): Int = sharedPreferences.getInt(Constants.userWeeklyGoal, 3)

    fun getWeeklyWorkouts(mon: Calendar, sun: Calendar) {
        mon.set(Calendar.HOUR, 0)
        mon.set(Calendar.MINUTE, 0)
        mon.set(Calendar.SECOND, 0)
        sun.set(Calendar.HOUR, 23)
        sun.set(Calendar.MINUTE, 59)
        sun.set(Calendar.SECOND, 59)
        viewModelScope.launch {
            _weeklyWorkouts.value = repository.getWeeklyWorkouts(mon.time, sun.time)
        }
    }

}