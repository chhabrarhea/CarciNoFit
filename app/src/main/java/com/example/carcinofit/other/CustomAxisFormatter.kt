package com.example.carcinofit.other

import com.example.carcinofit.data.local.models.ChartStats
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class CustomAxisFormatter(val calories:List<ChartStats>):IndexAxisValueFormatter() {

    override fun getFormattedValue(value: Float): String {

        val date =
            Calendar.getInstance().apply { timeInMillis = calories[abs(value.toInt())].timestamp }
        val dateFormat = SimpleDateFormat("dd MMM ''yy", Locale.getDefault())
        return dateFormat.format(date.time)
    }
}