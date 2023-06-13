package ru.freeit.crazytraining.exercise.detail.viewmodel_states

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.adapter.CoreAdapter
import ru.freeit.crazytraining.exercise.detail.adapter.ExerciseColorViewHolder
import ru.freeit.crazytraining.exercise.detail.adapter.ExerciseIconViewHolder

sealed class ExerciseIconSettingsListState(
    protected val items: IntArray,
    protected val checkedIcon: Int,
    protected val checkedColor: Int
) {

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseIconSettingsListState) return false

        return items.contentEquals(other.items) && checkedIcon == other.checkedIcon && checkedColor == other.checkedColor
    }

    override fun hashCode(): Int {
        var result = items.contentHashCode()
        result = 31 * result + checkedIcon
        result = 31 * result + checkedColor
        return result
    }

    abstract fun bindTextView(view: TextView)
    abstract fun bindItems(listView: RecyclerView, selectListener: (Int) -> Unit)

    fun bindImageView(view: ImageView) {
        view.setImageResource(checkedIcon)
        view.setColorFilter(checkedColor)
    }

    class Icons(items: IntArray, checkedIcon: Int, checkedColor: Int) : ExerciseIconSettingsListState(items, checkedIcon, checkedColor) {

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

    class Colors(items: IntArray, checkedIcon: Int, checkedColor: Int) : ExerciseIconSettingsListState(items, checkedIcon, checkedColor) {

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