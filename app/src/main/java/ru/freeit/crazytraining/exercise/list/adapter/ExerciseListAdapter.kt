package ru.freeit.crazytraining.exercise.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseDetailState

class ExerciseListAdapter(private val listeners: ExerciseViewHolderListeners): ListAdapter<ExerciseDetailState, ExerciseViewHolder>(object: DiffUtil.ItemCallback<ExerciseDetailState>() {
    override fun areItemsTheSame(oldItem: ExerciseDetailState, newItem: ExerciseDetailState) = true
    override fun areContentsTheSame(oldItem: ExerciseDetailState, newItem: ExerciseDetailState) = oldItem == newItem
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExerciseViewHolder.from(parent = parent)

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position), listeners)
    }

}