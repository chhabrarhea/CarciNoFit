package com.example.carcinofit.custom

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.core.view.get
import com.example.carcinofit.R
import java.time.YearMonth
import java.util.*
import kotlin.collections.HashSet

class WeekView constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    val monday: Calendar by lazy { Calendar.getInstance() }
    val sunday: Calendar by lazy { Calendar.getInstance() }
    val root: LinearLayout by lazy {
        findViewById(R.id.linear_root)
    }


    init {
        LayoutInflater.from(context).inflate(R.layout.week_view, this, true)
        initializeWeekView()
    }

    private fun initializeWeekView() {
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
            val yearMonth = YearMonth.of(year, month + 1)
            daysInMonth = yearMonth.lengthOfMonth()
            dayOfWeek = this.get(Calendar.DAY_OF_WEEK)
        }
        if (dayOfWeek == 1) {
            sunday.timeInMillis = System.currentTimeMillis()
            monday.set(
                year,
                if (day - 6 < 1) month - 1 else month,
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
        val sameMonth =
            if (daysInMonth - monday.get(Calendar.DAY_OF_MONTH) >= 6) 6 else daysInMonth - monday.get(
                Calendar.DAY_OF_MONTH
            )
        val days = listOf("M", "T", "W", "T", "F", "S", "S")

        for (i in 0..sameMonth) {
            root[i].apply {
                val date = (monday.get(Calendar.DAY_OF_MONTH) + i).toString()
                findViewById<TextView>(R.id.date_tv).text = date
                findViewById<TextView>(R.id.day_label_tv).text = days[i]
                findViewById<CardView>(R.id.day_card_view).tag = date
            }
        }

        for (i in 6 downTo sameMonth + 1) {
            root[i].apply {
                val date = (sunday.get(Calendar.DAY_OF_MONTH) - 6 + i).toString()
                findViewById<TextView>(R.id.date_tv).text = date
                findViewById<TextView>(R.id.day_label_tv).text = days[i]
                findViewById<CardView>(R.id.day_card_view).tag = date
            }
        }
    }

    fun setDates(dates: List<Date>, goal: Int) {
        val daysActive = HashSet<String>()
        for (date in dates) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val card = root.findViewWithTag<CardView>(day.toString())
            daysActive.add(day.toString())
            card.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.secondaryLightColor
                )
            )
            val textView = card.getChildAt(0) as TextView
            textView.setTextColor(context.getColor(R.color.secondaryDarkColor))
        }

        findViewById<TextView>(R.id.goal_text_view).text = SpannableStringBuilder()
            .append("${daysActive.size}/")
            .color(ContextCompat.getColor(context, R.color.secondaryDarkColor)) {
                append(goal.toString())
            }
            .append(" Keep Going!")
    }
}