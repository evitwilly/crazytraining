package ru.freeit.crazytraining.exercise.model

import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import ru.freeit.crazytraining.exercise.data.database.ExerciseTableDb

class ExerciseModel(
    @DrawableRes
    private val icon: Int,
    @ColorInt
    private val color: Int,
    private val title: String = "",
    private val measuredValueModel: ExerciseMeasuredValueModel = ExerciseMeasuredValueModel.QUANTITY
) {
   val database: ExerciseTableDb
       get() = ExerciseTableDb(icon, color, title, measuredValueModel.ordinal)

   fun bindTitle(view: TextView) {
       view.text = title
   }

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