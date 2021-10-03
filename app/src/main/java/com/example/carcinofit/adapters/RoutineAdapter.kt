package com.example.carcinofit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carcinofit.database.models.Routine
import com.example.carcinofit.databinding.RoutineCardBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class RoutineAdapter(
    private val clickListener: (Routine) -> Unit
) : ListAdapter <Routine, RoutineAdapter.RoutineViewHolder>(diffUtil) {

    class RoutineViewHolder(
        private val binding: RoutineCardBinding,
        private val clickListener: (Routine) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(routine: Routine) {
            val context = binding.root.context
            if (routine.caloriesBurned != 0)
                binding.calories.text = routine.caloriesBurned.toString()
            binding.calories.isVisible = routine.caloriesBurned != 0
            if (routine.duration != 0)
                "${routine.duration} minutes".also { binding.duration.text = it }
            binding.duration.isVisible = routine.duration != 0
            binding.rootRelativeLayout.setOnClickListener { clickListener(routine) }
            binding.heading.text = routine.name
            val ref = Firebase.storage.reference.child("headers").child(routine.image)
            Glide.with(context).load(ref).into(binding.headerImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = RoutineCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Routine>() {
            override fun areItemsTheSame(oldItem: Routine, newItem: Routine) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Routine, newItem: Routine) =
                oldItem.id == newItem.id

        }
    }
}