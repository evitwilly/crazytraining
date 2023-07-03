package ru.freeit.crazytraining.exercise.model

import android.os.Parcel
import android.os.Parcelable
import ru.freeit.crazytraining.core.ResourcesProvider
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetTableDb
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel

class ExerciseSetModel(
    private val id: Int = 0,
    val amount: Int = 0,
    val millis: Long = 0L,
    private val exerciseId: Int = 0,
    private val unit: ExerciseUnitModel = ExerciseUnitModel.QUANTITY,
    private val dateString: String = "",
    private val timeString: String = ""
): Parcelable {

    val database: ExerciseSetTableDb
        get() = ExerciseSetTableDb(
            id = id,
            amount = amount,
            millis = millis,
            exercise_id = exerciseId,
            unit = unit.ordinal,
            dateString = dateString,
            timeString = timeString
        )

    val isNotEmpty: Boolean
        get() = amount > 0

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readInt(),
        ExerciseUnitModel.values()[parcel.readInt()],
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty()
    )

    fun copyWithSimilar(millis: Long, dateString: String, timeString: String): ExerciseSetModel {
        return ExerciseSetModel(
            amount = amount,
            millis = millis,
            exerciseId = exerciseId,
            unit = unit,
            dateString = dateString,
            timeString = timeString
        )
    }

    fun isThisExercise(model: ExerciseModel) = model.id == exerciseId

    fun amountString(resources: ResourcesProvider) = unit.amountConverterString.invoke(resources, amount)

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeInt(id)
        writeInt(amount)
        writeLong(millis)
        writeInt(exerciseId)
        writeInt(unit.ordinal)
        writeString(dateString)
        writeString(timeString)
    }

    override fun describeContents() = 0

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseSetModel) return false

        return id == other.id && amount == other.amount && millis == other.millis &&
                exerciseId == other.exerciseId && unit == other.unit &&
                dateString == other.dateString && timeString == other.timeString
    }

    override fun hashCode(): Int = amount

    companion object CREATOR : Parcelable.Creator<ExerciseSetModel> {
        override fun createFromParcel(parcel: Parcel) = ExerciseSetModel(parcel)
        override fun newArray(size: Int): Array<ExerciseSetModel?> =arrayOfNulls(size)
    }

}
