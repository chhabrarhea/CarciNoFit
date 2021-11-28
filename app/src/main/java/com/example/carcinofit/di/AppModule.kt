package com.example.carcinofit.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.carcinofit.data.local.WorkoutDatabase
import com.example.carcinofit.other.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Singleton
    @Provides
    fun provideWorkoutDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        WorkoutDatabase::class.java,
        Constants.WORKOUT_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideWorkoutDao(db: WorkoutDatabase) = db.getRunDao()

    @Singleton
    @Provides
    fun getSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("WorkOutUserDetails", Context.MODE_PRIVATE)
}