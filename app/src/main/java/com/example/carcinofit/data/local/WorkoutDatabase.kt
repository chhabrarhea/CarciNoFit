package com.example.carcinofit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.carcinofit.data.local.converters.BitmapConverter
import com.example.carcinofit.data.local.converters.DateConvertor
import com.example.workoutapp.database.models.Workout

@Database(entities = [Workout::class], version = 1, exportSchema = false)
@TypeConverters(BitmapConverter::class, DateConvertor::class)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun getRunDao(): WorkoutDAO
}
