package ru.freeit.crazytraining.settings.viewmodel_states

import android.os.Parcel
import android.os.Parcelable
import ru.freeit.crazytraining.core.models.WeekdayModel

class WeekdayState(val model: WeekdayModel, val checked: Boolean) : Parcelable {

    constructor(parcel: Parcel) : this(
        WeekdayModel.values()[parcel.readInt()],
        parcel.readByte() != 0.toByte()
    )

    fun withChecked(checked: Boolean) = WeekdayState(model, checked)

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is WeekdayState) return false
        return model == other.model && checked == other.checked
    }

    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + checked.hashCode()
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeInt(model.ordinal)
        writeByte(if (checked) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<WeekdayState> {
        override fun createFromParcel(parcel: Parcel) = WeekdayState(parcel)
        override fun newArray(size: Int): Array<WeekdayState?> = arrayOfNulls(size)
    }

}