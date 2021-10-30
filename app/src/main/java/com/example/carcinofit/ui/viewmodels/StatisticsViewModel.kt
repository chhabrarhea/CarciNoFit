package com.example.carcinofit.ui.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.core.view.get
import androidx.lifecycle.*
import com.example.carcinofit.R
import com.example.carcinofit.database.Repository
import com.example.carcinofit.database.models.ChartStats
import com.example.carcinofit.databinding.ListItemWeekviewBinding
import com.example.carcinofit.databinding.WeekViewBinding
import com.example.carcinofit.other.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel(), LifecycleObserver {
    private val weeklyWorkouts = MutableLiveData<List<Date>>()
    val workouts = weeklyWorkouts as LiveData<*>
    val caloriesBurned = MutableLiveData<List<ChartStats>>()
    val totalData = repository.getStatsHeaderData()

    fun getChartStats() {
        viewModelScope.launch {
            caloriesBurned.value = repository.caloriesBurned()
        }
    }

    fun getWeeklyGoal(): Int = sharedPreferences.getInt(Constants.userWeeklyGoal, 3)

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
        var day = 0
        var dayOfWeek = 0
        var year = 0
        var month = 0
        var daysInMonth = 0
        Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            day = this.get(Calendar.DAY_OF_MONTH)
            year = this.get(Calendar.YEAR)
            month = this.get(Calendar.MONTH)
            val yearMonth = YearMonth.of(year, month)
            daysInMonth = yearMonth.lengthOfMonth()
            dayOfWeek = this.get(Calendar.DAY_OF_WEEK)
        }

        val monday = Calendar.getInstance()
        val sunday = Calendar.getInstance()
        if (dayOfWeek == 1) {
            sunday.timeInMillis = System.currentTimeMillis()
            monday.set(
                year,
                if (day - 6 < 1) month - 1  else month,
                if (day - 6 < 1) daysInMonth - 6 + day else day - 6
            )
        } else {
            sunday.set(
                year,
                month,
                day + (8 - dayOfWeek)
            )
            monday.set(
                year,
                month,
                day - dayOfWeek + 2
            )
        }
        val sameMonth = if(daysInMonth - monday.get(Calendar.DAY_OF_MONTH)>= 6) 6 else daysInMonth - monday.get(Calendar.DAY_OF_MONTH)
        val root = calendarView.linearRoot
        val days = listOf("M","T","W","T","F","S","S")

        for(i in 0..sameMonth){
            root[i].apply {
                val date = (monday.get(Calendar.DAY_OF_MONTH) + i).toString()
                findViewById<TextView>(R.id.date_tv).text = date
                findViewById<TextView>(R.id.day_label_tv).text = days[i]
                findViewById<CardView>(R.id.day_card_view).tag = date
            }
        }

        for (i in 6 downTo sameMonth+1){
            root[i].apply {
                val date = (sunday.get(Calendar.DAY_OF_MONTH) - 6 + i).toString()
                findViewById<TextView>(R.id.date_tv).text = date
                findViewById<TextView>(R.id.day_label_tv).text = days[i]
                findViewById<CardView>(R.id.day_card_view).tag = date
            }
        }

        getWeeklyWorkouts(monday, sunday)
    }

    fun updateWeekView(calendarView: WeekViewBinding, context: Context) {
        for (date in weeklyWorkouts.value!!) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val card = calendarView.root.findViewWithTag<CardView>(day.toString())
            card.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.secondaryLightColor
                )
            )
            val textView = card.getChildAt(0) as TextView
            textView.setTextColor(context.getColor(R.color.secondaryDarkColor))
        }
        val goal = getWeeklyGoal()
        calendarView.goalTextView.text = SpannableStringBuilder()
            .append("${weeklyWorkouts.value?.size?:0}/")
            .color(ContextCompat.getColor(context,R.color.secondaryDarkColor)){
                append(goal.toString())
            }
            .append(" Keep Going!")
    }

}