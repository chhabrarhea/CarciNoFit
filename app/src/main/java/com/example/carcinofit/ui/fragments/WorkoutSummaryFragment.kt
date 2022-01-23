package com.example.carcinofit.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.carcinofit.R
import com.example.carcinofit.data.local.models.FitData
import com.example.carcinofit.databinding.FragmentWorkoutSummaryBinding
import com.example.carcinofit.services.FitService
import com.example.carcinofit.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import javax.inject.Inject

@AndroidEntryPoint
class WorkoutSummaryFragment : Fragment() {

    private val binding: FragmentWorkoutSummaryBinding by lazy {
        FragmentWorkoutSummaryBinding.inflate(layoutInflater, null, false)
    }

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var fitService: FitService

    private val routineData: FitData? by lazy {
        requireArguments().getParcelable(FIT_DATA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK && requestCode == FitService.GOOGLE_FIT_CODE)
            routineData?.let { fitService.syncData(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        throwConfetti()
        initViews()
        syncToGoogleFit()
    }

    private fun syncToGoogleFit() {
        if (!viewModel.getSyncToGoogleFit()) return
        fitService.checkPermission(routineData, requireActivity())
    }

    private fun initViews() {
        routineData?.let {
            binding.statsLayout.apply {
                workoutsTitle.text = getString(R.string.exercises)
                image.visibility = View.GONE
                workoutsTv.text = it.exercise.toString()
                caloriesTv.text = it.calories.toString()
                minutesTv.text = (it.duration / 60 / 1000).toString()
            }
        }
    }

    private fun throwConfetti() {
        binding.confettiView.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.BLUE)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(-50f, binding.confettiView.width + 50f, -50f, -50f)
            .streamFor(300, 5000L)
    }

    companion object {
        const val FIT_DATA = "fitData"
    }
}