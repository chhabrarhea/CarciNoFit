package com.example.carcinofit.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carcinofit.R
import com.example.carcinofit.databinding.FragmentWorkoutSummaryBinding
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


class WorkoutSummaryFragment : Fragment() {
    private val binding: FragmentWorkoutSummaryBinding by lazy {
        FragmentWorkoutSummaryBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        throwConfetti()
        binding.statsLayout.workoutsTitle.text = getString(R.string.exercises)
        binding.statsLayout.image.visibility = View.GONE
        binding.statsLayout.workoutsTv.text = requireArguments().getInt("exercises").toString()
        binding.statsLayout.caloriesTv.text = requireArguments().getInt("calories").toString()
        binding.statsLayout.minutesTv.text = requireArguments().getInt("duration").toString()
        return binding.root
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
}