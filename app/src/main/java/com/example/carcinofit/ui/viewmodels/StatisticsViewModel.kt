package com.example.workoutapp.ui.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carcinofit.R
import com.example.carcinofit.database.Repository
import com.example.carcinofit.database.models.ChartStats
import com.example.carcinofit.databinding.WeekViewBinding
import com.example.carcinofit.other.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val weeklyWorkouts = MutableLiveData<List<Date>>()
    val workouts = weeklyWorkouts as LiveData<*>
    val caloriesBurned = MutableLiveData<List<ChartStats>>()
    val totalData = repository.getStatsHeaderData()

    fun getChartStats() {
        viewModelScope.launch {
            caloriesBurned.value = repository.caloriesBurned()
        }
    }

    fun getWeeklyGoal(): String = sharedPreferences.getInt(Constants.userWeeklyGoal, 1).toString()

    private fun getWeeklyWorkouts(mon: Calendar, sun: Calendar) {
        mon.set(Calendar.HOUR, 0)
        mon.set(Calendar.MINUTE, 0)
        mon.set(Calendar.SECOND, 0)
        sun.set(Calendar.HOUR, 23)
        sun.set(Calendar.MINUTE, 59)
        sun.set(Calendar.SECOND, 59)
        viewModelScope.launch {
            weeklyWorkouts.value = repository.getWeeklyWorkouts(mon.time, sun.time)
        }
    }

    fun initializeWeekView(calendarView: WeekViewBinding) {
        val day = Calendar.getInstance()
        day.timeInMillis = System.currentTimeMillis()
        val monday = Calendar.getInstance()
        var sunday = Calendar.getInstance()
        if (day.get(Calendar.DAY_OF_WEEK) == 1) {
            sunday = day
            monday.set(
                day.get(Calendar.YEAR),
                day.get(Calendar.MONTH),
                day.get(Calendar.DAY_OF_MONTH) - 6
            )
        } else {
            sunday.set(
                day.get(Calendar.YEAR),
                day.get(Calendar.MONTH),
                day.get(Calendar.DAY_OF_MONTH) + (8 - day.get(Calendar.DAY_OF_WEEK))
            )
            monday.set(
                day.get(Calendar.YEAR),
                day.get(Calendar.MONTH),
                day.get(Calendar.DAY_OF_MONTH) - day.get(Calendar.DAY_OF_WEEK) + 2
            )
        }
        calendarView.mondayTv.text = monday.get(Calendar.DAY_OF_MONTH).toString()
        calendarView.tuesdayTv.text = (sunday.get(Calendar.DAY_OF_MONTH) - 5).toString()
        calendarView.wednesdayTv.text = (sunday.get(Calendar.DAY_OF_MONTH) - 4).toString()
        calendarView.thursdayTv.text = (sunday.get(Calendar.DAY_OF_MONTH) - 3).toString()
        calendarView.fridayTv.text = (sunday.get(Calendar.DAY_OF_MONTH) - 2).toString()
        calendarView.saturdayTv.text = (sunday.get(Calendar.DAY_OF_MONTH) - 1).toString()
        calendarView.sundayTv.text = (sunday.get(Calendar.DAY_OF_MONTH)).toString()
        getWeeklyWorkouts(monday, sunday)
    }

    fun updateWeekView(calendarView: WeekViewBinding, context: Context) {
        for (date in weeklyWorkouts.value!!) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            val day = calendar.get(Calendar.DAY_OF_WEEK)
            val card = calendarView.root.findViewWithTag(day) as CardView
            card.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.secondaryLightColor
                )
            )
            val textView = card.getChildAt(0) as TextView
            textView.setTextColor(context.getColor(R.color.secondaryDarkColor))
        }
    }

}