package ru.freeit.crazytraining.training.model

import ru.freeit.crazytraining.training.data.database.TrainingTableDb

class TrainingModel(
    private val millis: Long = 0L,
    private val date: String = "",
    private val rating: Float = 4f,
    private val comment: String = "",
    private val active: Boolean = true,
    val id: Int = 0
) {

    val isEmpty: Boolean
        get() = millis == 0L && date.isBlank() && id == 0

    val hasNotFinished: Boolean
        get() = !isEmpty && active

    val database: TrainingTableDb
        get() = TrainingTableDb(
            millis = millis,
            date = date,
            rating = rating,
            comment = comment,
            active = active,
            id = id
        )

    fun isThisDate(date: String) = this.date == date

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is TrainingModel) return false

        return millis == other.millis && date == other.date && rating == other.rating
                && comment == other.comment
    }

    override fun hashCode(): Int {
        var result = millis.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + rating.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + id
        return result
    }

}