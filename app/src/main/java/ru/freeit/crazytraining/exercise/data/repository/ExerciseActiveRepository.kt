package ru.freeit.crazytraining.exercise.data.repository

interface ExerciseActiveRepository {
    suspend fun checkActive(exerciseId: Int, active: Boolean)
    fun activeExerciseIds(): List<Int>
}