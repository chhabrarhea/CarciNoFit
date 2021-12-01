package com.example.carcinofit.other

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsUtil @Inject constructor(private val sharedPreferences: SharedPreferences) {


    companion object {
        const val HEIGHT_PREFS_KEY = "userHeightKey"
        const val WEIGHT_PREFS_KEY = "userWeightKey"
        const val WEEKLY_GOAL_PREFS_KEY = "userWeeklyGoal"
        const val AGE_PREFS_KEY = "userAge"
        const val GENDER_PREFS_KEY = "userGender"
        const val REST_SET_PREFS_KEY = "userRestTime"
    }
}