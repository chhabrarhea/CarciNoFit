package com.example.carcinofit.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carcinofit.R
import com.example.carcinofit.adapters.ExerciseAdapter
import com.example.carcinofit.data.local.models.Exercise
import com.example.carcinofit.data.local.models.Routine
import com.example.carcinofit.databinding.ExerciseDetailDialogBinding
import com.example.carcinofit.databinding.FragmentRoutineDetailBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutineDetailFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {
    private val binding: FragmentRoutineDetailBinding by lazy {
        FragmentRoutineDetailBinding.inflate(layoutInflater, null, false)
    }
    private val routine: Routine? by lazy {
        arguments?.getParcelable("routine")
    }

    private val adapter: ExerciseAdapter by lazy {
        ExerciseAdapter { exercise: Exercise -> exerciseClickListener(exercise) }
    }
    private var isShow = true
    private var scrollRange = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.appbar.addOnOffsetChangedListener(this)
        binding.startBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("routine", routine)
            findNavController().navigate(R.id.action_routineDetailFragment_to_restFragment, bundle)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        routine?.let {
            adapter.submitList(it.exercises)
        }
        binding.routine = routine
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun exerciseClickListener(exercise: Exercise) {
        val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        val alertDialogBinding = ExerciseDetailDialogBinding.inflate(layoutInflater, null, false)
        alertDialogBuilder.setView(alertDialogBinding.root)
        alertDialogBinding.exercise = exercise
        val alertDialog = alertDialogBuilder.create()
        alertDialogBinding.closeButton.setOnClickListener { alertDialog.dismiss() }
        alertDialog.show()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout?.totalScrollRange!!
        }
        if (scrollRange + verticalOffset == 0) {
            binding.collapseToolbar.title = routine?.name
            binding.toolbar.setBackgroundColor(Color.parseColor(routine?.color))
            binding.toolbar.fitsSystemWindows = true
            binding.collapseToolbar.setStatusBarScrimColor(Color.parseColor(routine?.color))
            isShow = true
        } else if (isShow) {
            binding.toolbar.setBackgroundColor(Color.TRANSPARENT)
            binding.collapseToolbar.title =
                " " // There should a space between double quote otherwise it wont work
            isShow = false
            binding.toolbar.fitsSystemWindows = false
        }
    }


}