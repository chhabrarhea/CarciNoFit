package com.example.carcinofit.ui.viewmodels


import android.graphics.Bitmap
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carcinofit.data.Repository
import com.example.carcinofit.data.preferences.PrefsImpl
import com.example.workoutapp.database.models.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val prefsImpl: PrefsImpl
) : ViewModel(), LifecycleObserver {

    val monthlyWorkouts = MutableLiveData<List<Workout>>()

    fun insertWorkout(
        category: Int,
        absoluteDate: Date,
        duration: Long,
        calories: Int,
        speed: Float = 0f,
        dist: Int = 0,
        img: Bitmap? = null
    ) {
        val workout =
            Workout(category, img, absoluteDate, speed, dist, duration, calories)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertWorkout(workout)
        }
    }

    fun getWorkoutByMonth(month: Int, year: Int) {
        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
        val yearMonth = YearMonth.of(year, month)
        val daysInMonth = yearMonth.lengthOfMonth()
        val startingDate = sdf.parse("$month/01/$year 00:00:00")
        val endingDate = sdf.parse("$month/$daysInMonth/$year 23:59:59")
        viewModelScope.launch(Dispatchers.IO) {
            if (startingDate != null && endingDate != null)
                monthlyWorkouts.postValue(
                    repository.getMonthlyWorkouts(
                        startingDate,
                        endingDate
                    )
                )
        }
    }

    fun deleteWorkouts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

    fun getRestSet(): Int = prefsImpl.getRestSet()

    fun getSyncToGoogleFit(): Boolean = prefsImpl.getSyncToGoogleFit()
}