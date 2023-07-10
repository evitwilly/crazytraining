package ru.freeit.crazytraining.exercise.data.repository

import ru.freeit.crazytraining.core.cache.PersistentStringStorage
import ru.freeit.crazytraining.core.extensions.default

class ExerciseActiveRepositoryImpl(private val stringStorage: PersistentStringStorage) : ExerciseActiveRepository {

    override suspend fun checkActive(exerciseId: Int, active: Boolean) = default {
        val existingExerciseIds = activeExerciseIds().toMutableList()
        val foundedExerciseIdIndex = existingExerciseIds.indexOf(exerciseId)
        if (foundedExerciseIdIndex != -1 && !active) {
            existingExerciseIds.remove(exerciseId)
        } else if (active) {
            existingExerciseIds.add(exerciseId)
        }
        stringStorage.saveNow(storage_data_key, existingExerciseIds.joinToString(separator))
    }

    override fun activeExerciseIds(): List<Int> {
        val activeIdsString = stringStorage.string(storage_data_key, "")
        return if (activeIdsString.isBlank()) {
            emptyList()
        } else {
            activeIdsString.split(separator).map { it.toInt() }
        }
    }

    private companion object {
        const val storage_data_key = "active_exercise_ids"
        const val separator = ","
    }

}