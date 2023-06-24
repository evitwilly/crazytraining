package ru.freeit.crazytraining.exercise.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.TextView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetTableDb
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel

class ExerciseSetModel(
    private val id: Int = 0,
    private val amount: Int,
    private val millis: Long,
    private val exerciseId: Int = 0,
    private val measuredValueModel: ExerciseMeasuredValueModel,
    private val dateString: String = "",
    private val timeString: String = ""
): Parcelable {

    val database: ExerciseSetTableDb
        get() = ExerciseSetTableDb(
            id = id,
            amount = amount,
            millis = millis,
            exercise_id = exerciseId,
            measuredValueModel = measuredValueModel.ordinal,
            dateString = dateString,
            timeString = timeString
        )

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readInt(),
        ExerciseMeasuredValueModel.values()[parcel.readInt()],
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty()
    )

    fun isThisExercise(model: ExerciseModel) = model.id == exerciseId

    fun bindAmount(view: TextView, number: Int) {
        view.text = view.context.resources.getQuantityString(R.plurals.set_title, amount, number, amount)
    }

    fun bindTime(view: TextView) {
        view.text = timeString
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeInt(id)
        writeInt(amount)
        writeLong(millis)
        writeInt(exerciseId)
        writeInt(measuredValueModel.ordinal)
        writeString(dateString)
        writeString(timeString)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ExerciseSetModel> {
        override fun createFromParcel(parcel: Parcel) = ExerciseSetModel(parcel)
        override fun newArray(size: Int): Array<ExerciseSetModel?> =arrayOfNulls(size)
    }

}
