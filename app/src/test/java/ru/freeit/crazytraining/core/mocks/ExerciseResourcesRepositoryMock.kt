package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.exercise.detail.repository.ExerciseResourcesRepository

class ExerciseResourcesRepositoryMock(private val colors: IntArray, private val icons: IntArray) : ExerciseResourcesRepository {
        override fun colors() = colors
        override fun icons() = icons
    }