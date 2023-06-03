package ru.freeit.crazytraining.exercise.viewmodel_states

import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import android.widget.TextView
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class AddingExerciseState(
    private val icon: Int,
    private val color: Int,
    private val title: String = "",
    val measuredState: ExerciseMeasuredValueListState
) {
    val model: ExerciseModel
        get() = ExerciseModel(icon, color, title, measuredState.checkedMeasuredModel)

    fun withChangedIcon(icon: Int) = AddingExerciseState(icon, color, title, measuredState)
    fun withChangedColor(color: Int) = AddingExerciseState(icon, color, title, measuredState)
    fun withChangedTitle(title: String) = AddingExerciseState(icon, color, title, measuredState)
    fun withChangedMeasuredState(measuredState: ExerciseMeasuredValueListState) = AddingExerciseState(icon, color, title, measuredState)

    fun bindViews(titleView: TextView, imageView: ImageView) {
        titleView.text = title
        imageView.setImageResource(icon)
        imageView.setColorFilter(color)
        val background = GradientDrawable()
        background.cornerRadius = imageView.context.dp(16f)
        background.setStroke(imageView.context.dp(2), color)
        imageView.background = background
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is AddingExerciseState) return false
        return icon == other.icon && color == other.color && title == other.title && measuredState == other.measuredState
    }

}