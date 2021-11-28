package com.example.carcinofit.data.local.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Exercise(
    val name: String,
    val desc: String,
    val image: String,
    val duration: Int,
    val reps: Int,
    val isGif: Boolean
) : Parcelable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Exercise

        if (name != other.name) return false
        if (desc != other.desc) return false
        if (image != other.image) return false
        if (duration != other.duration) return false
        if (reps != other.reps) return false
        if (isGif != other.isGif) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + desc.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + duration
        result = 31 * result + reps
        result = 31 * result + isGif.hashCode()
        return result
    }
}
