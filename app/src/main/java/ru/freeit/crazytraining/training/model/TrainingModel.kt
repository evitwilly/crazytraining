package ru.freeit.crazytraining.training.model

import android.os.Parcel
import android.os.Parcelable
import ru.freeit.crazytraining.training.data.database.TrainingTableDb

class TrainingModel(
    private val millis: Long = 0L,
    private val date: String = "",
    private val rating: Float = 4f,
    private val comment: String = "",
    private val active: Boolean = true,
    val id: Int = 0
): Parcelable {

    val isEmpty: Boolean
        get() = millis == 0L && date.isBlank() && id == 0

    val isNotEmpty: Boolean
        get() = millis != 0L && date.isNotBlank() && id != 0

    val hasNotFinished: Boolean
        get() = isNotEmpty && active

    val database: TrainingTableDb
        get() = TrainingTableDb(
            millis = millis,
            date = date,
            rating = rating,
            comment = comment,
            active = active,
            id = id
        )

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().orEmpty(),
        parcel.readFloat(),
        parcel.readString().orEmpty(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    )

    fun isThisDate(date: String) = this.date == date

    fun copy(
        millis: Long = this.millis,
        date: String = this.date,
        rating: Float = this.rating,
        comment: String = this.comment,
        active: Boolean = this.active,
        id: Int = this.id
    ) = TrainingModel(
        millis = millis,
        date = date,
        rating = rating,
        comment = comment,
        active = active,
        id = id
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is TrainingModel) return false

        return millis == other.millis && date == other.date && rating == other.rating
                && comment == other.comment && active == other.active
    }

    override fun hashCode(): Int {
        var result = millis.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + rating.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + active.hashCode()
        result = 31 * result + id
        return result
    }

    override fun toString(): String {
        return "{ id=$id, millis=$millis, rating=$rating, comment=$comment, active=$active }"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(millis)
        parcel.writeString(date)
        parcel.writeFloat(rating)
        parcel.writeString(comment)
        parcel.writeByte(if (active) 1 else 0)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<TrainingModel> {
        override fun createFromParcel(parcel: Parcel) = TrainingModel(parcel)
        override fun newArray(size: Int): Array<TrainingModel?> = arrayOfNulls(size)
    }

}