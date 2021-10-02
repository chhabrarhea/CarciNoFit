package com.example.carcinofit.database


import com.example.workoutapp.database.models.Workout
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(private val dao: WorkoutDAO) {
    suspend fun insertWorkout(workout: Workout) = dao.insertRun(workout)
    suspend fun caloriesBurned() = dao.getDailyCalories()
    suspend fun getWeeklyWorkouts(start: Date, end: Date) = dao.getWeeklyWorkouts(start, end)
    suspend fun getMonthlyWorkouts(start: Date, end: Date) = dao.getWorkoutsBetween(start, end)
    fun getStatsHeaderData() = dao.getTotalStats()
    suspend fun deleteAllData() = dao.deleteAllData()
}