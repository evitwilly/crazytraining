package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.exercise.data.repository.ExerciseActiveRepository

class ExerciseActiveRepositoryMock(params: List<Int> = emptyList()) : ExerciseActiveRepository {

    private val data = hashMapOf<Int, Boolean>()

    init {
        params.forEach { exerciseId -> data[exerciseId] = true }
    }

    fun changeItems(items: List<Int>) {
        data.clear()
        items.forEach { exerciseId -> data[exerciseId] = true }
    }

    override suspend fun checkActive(exerciseId: Int, active: Boolean) {
        if (active) {
            data[exerciseId] = true
        } else {
            data.remove(exerciseId)
        }
    }

    override fun activeExerciseIds(): List<Int> = data.keys.toList()

}