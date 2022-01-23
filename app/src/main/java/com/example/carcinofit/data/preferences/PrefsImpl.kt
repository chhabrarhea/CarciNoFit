package com.example.carcinofit.data.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : Prefs {
    override fun resetPrefs() {
        sharedPreferences.edit().putInt(PrefsKeys.REST_SET_PREFS_KEY, 10)
            .putInt(PrefsKeys.WEEKLY_GOAL_PREFS_KEY, 1).putInt(PrefsKeys.HEIGHT_PREFS_KEY, 150)
            .putInt(PrefsKeys.WEIGHT_PREFS_KEY, 55).putInt(PrefsKeys.GENDER_PREFS_KEY, 0)
            .putInt(PrefsKeys.AGE_PREFS_KEY, 22).apply()
    }

    override fun setWeeklyGoal(goal: Int) {
        sharedPreferences.edit().putInt(PrefsKeys.WEEKLY_GOAL_PREFS_KEY, goal).apply()
    }

    override fun getWeeklyGoal() =
        sharedPreferences.getInt(PrefsKeys.WEEKLY_GOAL_PREFS_KEY, 3)

    override fun setRestSet(set: Int) =
        sharedPreferences.edit().putInt(PrefsKeys.REST_SET_PREFS_KEY, set).apply()

    override fun getRestSet() =
        sharedPreferences.getInt(PrefsKeys.REST_SET_PREFS_KEY, 10)

    override fun getHeight(): Int {
        return sharedPreferences.getInt(PrefsKeys.HEIGHT_PREFS_KEY, 140)
    }

    override fun setHeight(height: Int) {
        sharedPreferences.edit().putInt(PrefsKeys.HEIGHT_PREFS_KEY, height).apply()
    }

    override fun getWeight(): Int {
        return sharedPreferences.getInt(PrefsKeys.WEIGHT_PREFS_KEY, 50)
    }

    override fun setWeight(weight: Int) {
        sharedPreferences.edit().putInt(PrefsKeys.WEIGHT_PREFS_KEY, weight).apply()
    }

    override fun setAge(age: Int) {
        sharedPreferences.edit().putInt(PrefsKeys.AGE_PREFS_KEY, age).apply()
    }

    override fun getAge(): Int {
        return sharedPreferences.getInt(PrefsKeys.AGE_PREFS_KEY, 20)
    }

    override fun getGender(): Int {
        return sharedPreferences.getInt(PrefsKeys.GENDER_PREFS_KEY, 0)
    }

    override fun setGender(gender: Int) {
        sharedPreferences.edit().putInt(PrefsKeys.GENDER_PREFS_KEY, gender).apply()
    }

    override fun setSyncToGoogleFit(sync: Boolean) {
        sharedPreferences.edit().putBoolean(PrefsKeys.SYNC_TO_GOOGLE_FIT_KEY, sync).apply()
    }

    override fun getSyncToGoogleFit(): Boolean {
        return sharedPreferences.getBoolean(PrefsKeys.SYNC_TO_GOOGLE_FIT_KEY, false)
    }

}