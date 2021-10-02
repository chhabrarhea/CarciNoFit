package com.example.carcinofit.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carcinofit.R
import com.example.carcinofit.adapters.RoutineAdapter
import com.example.carcinofit.database.models.Routine
import com.example.carcinofit.database.models.RoutineData
import com.example.carcinofit.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater, null, false)
    }
    private val adapter: RoutineAdapter by lazy {
        RoutineAdapter{ item: Routine -> routineClickListener((item)) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(RoutineData.routines)
        binding.recyclerView.apply {
            this.adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun routineClickListener(routine: Routine) {
        if (routine.id == 0) {
            findNavController().navigate(R.id.action_homeFragment_to_runTrackFragment)
        } else {
            val bundle = Bundle()
            bundle.putParcelable("routine", routine)
            findNavController().navigate(R.id.action_homeFragment_to_routineDetailFragment, bundle)
        }
    }
}