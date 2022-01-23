package com.example.carcinofit.services

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.carcinofit.data.local.models.FitData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.*
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FitService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fitnessOptions by lazy {
        FitnessOptions.builder()
            .addDataType(DataType.AGGREGATE_CALORIES_EXPENDED, FitnessOptions.ACCESS_WRITE)
            .addDataType(DataType.TYPE_ACTIVITY_SEGMENT, FitnessOptions.ACCESS_WRITE)
            .build()
    }
    private val account =
        GoogleSignIn.getLastSignedInAccount(context) ?: GoogleSignIn.getAccountForExtension(
            context,
            fitnessOptions
        )

    private fun hasPermission() = GoogleSignIn.hasPermissions(account, fitnessOptions)

    fun checkPermission(data: FitData?, activity: FragmentActivity) {
        if (data == null) return
        if (hasPermission()) syncData(data)
        else requestPermission(activity)
    }

    private fun requestPermission(activity: FragmentActivity) {
        GoogleSignIn.requestPermissions(
            activity,
            GOOGLE_FIT_CODE,
            account,
            fitnessOptions
        )
    }

    fun syncData(it: FitData) {
        val dataSource = DataSource.Builder()
            .setAppPackageName(context)
            .setDataType(DataType.AGGREGATE_CALORIES_EXPENDED)
            .setType(DataSource.TYPE_RAW)
            .build()
        val caloriesDataPoint = DataPoint.builder(dataSource)
            .setTimeInterval(
                it.endTime - it.duration,
                it.endTime,
                TimeUnit.MILLISECONDS
            )
            .setField(Field.FIELD_CALORIES, it.calories.toFloat())
            .build()
        val dataSet = DataSet.builder(dataSource)
            .add(caloriesDataPoint)
            .build()
        Fitness.getHistoryClient(context, account).insertData(dataSet)
            .addOnSuccessListener {
                Timber.i("onSuccess")
            }
            .addOnFailureListener { ex ->
                Timber.i("onFailed: $ex")
            }
    }

    companion object {
        const val GOOGLE_FIT_CODE = 5887
    }
}