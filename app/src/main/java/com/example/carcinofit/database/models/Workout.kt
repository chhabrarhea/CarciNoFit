package com.example.workoutapp.database.models

import android.graphics.Bitmap
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "workout_table")
data class Workout(
    @NonNull var category:Int,
    @Nullable
    var img: Bitmap? = null,
    var timestamp: Date,
    var relativeDate:Date,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}