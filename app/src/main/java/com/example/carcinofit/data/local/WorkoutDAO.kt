package com.example.carcinofit.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.carcinofit.data.local.models.ChartStats
import com.example.carcinofit.data.local.models.Stats
import com.example.workoutapp.database.models.Workout
import java.util.*

@Dao
interface WorkoutDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Workout)

    @Delete
    suspend fun deleteRun(run: Workout)

    @Query("SELECT * FROM workout_table ORDER BY timestamp DESC")
    fun getAllRunsSortedByDate(): LiveData<List<Workout>>

    @Query("SELECT * FROM workout_table ORDER BY timeInMillis DESC")
    fun getAllRunsSortedByTimeInMillis(): LiveData<List<Workout>>

    @Query("SELECT * FROM workout_table ORDER BY caloriesBurned DESC")
    fun getAllRunsSortedByCaloriesBurned(): LiveData<List<Workout>>


    @Query("SELECT * FROM workout_table ORDER BY avgSpeedInKMH DESC")
    fun getAllRunsSortedByAvgSpeed(): LiveData<List<Workout>>

    @Query("SELECT * FROM workout_table ORDER BY distanceInMeters DESC")
    fun getAllRunsSortedByDistance(): LiveData<List<Workout>>

    @Query("SELECT SUM(timeInMillis) FROM workout_table")
    fun getTotalTimeInMillis(): LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM workout_table")
    fun getTotalCaloriesBurned(): LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM workout_table")
    fun getTotalDistance(): LiveData<Int>

    @Query("SELECT AVG(avgSpeedInKMH) FROM workout_table")
    fun getTotalAvgSpeed(): LiveData<Float>

    @Query("Select * from workout_table where timestamp between :start AND :end")
   suspend fun getWorkoutsBetween(start: Date, end:Date):List<Workout>

   @Query("SELECT timestamp from workout_table where  timestamp between :start AND :end ")
   suspend fun getWeeklyWorkouts(start:Date,end: Date):List<Date>

   @Query("Select sum(caloriesBurned) as calories, sum(timeInMillis)/1000/60 as duration,timestamp from workout_table GROUP BY date(timestamp/1000, 'unixepoch')")
   suspend fun getDailyCalories():List<ChartStats>

   @Query("Select sum(caloriesBurned) as calories,sum(timeInMillis)/1000/60 as total_time,count(id) as workouts from workout_table")
   fun getTotalStats():LiveData<Stats>

   @Query("Delete from workout_table")
   suspend fun deleteAllData()
}