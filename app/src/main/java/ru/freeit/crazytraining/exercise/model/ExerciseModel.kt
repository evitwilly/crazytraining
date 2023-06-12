package ru.freeit.crazytraining.exercise.model

import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.exercise.data.database.ExerciseTableDb
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel

class ExerciseModel(
    @DrawableRes
    private val icon: Int,
    @ColorInt
    private val color: Int,
    private val title: String = "",
    private val measuredValueModel: ExerciseMeasuredValueModel = ExerciseMeasuredValueModel.QUANTITY,
    private val sets: List<ExerciseSetModel> = emptyList(),
) {

   val database: ExerciseTableDb
       get() = ExerciseTableDb(icon, color, title, measuredValueModel.ordinal)

   fun bindTitle(view: TextView) {
       view.text = title
   }

   fun bindImage(view: ImageView) {
       if (icon != -1) {
           view.setImageResource(icon)
       }
       if (color != -1) {
           view.setColorFilter(color)
           val background = GradientDrawable()
           background.setStroke(view.context.dp(1), color)
           background.cornerRadius = view.context.dp(8f)
           view.background = background
       }
   }

    fun bindMeasuredValue(view: TextView) {
        val unit = measuredValueModel.unit
        if (unit != -1) {
            view.setText(unit)
        }
    }

   fun withSets(sets: List<ExerciseSetModel>) = ExerciseModel(icon, color, title, measuredValueModel, sets)

   override fun equals(other: Any?): Boolean {
       if (other == null) return false
       if (other !is ExerciseModel) return false

       return icon == other.icon && color == other.color && title == other.title && measuredValueModel == other.measuredValueModel
   }

    override fun hashCode(): Int {
        var result = icon
        result = 31 * result + color
        result = 31 * result + title.hashCode()
        result = 31 * result + measuredValueModel.hashCode()
        return result
    }

}