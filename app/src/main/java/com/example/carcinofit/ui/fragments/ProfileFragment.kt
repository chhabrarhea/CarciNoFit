package com.example.carcinofit.ui.fragments

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carcinofit.R
import com.example.carcinofit.databinding.DialogAlarmDaysBinding
import com.example.carcinofit.databinding.FragmentProfileBinding
import com.example.carcinofit.databinding.RestSetDialogBinding
import com.example.carcinofit.databinding.WeeklyGoalLayoutBinding
import com.example.carcinofit.services.ReminderReceiver
import com.example.carcinofit.ui.viewmodels.MainViewModel
import com.example.carcinofit.ui.viewmodels.ProfileViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater, null, false)
    }
    private val viewModel: ProfileViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private val alarmDayDialog by lazy { AlertDialog.Builder(requireContext()).create() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAlarmDaySelectionDialog()
        binding.apply {
            profileTv.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_infoFragment)
            }
            restSetTv.setOnClickListener { inflateRestSetDialog() }
            goalTv.setOnClickListener { inflateWeeklyGoalDialog() }
            resetTv.setOnClickListener { inflateResetProgressDialog() }
            reminderTv.setOnClickListener { alarmDayDialog.show() }
        }
    }

    private fun inflateTimeDialog() {
        val time = Calendar.getInstance()
        val timeDialog = TimePickerDialog(context, { _, hour, min ->
            run {
                setAlarm(hour, min)
            }
        }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true)
        timeDialog.show()
    }

    private fun initAlarmDaySelectionDialog() {
        DialogAlarmDaysBinding.inflate(layoutInflater).apply {
            val days =
                listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
            for (day in days) {
                val chip = layoutInflater.inflate(
                    R.layout.list_item_alarm_day,
                    dayChipGroup,
                    false
                ) as Chip
                chip.text = day
                chip.id = days.indexOf(day) + 1
                dayChipGroup.addView(chip)
            }
            cancelButton.setOnClickListener {
                alarmDayDialog.dismiss()
            }
            cancelButton.text = getString(R.string.cancel)
            confirmButton.text = getString(R.string.confirm)
            confirmButton.setOnClickListener {
                viewModel.setReminderDays(dayChipGroup.checkedChipIds)
                alarmDayDialog.dismiss()
                if (dayChipGroup.isSelected) inflateTimeDialog()
            }
            alarmDayDialog.setView(root)
        }
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

    private fun setAlarm(hour: Int, mins: Int) {
        val date = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, mins)
            set(Calendar.SECOND, 0)
        }

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for (day in 1..7) {
            date.set(Calendar.DAY_OF_WEEK, day)
            val intent =
                Intent(requireContext().applicationContext, ReminderReceiver::class.java).apply {
                    putExtra(ReminderReceiver.NOTIFICATION_ID, day)
                }
            val pendingIntent = PendingIntent.getBroadcast(
                requireContext().applicationContext,
                day,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            if (day !in viewModel.getReminderDays()) continue
            val time =
                if (date.timeInMillis <= System.currentTimeMillis())
                    date.timeInMillis + (24 * 60 * 60 * 1000 * 7)
                else date.timeInMillis
            alarmManager.cancel(pendingIntent)
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                time, AlarmManager.INTERVAL_DAY * 7, pendingIntent
            )
        }
        Snackbar.make(binding.root, "Reminder Set!", Snackbar.LENGTH_LONG).show()
    }
}