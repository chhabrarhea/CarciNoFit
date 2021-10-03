package com.example.carcinofit.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carcinofit.R
import com.example.carcinofit.databinding.FragmentSettingsBinding
import com.example.carcinofit.databinding.HwDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val binding: FragmentSettingsBinding by lazy {
        FragmentSettingsBinding.inflate(layoutInflater, null, false)
    }
    private val newDate: Calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            dobText.setOnClickListener { openDobDialog() }
            weightTextView.setOnClickListener { openDialog() }
            hText.setOnClickListener { openDialog() }
            cont.setOnClickListener { findNavController().navigate(R.id.action_settingsFragment_to_homeFragment) }
        }
        return binding.root
    }

    private fun openDialog() {
        val binding1 = HwDialogBinding.inflate(layoutInflater, null, false)
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setView(binding1.root)
        val alert = builder.create()
        binding1.cancel.setOnClickListener { alert.dismiss() }
        binding1.ok.setOnClickListener {
            var wt: String
            if (binding1.weight.text.isNotEmpty()) {
                wt = "${binding1.weight.text} ${resources.getString(R.string.kg)}"
                binding.weightTextView.text = wt
            }
            if (binding1.height.text.isNotEmpty()) {
                wt = "${binding1.weight.text} ${resources.getString(R.string.cm)}"
                binding.hText.text = wt
            }
            alert.dismiss()
        }
        alert.show()
    }

    private fun openDobDialog() {
        val newCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                run {
                    newDate.set(year, month, day)
                    val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val dateString = df.format(newDate.time)
                    binding.dobText.text = dateString
                }
            }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(
                Calendar.DAY_OF_MONTH
            )
        )
        datePicker.show()

    }
}