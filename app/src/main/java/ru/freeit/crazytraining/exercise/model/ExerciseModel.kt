package ru.freeit.crazytraining.exercise.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.TextView
import androidx.core.view.isVisible
import ru.freeit.crazytraining.exercise.data.database.ExerciseTableDb
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseSettingsState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseUnitListState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseUnitListItemState

class ExerciseModel(
    val title: String = "",
    val unit: ExerciseUnitModel = ExerciseUnitModel.QUANTITY,
    val id: Int = 0,
) : Parcelable {

   val exerciseSettingsState: ExerciseSettingsState
       get() = ExerciseSettingsState(
           title = title,
           unitListState = ExerciseUnitListState(listOf(ExerciseUnitListItemState(unit, true)))
       )

   val database: ExerciseTableDb
       get() = ExerciseTableDb(title, unit.ordinal, id)

    constructor(parcel: Parcel) : this(
        title = parcel.readString().orEmpty(),
        unit = ExerciseUnitModel.values()[parcel.readInt()],
        id = parcel.readInt()
    )

    fun bindTitle(view: TextView) {
       view.text = title
   }

    fun bindUnit(view: TextView) {
        val unit = unit.value
        view.isVisible = unit != -1
        if (unit != -1) {
            view.setText(unit)
        }
    }

   override fun equals(other: Any?): Boolean {
       if (this === other) return true
       if (other == null) return false
       if (other !is ExerciseModel) return false

       return title == other.title && unit == other.unit
   }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + unit.hashCode()
        return result
    }

    override fun toString(): String {
        return "{ title -> $title, unit -> $unit, id -> $id"
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(destination: Parcel, arg: Int) {
        with(destination) {
            writeString(title)
            writeInt(unit.ordinal)
            writeInt(id)
        }
    }

    companion object CREATOR : Parcelable.Creator<ExerciseModel> {
        override fun createFromParcel(parcel: Parcel) = ExerciseModel(parcel)
        override fun newArray(size: Int) = arrayOfNulls<ExerciseModel>(size)
    }

}