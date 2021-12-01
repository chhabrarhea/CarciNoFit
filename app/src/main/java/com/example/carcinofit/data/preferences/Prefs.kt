package com.example.carcinofit.data.preferences

interface Prefs {
    fun resetPrefs()
    fun setWeeklyGoal(goal: Int)
    fun getWeeklyGoal(): Int
    fun setRestSet(set: Int)
    fun getRestSet(): Int
    fun getHeight(): Int
    fun setHeight(height: Int)
    fun getWeight(): Int
    fun setWeight(weight: Int)
    fun setAge(age: Int)
    fun getAge(): Int
    fun getGender(): Int
    fun setGender(gender: Int)
}