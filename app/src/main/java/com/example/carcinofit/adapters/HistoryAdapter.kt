package com.example.carcinofit.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carcinofit.R
import com.example.carcinofit.databinding.ItemRunBinding
import com.example.carcinofit.databinding.RunDetailDialogBinding
import com.example.workoutapp.database.models.Workout
import com.example.carcinofit.other.TrackingUtility
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : ListAdapter<Workout, HistoryAdapter.WorkoutViewHolder>(diffUtil) {
    inner class WorkoutViewHolder(val binding: ItemRunBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout) {
            val context = binding.root.context
            when (workout.category) {
                0 -> {
                    binding.routineNameTv.text = context.getString(R.string.running)
                    binding.icon.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_runner_history_icon
                        )
                    )
                    binding.relativeRoot.setOnClickListener {
                        inflateRunDetailDialog(
                            workout,
                            context
                        )
                    }
                }
                1 -> binding.routineNameTv.text = context.getString(R.string.fat_burning)
                2 -> binding.routineNameTv.text = context.getString(R.string.legs)
                3 -> binding.routineNameTv.text = context.getString(R.string.obliques)
                4 -> binding.routineNameTv.text = context.getString(R.string.face_yoga)
                5 -> binding.routineNameTv.text = context.getString(R.string.relaxing_yoga)
            }
            binding.caloriesTv.text = workout.caloriesBurned.toString()
            binding.durationTv.text =
                TrackingUtility.getFormattedStopWatchTime(workout.timeInMillis)
            val dateFormat = SimpleDateFormat("MMM d, hh:mm aaa", Locale.getDefault())
            binding.timeStampTv.text = dateFormat.format(workout.timestamp)
        }

        private fun inflateRunDetailDialog(run: Workout, context: Context) {
            val alBuilder = AlertDialog.Builder(context)
            val dialogBinding =
                RunDetailDialogBinding.inflate(LayoutInflater.from(context), null, false)
            val avgSpeed = "${run.avgSpeedInKMH}km/h"
            dialogBinding.avgDist.text = avgSpeed
            val distanceInKm = "${run.distanceInMeters / 1000f}km"
            dialogBinding.avgDist.text = distanceInKm
            alBuilder.setView(dialogBinding.root)
            val dialog = alBuilder.create()
            dialog.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        return WorkoutViewHolder(
            ItemRunBinding.inflate(LayoutInflater.from(parent.context)),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Workout>() {
            override fun areItemsTheSame(oldItem: Workout, newItem: Workout) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Workout, newItem: Workout) =
                oldItem == newItem

        }
    }
}