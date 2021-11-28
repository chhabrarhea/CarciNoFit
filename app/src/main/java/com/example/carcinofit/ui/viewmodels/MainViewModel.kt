package com.example.carcinofit.ui.viewmodels


import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carcinofit.data.Repository
import com.example.carcinofit.other.Constants
import com.example.workoutapp.database.models.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
): ViewModel(), LifecycleObserver {

    val monthlyWorkouts= MutableLiveData<List<Workout>>()

    fun insertWorkout(category:Int,absoluteDate: Date,duration: Long,calories:Int,speed:Float=0f,dist:Int=0,img:Bitmap?=null){
        val date=Calendar.getInstance()
        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        date.set(Calendar.DATE,localDate.dayOfMonth)
        date.set(Calendar.YEAR,localDate.year)
        date.set(Calendar.MONTH,localDate.monthValue)
        date.set(Calendar.HOUR,23)
        date.set(Calendar.MINUTE,59)
        date.set(Calendar.SECOND,59)
        date.set(Calendar.MILLISECOND,59)
        val workout=Workout(category,img,absoluteDate,date.time,speed,dist,duration,calories)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertWorkout(workout)
        }
    }

    fun getWorkoutByMonth(mo:Int,year:Int){
        val sdf= SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
        val date1=sdf.parse("$mo/01/$year 00:00:00")
        val date2=sdf.parse("$mo/31/$year 23:59:59")
        viewModelScope.launch(Dispatchers.IO) {
            monthlyWorkouts.postValue(repository.getMonthlyWorkouts(date1!!, date2!!))
    }}

    fun deleteWorkouts(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

    fun getRestSet(): Int = sharedPreferences.getInt(Constants.userRestTime, 10)
}