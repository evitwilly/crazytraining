package ru.freeit.crazytraining.exercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.core.mocks.ExerciseListRepositoryMock
import ru.freeit.crazytraining.exercise.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.repository.ExerciseResourcesRepository
import ru.freeit.crazytraining.exercise.viewmodel_states.AddingExerciseState
import ru.freeit.crazytraining.exercise.viewmodel_states.ExerciseMeasuredValueListState
import ru.freeit.crazytraining.exercise.viewmodel_states.ExerciseMeasuredValueState
import ru.freeit.crazytraining.exercise.viewmodel_states.SettingsIconState

internal class ExerciseViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mockData = intArrayOf(1, 2, 3)

    class ExerciseResourcesRepositoryMock(private val colors: IntArray, private val icons: IntArray) : ExerciseResourcesRepository {
        override fun colors() = colors
        override fun icons() = icons
    }

    @Test
    fun `test changing states`() {
        val viewModel = ExerciseViewModel(ExerciseListRepositoryMock(), ExerciseResourcesRepositoryMock(mockData, mockData))

        val measuredState = ExerciseMeasuredValueListState(ExerciseMeasuredValueModel.measuredStates)
        assertEquals(SettingsIconState(mockData, mockData), viewModel.settingsIconState.value)
        assertEquals(AddingExerciseState(icon = mockData[0], color = mockData[0], measuredState = measuredState), viewModel.addingExerciseState.value)

        viewModel.changeTitle("exercise 1")
        assertEquals(AddingExerciseState(icon = 1, color = 1, title = "exercise 1", measuredState = measuredState), viewModel.addingExerciseState.value)

        viewModel.checkColor(2)
        assertEquals(AddingExerciseState(icon = 1, color = 2, title = "exercise 1", measuredState = measuredState), viewModel.addingExerciseState.value)

        viewModel.checkIcon(3)
        assertEquals(AddingExerciseState(icon = 3, color = 2, title = "exercise 1", measuredState = measuredState), viewModel.addingExerciseState.value)

        val newMeasuredState = ExerciseMeasuredValueState(ExerciseMeasuredValueModel.DISTANCE, true)
        viewModel.checkMeasuredState(newMeasuredState)
        assertEquals(AddingExerciseState(icon = 3, color = 2, title = "exercise 1", measuredState = measuredState.withCheckedState(newMeasuredState)), viewModel.addingExerciseState.value)
    }

}