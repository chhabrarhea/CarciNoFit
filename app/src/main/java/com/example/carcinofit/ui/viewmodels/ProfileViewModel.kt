package com.example.carcinofit.ui.viewmodels

import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carcinofit.other.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel(), Observable {
    private var restTime: Int = sharedPreferences.getInt(Constants.userRestTime, 20)
    private var weeklyGoal = sharedPreferences.getInt(Constants.userWeeklyGoal, 1)

    @Bindable
    val restTimeText = MutableLiveData<String>()

    @Bindable
    val weeklyGoalText = MutableLiveData<String>()

    init {
        restTimeText.value = restTime.toString()
        weeklyGoalText.value = weeklyGoal.toString()
    }

    fun incrementWeeklyGoal() {
        if (weeklyGoal == 7)
            return
        weeklyGoal++
        weeklyGoalText.value = weeklyGoal.toString()
    }

    fun decrementWeeklyGoal() {
        if (weeklyGoal == 1)
            return
        weeklyGoal--
        weeklyGoalText.value = weeklyGoal.toString()
    }

    fun incrementRestSet() {
        if (restTime >= 40)
            return
        restTime++
        restTimeText.value = restTime.toString()
    }

    fun decrementRestSet() {
        if (restTime <= 10)
            return
        restTime--
        restTimeText.value = restTime.toString()
    }

    fun setRestSet() {
        sharedPreferences.edit().putInt(Constants.userRestTime, restTime).apply()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    fun setWeeklyGoal() {
        sharedPreferences.edit().putInt(Constants.userWeeklyGoal, weeklyGoal).apply()
    }

    fun resetData() {
        sharedPreferences.edit().putInt(Constants.userRestTime, 10)
            .putInt(Constants.userWeeklyGoal, 1).putInt(Constants.userHeightKey, 150)
            .putInt(Constants.userWeightKey, 55).putInt(Constants.userGender, 0)
            .putInt(Constants.userAge, 22).apply()
    }

    fun inflateReminderDialog(context: Context) {
        val time = Calendar.getInstance()
        val timeDialog = TimePickerDialog(context, { _, hour, min ->
            run {

            }
        }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true)
    }

    fun setAlarm(dayOfWeek: Int, AlarmHrsInInt: Int, AlarmMinsInInt: Int) {
        val alarmCalendar = Calendar.getInstance()
        alarmCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)
        alarmCalendar.set(Calendar.HOUR, AlarmHrsInInt)
        alarmCalendar.set(Calendar.MINUTE, AlarmMinsInInt)
        alarmCalendar.set(Calendar.SECOND, 0)
        val alarmTime: Long = alarmCalendar.timeInMillis

//        am.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, 24 * 60 * 60 * 1000, pi)
    }
}