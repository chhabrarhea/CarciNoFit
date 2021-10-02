package com.example.carcinofit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carcinofit.database.models.Exercise
import com.example.carcinofit.databinding.ExerciseCardBinding

class ExerciseAdapter(
    private val clickListener: (Exercise) -> Unit
) : ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(object :
    DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) =
        oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) =
        oldItem == newItem
}) {
    class ExerciseViewHolder(private val binding: ExerciseCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise, clickListener: (Exercise) -> Unit) {
            binding.exercise = exercise
            val duration = if (exercise.duration != 0)
                "${exercise.duration}''"
            else
                "x${exercise.reps}"
            binding.duration.text = duration
            binding.root.setOnClickListener { clickListener(exercise) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ExerciseCardBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, clickListener)
        }
    }
}