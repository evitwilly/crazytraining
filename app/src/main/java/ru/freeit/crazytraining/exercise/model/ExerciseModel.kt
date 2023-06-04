package ru.freeit.crazytraining.exercise.model

import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

class ExerciseModel(
    @DrawableRes
    private val icon: Int,
    @ColorInt
    private val color: Int,
    private val title: String = "",
    private val measuredValueModel: ExerciseMeasuredValueModel = ExerciseMeasuredValueModel.QUANTITY
) {
   fun bindTitle(view: TextView) {
       view.text = title
   }
}