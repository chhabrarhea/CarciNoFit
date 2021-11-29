package com.example.carcinofit.other

import android.graphics.Color

object Constants {
    const val dummyURL="https://jsonplaceholder.typicode.com/"

    const val WORKOUT_DATABASE_NAME = "work_db"

    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1

    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L

    const val POLYLINE_COLOR = Color.BLACK
    const val POLYLINE_WIDTH = 8f
    const val MAP_ZOOM = 18f

    const val TIMER_UPDATE_INTERVAL = 50L

    const val userHeightKey="userHeightKey"
    const val userWeightKey="userWeightKey"
    const val userWeeklyGoal="userWeeklyGoal"
    const val userAge="userAge"
    const val userGender="userGender"
    const val userRestTime="userRestTime"

    val BmiCategory = arrayOf(
        "Severely Underweight", "Underweight", "Normal",
        "Overweight", "Obese Class I", "Obese Class II"
    )
}