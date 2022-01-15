package com.example.carcinofit.ui.viewmodels

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.carcinofit.data.preferences.PrefsImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefsImpl: PrefsImpl,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel(), Observable {

    private var restTime: Int = prefsImpl.getRestSet()
    private var weeklyGoal = prefsImpl.getWeeklyGoal()

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
        prefsImpl.setRestSet(restTime)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    fun setWeeklyGoal() {
        prefsImpl.setWeeklyGoal(weeklyGoal)
    }

    fun resetData() {
        prefsImpl.resetPrefs()
    }

    fun setReminderDays(days: List<Int>) {
        savedStateHandle.set(REMINDER_DAYS, days)
    }

    fun getReminderDays() = savedStateHandle[REMINDER_DAYS] ?: emptyList<Int>()

    companion object {
        private const val REMINDER_DAYS = "reminder_days"
    }
}