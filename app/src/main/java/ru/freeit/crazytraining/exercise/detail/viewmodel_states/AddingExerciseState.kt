package ru.freeit.crazytraining.exercise.detail.viewmodel_states

import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class AddingExerciseState(
    val icon: Int,
    val color: Int,
    private val title: String = "",
    val measuredState: ExerciseMeasuredValueListState
) {
    val is_valid: Boolean
        get() = title.isNotBlank()

    fun model(id: Int): ExerciseModel {
        return ExerciseModel(
            icon = icon,
            color = color,
            title = title,
            measuredValueModel = measuredState.checkedMeasuredModel,
            id = id
        )
    }

    fun withChangedIcon(icon: Int) = AddingExerciseState(icon, color, title, measuredState)
    fun withChangedColor(color: Int) = AddingExerciseState(icon, color, title, measuredState)
    fun withChangedTitle(title: String) = AddingExerciseState(icon, color, title, measuredState)
    fun withChangedMeasuredState(measuredState: ExerciseMeasuredValueListState) = AddingExerciseState(icon, color, title, measuredState)

    fun bindImageView(imageView: ImageView) {
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

    override fun hashCode(): Int {
        var result = icon
        result = 31 * result + color
        result = 31 * result + title.hashCode()
        result = 31 * result + measuredState.hashCode()
        return result
    }

}