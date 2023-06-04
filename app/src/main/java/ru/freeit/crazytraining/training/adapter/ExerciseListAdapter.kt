package ru.freeit.crazytraining.training.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseListAdapter(private val items: List<ExerciseModel>) : RecyclerView.Adapter<ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExerciseViewHolder.from(parent)

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}