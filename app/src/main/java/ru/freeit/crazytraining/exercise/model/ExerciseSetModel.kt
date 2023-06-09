package ru.freeit.crazytraining.exercise.model

import ru.freeit.crazytraining.exercise.data.database.ExerciseSetTableDb

class ExerciseSetModel(
    private val amount: Int,
    private val date: Long,
    private val exerciseId: Int = 0,
    private val timeString: String = "",
) {
    val database: ExerciseSetTableDb
        get() = ExerciseSetTableDb(amount, date, exerciseId)
}
