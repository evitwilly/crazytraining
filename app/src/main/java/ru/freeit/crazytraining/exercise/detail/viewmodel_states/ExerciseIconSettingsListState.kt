package ru.freeit.crazytraining.exercise.detail.viewmodel_states

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.adapter.CoreAdapter
import ru.freeit.crazytraining.exercise.detail.adapter.ExerciseColorViewHolder
import ru.freeit.crazytraining.exercise.detail.adapter.ExerciseIconViewHolder

sealed class ExerciseIconSettingsListState(
    protected val checkedIcon: Int,
    protected val checkedColor: Int
) {

    abstract fun bindTextView(view: TextView)
    abstract fun bindItems(listView: RecyclerView, selectListener: (Int) -> Unit)

    fun bindImageView(view: ImageView) {
        view.setImageResource(checkedIcon)
        view.setColorFilter(checkedColor)
    }

    class Icons(
        private val items: IntArray,
        checkedIcon: Int,
        checkedColor: Int
    ) : ExerciseIconSettingsListState(checkedIcon, checkedColor) {

        override fun bindTextView(view: TextView) {
            view.setText(R.string.change_color)
        }

        override fun bindItems(listView: RecyclerView, selectListener: (Int) -> Unit) {
            listView.adapter = CoreAdapter(
                items = items.toList(),
                viewHolder = { context -> ExerciseIconViewHolder.from(context, selectListener, checkedIcon) }
            )
        }

    }

    class Colors(
        private val items: IntArray,
        checkedIcon: Int,
        checkedColor: Int
    ) : ExerciseIconSettingsListState(checkedIcon, checkedColor) {

        override fun bindTextView(view: TextView) {
            view.setText(R.string.change_icon)
        }

        override fun bindItems(listView: RecyclerView, selectListener: (Int) -> Unit) {
            listView.adapter = CoreAdapter(
                items = items.toList(),
                viewHolder = { context -> ExerciseColorViewHolder.from(context, selectListener, checkedColor) }
            )
        }

    }

}