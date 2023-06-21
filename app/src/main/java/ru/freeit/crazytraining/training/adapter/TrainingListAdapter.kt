package ru.freeit.crazytraining.training.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel
import ru.freeit.crazytraining.training.viewmodel_states.TrainingDetailState

class TrainingListAdapter(
    private val addSetListener: (ExerciseModel) -> Unit,
    private val removeSetListener: (ExerciseSetModel) -> Unit
) : ListAdapter<TrainingDetailState, TrainingViewHolder>(
    object: DiffUtil.ItemCallback<TrainingDetailState>() {
        override fun areItemsTheSame(oldItem: TrainingDetailState, newItem: TrainingDetailState) = true
        override fun areContentsTheSame(oldItem: TrainingDetailState, newItem: TrainingDetailState) = oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrainingViewHolder.from(parent)

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bind(getItem(position), addSetListener, removeSetListener)
    }

}