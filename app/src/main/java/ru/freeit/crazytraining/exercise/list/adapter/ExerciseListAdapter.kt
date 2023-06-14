package ru.freeit.crazytraining.exercise.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseListAdapter(
    private val items: List<ExerciseModel>,
    private val editClickListener: (ExerciseModel) -> Unit,
    private val removeClickListener: (ExerciseModel) -> Unit
) : RecyclerView.Adapter<ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder.from(
            parent = parent,
            viewModel = ExerciseEditButtonViewModel(
                editClickListener = editClickListener,
                removeClickListener = removeClickListener
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}