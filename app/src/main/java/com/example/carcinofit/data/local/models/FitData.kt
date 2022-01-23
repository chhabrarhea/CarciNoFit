package com.example.carcinofit.data.local.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FitData(
    val name: String,
    val endTime: Long,
    val duration: Long,
    val calories: Int,
    val exercise: Int
) : Parcelable