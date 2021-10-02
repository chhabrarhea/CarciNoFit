package com.example.carcinofit.other

import android.os.CountDownTimer


class Timer(private val updateUI: (Long) -> Unit, private val finishTimer: () -> Unit) {
    private lateinit var timer: CountDownTimer
    private var timerRunning = false
    private var timerInitialized = false
    private var timeLeftInMillis = 0L

    private fun startTimer() {

        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(p0: Long) {
                updateUI(p0)
                timeLeftInMillis = p0
            }

            override fun onFinish() {
                finishTimer()
                timerInitialized = false
                timeLeftInMillis = 0
                timerRunning = false
            }

        }
        timer.start()
        timerRunning = true

    }

    fun initializeTimer(duration: Long) {
        timeLeftInMillis = duration
        timerInitialized = true
        startTimer()
    }

    fun pauseTimer() {
        if (timerRunning) {
            timer.cancel()
            timerRunning = false
        } else if (timerInitialized)
            startTimer()

    }


}