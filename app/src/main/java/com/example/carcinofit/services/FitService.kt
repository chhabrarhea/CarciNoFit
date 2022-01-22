package com.example.carcinofit.services

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.carcinofit.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.google.android.gms.common.util.ScopeUtil
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessActivities
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.*
import com.google.android.gms.fitness.request.SessionInsertRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FitService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fitnessOptions by lazy {
        FitnessOptions.builder()
            .addDataType(DataType.TYPE_ACTIVITY_SEGMENT, FitnessOptions.ACCESS_WRITE)
            .build()
    }
    private val account = GoogleSignIn.getAccountForExtension(context, fitnessOptions)

    fun hasPermission() = GoogleSignIn.hasPermissions(account, fitnessOptions)

    fun syncData(startTime: Long, endTime: Long, calories: Int, activityName: String) {
        val dataSource = DataSource.Builder()
            .setAppPackageName(context)
            .setDataType(DataType.AGGREGATE_CALORIES_EXPENDED)
            .setType(DataSource.TYPE_RAW)
            .build()
        val caloriesDataPoint = DataPoint.builder(dataSource)
            .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
            .setField(Field.FIELD_CALORIES, calories)
            .build()
        val dataSet = DataSet.builder(dataSource)
            .add(caloriesDataPoint)
            .build()
        Fitness.getHistoryClient(context, account).insertData(dataSet)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    companion object {
        const val GOOGLE_FIT_CODE = 5887
    }
}