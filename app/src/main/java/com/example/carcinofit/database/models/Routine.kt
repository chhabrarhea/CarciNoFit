package com.example.carcinofit.database.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Routine(
    val id: Int,
    val name: String,
    val image: String,
    val desc: String,
    val caloriesBurned: Int,
    val duration: Int,
    val exercises: List<Exercise>,
    val color: String
) : Parcelable