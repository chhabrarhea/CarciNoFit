package com.example.carcinofit.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carcinofit.R
import com.example.carcinofit.databinding.FragmentProfileBinding
import com.example.carcinofit.databinding.RestSetDialogBinding
import com.example.carcinofit.databinding.WeeklyGoalLayoutBinding
import com.example.carcinofit.ui.viewmodels.MainViewModel
import com.example.carcinofit.ui.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater, null, false)
    }
    private val viewModel: ProfileViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            profileTv.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_infoFragment)
            }
            restSetTv.setOnClickListener { inflateRestSetDialog() }
            goalTv.setOnClickListener { inflateWeeklyGoalDialog() }
            resetTv.setOnClickListener { inflateResetProgressDialog() }
        }
        return binding.root
    }

    private fun inflateRestSetDialog() {
        val dialogBinding = RestSetDialogBinding.inflate(layoutInflater, null, false)
        dialogBinding.viewModel = viewModel
        dialogBinding.lifecycleOwner = requireActivity()
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root).create()
        dialogBinding.cancelBtn.setOnClickListener { alertDialog.dismiss() }
        dialogBinding.setBtn.setOnClickListener {
            viewModel.setRestSet()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun inflateWeeklyGoalDialog() {
        val dialogBinding = WeeklyGoalLayoutBinding.inflate(layoutInflater, null, false)
        dialogBinding.viewModel = viewModel
        dialogBinding.lifecycleOwner = requireActivity()
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root).create()
        dialogBinding.cancelBtn.setOnClickListener { alertDialog.dismiss() }
        dialogBinding.setBtn.setOnClickListener {
            viewModel.setWeeklyGoal()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun inflateResetProgressDialog() {
        val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Reset Progress?")
            .setMessage("Are you sure?").setPositiveButton("Yes") { _, _ ->
                mainViewModel.deleteWorkouts()
                viewModel.resetData()
            }.setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        alertDialog.show()
    }
}