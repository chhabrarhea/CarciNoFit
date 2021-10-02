package com.example.carcinofit.other

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow

data class BmiCalculator(val height: Int, val weight: Int) {

    var bmi: Double = 0.0

    init {
        bmi = (weight / (height.toDouble() / 100).pow(2.0)).roundOff()
    }

    private fun Double.roundOff(): Double {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        return df.format(this).toDouble()
    }

    fun  getResult():Int{
       if (bmi in 15.00..16.00)
           return 0
        if(bmi in 16.00..18.50)
            return 1
        if(bmi in 18.5..25.00)
            return 2
        if(bmi in 25.00..30.00)
            return 3
        return if(bmi in 30.00..35.00)
            4
        else
            5

    }
}