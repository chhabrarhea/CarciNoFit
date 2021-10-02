package com.example.carcinofit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.example.carcinofit.R
import com.example.carcinofit.adapters.HistoryAdapter
import com.example.carcinofit.databinding.FragmentHistoryBinding
import com.example.carcinofit.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history.*
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class HistoryFragment : Fragment(), OnCalendarPageChangeListener {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentHistoryBinding
    private val runAdapter: HistoryAdapter = HistoryAdapter()
    private var events = ArrayList<CalendarDay>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, null, false)
        return binding.root
    }

    private fun subscribeToObservers() {
        viewModel.monthlyWorkouts.observe(viewLifecycleOwner, {
            runAdapter.submitList(it)
            events.clear()
            for (work in it) {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = work.timestamp.time
                val calendarDay = CalendarDay(calendar).apply {
                    labelColor = R.color.secondaryDarkColor
                    backgroundResource = R.drawable.circular_label
                    backgroundDrawable =
                        ContextCompat.getDrawable(requireContext(), R.drawable.circular_label)
                }
                events.add(calendarDay)
            }
            binding.calendar.setCalendarDays(events)
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeToObservers()
        binding.calendar.setOnForwardPageChangeListener(this)
        binding.calendar.setOnPreviousPageChangeListener(this)
        setUpMonthlyWorkouts()
    }


    private fun setupRecyclerView() = rvRuns?.apply {
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setUpMonthlyWorkouts() {
        viewModel.getWorkoutByMonth(
            binding.calendar.currentPageDate.get(Calendar.MONTH) + 1,
            binding.calendar.currentPageDate.get(Calendar.YEAR)
        )

    }

    override fun onChange() {
        setUpMonthlyWorkouts()
    }


}