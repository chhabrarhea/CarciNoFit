package com.example.carcinofit.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.carcinofit.R
import com.example.carcinofit.databinding.FragmentStatisticsBinding
import com.example.carcinofit.other.CustomAxisFormatter
import com.example.carcinofit.ui.viewmodels.StatisticsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class StatisticsFragment : Fragment() {
    private val viewModel: StatisticsViewModel by viewModels()
    private val binding: FragmentStatisticsBinding by lazy {
        FragmentStatisticsBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        subscribeToObservers()
        viewModel.getChartStats()
        initializeDurationGraph()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWeeklyWorkouts(mon = binding.weekView.monday, sun = binding.weekView.sunday)
    }

    private fun subscribeToObservers() {
        viewModel.workouts.observe(viewLifecycleOwner, {
            binding.weekView.setDates(it, viewModel.getWeeklyGoal())
        })

        viewModel.caloriesBurned.observe(viewLifecycleOwner, {
            initializeCalorieGraph()
            val allEntries = it.indices.map { i -> BarEntry(i.toFloat(), it[i].calories.toFloat()) }
            val allTimeEntries =
                it.indices.map { i -> Entry(i.toFloat(), it[i].duration.toFloat()) }
            setLineData(allTimeEntries)
            setBarData(allEntries)
        })

        viewModel.totalData.observe(viewLifecycleOwner, {
            binding.headerImageView.caloriesTv.text = it.calories.toString()
            binding.headerImageView.minutesTv.text = it.total_time.toString()
            binding.headerImageView.workoutsTv.text = it.workouts.toString()
        })
    }

    private fun initializeCalorieGraph() {
        binding.calorieChart.setDrawBarShadow(false)
        binding.calorieChart.setDrawValueAboveBar(true)
        binding.calorieChart.description.isEnabled = false
        binding.calorieChart.isHovered = false
        binding.calorieChart.legend.isEnabled = false

        val xAxisFormatter: IndexAxisValueFormatter =
            CustomAxisFormatter(viewModel.caloriesBurned.value!!)

        val xAxis: XAxis = binding.calorieChart.xAxis
        xAxis.position = XAxisPosition.BOTTOM

        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.valueFormatter = xAxisFormatter


        val rightAxis: YAxis = binding.calorieChart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f
        rightAxis.axisMaximum = 500f


        val leftAxis: YAxis = binding.calorieChart.axisLeft
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMaximum = 500f
        leftAxis.axisMinimum = 0f
    }

    private fun initializeDurationGraph() {
        binding.lineChart.description.isEnabled = false
        binding.lineChart.isHovered = false
        binding.lineChart.legend.isEnabled = false
        val xAxis = binding.lineChart.xAxis
        xAxis.axisMinimum = 0f

        xAxis.granularity = 1f

        xAxis.position = XAxisPosition.BOTTOM

        val leftAxis = binding.lineChart.axisLeft
        leftAxis.axisMinimum = 0f
        leftAxis.axisMaximum = 120f
        leftAxis.spaceTop = 15f
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)

        binding.lineChart.axisRight.isEnabled = false


    }

    private fun setBarData(allEntries: List<BarEntry>) {
        val dataSet = BarDataSet(allEntries, "Estimated Calories").apply {
            valueTextColor = Color.BLACK
        }
        binding.calorieChart.data = BarData(dataSet)
        dataSet.setGradientColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.secondaryLightColor
            ), ContextCompat.getColor(requireContext(), R.color.secondaryDarkColor)
        )
        binding.calorieChart.invalidate()
    }

    private fun setLineData(entries: List<Entry>) {
        val lineDataSet = LineDataSet(entries, "Duration Data")
        val iLineDataSet = ArrayList<ILineDataSet>(Collections.singletonList(lineDataSet))
        lineDataSet.color = ContextCompat.getColor(requireContext(), R.color.secondaryLightColor)
        lineDataSet.setCircleColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.secondaryDarkColor
            )
        )
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawCircleHole(true)
        lineDataSet.lineWidth = 5F
        lineDataSet.circleRadius = 10F
        lineDataSet.circleHoleRadius = 10F
        lineDataSet.valueTextSize = 10F
        lineDataSet.valueTextColor = Color.BLACK
        binding.lineChart.data = LineData(iLineDataSet)
        binding.lineChart.invalidate()
    }
}





