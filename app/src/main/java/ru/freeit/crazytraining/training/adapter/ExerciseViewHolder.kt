package ru.freeit.crazytraining.training.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.fontSize
import ru.freeit.crazytraining.core.theming.extensions.layoutParams
import ru.freeit.crazytraining.core.theming.extensions.recyclerLayoutParams
import ru.freeit.crazytraining.core.theming.typeface.TypefaceStyle
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseViewHolder(
    private val view: TextView
) : RecyclerView.ViewHolder(view) {

    fun bind(model: ExerciseModel) {
        model.bindTitle(view)
    }

    companion object {
        fun from(parent: ViewGroup) : ExerciseViewHolder {
            val context = parent.context
            val textView = CoreTextView(context)
            textView.fontSize(17f)
            textView.fontFamily(TypefaceStyle.MEDIUM)
            textView.layoutParams(recyclerLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(8)))
            return ExerciseViewHolder(textView)
        }
    }
}