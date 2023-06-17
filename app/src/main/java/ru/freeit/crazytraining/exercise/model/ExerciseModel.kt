package ru.freeit.crazytraining.exercise.model

import android.graphics.drawable.GradientDrawable
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.exercise.data.database.ExerciseTableDb
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseSettingsState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseMeasuredValueListState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseMeasuredValueState

class ExerciseModel(
    @DrawableRes
    private val icon: Int,
    @ColorInt
    private val color: Int,
    val title: String = "",
    private val measuredValueModel: ExerciseMeasuredValueModel = ExerciseMeasuredValueModel.QUANTITY,
    val id: Int = 0,
) : Parcelable {

   val exerciseSettingsState: ExerciseSettingsState
       get() = ExerciseSettingsState(
           icon = icon,
           color = color,
           title = title,
           measuredState = ExerciseMeasuredValueListState(listOf(ExerciseMeasuredValueState(measuredValueModel, true)))
       )

   val database: ExerciseTableDb
       get() = ExerciseTableDb(icon, color, title, measuredValueModel.ordinal, id)

    constructor(parcel: Parcel) : this(
        icon = parcel.readInt(),
        color = parcel.readInt(),
        title = parcel.readString().orEmpty(),
        measuredValueModel = ExerciseMeasuredValueModel.values()[parcel.readInt()],
        id = parcel.readInt()
    )

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

    override fun toString(): String {
        return "{ icon -> $icon, color -> $color, title -> $title, measured_value_model -> $measuredValueModel, id -> $id"
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(destination: Parcel, arg: Int) {
        with(destination) {
            writeInt(icon)
            writeInt(color)
            writeString(title)
            writeInt(measuredValueModel.ordinal)
            writeInt(id)
        }
    }

    companion object CREATOR : Parcelable.Creator<ExerciseModel> {
        override fun createFromParcel(parcel: Parcel) = ExerciseModel(parcel)
        override fun newArray(size: Int) = arrayOfNulls<ExerciseModel>(size)
    }

}