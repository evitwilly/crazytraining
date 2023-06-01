package ru.freeit.crazytraining.exercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.exercise.repository.ExerciseRepository
import ru.freeit.crazytraining.exercise.viewmodel_states.AddingExerciseState
import ru.freeit.crazytraining.exercise.viewmodel_states.SettingsIconState

internal class ExerciseViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mockData = intArrayOf(1, 2, 3)

    class Mock(private val colors: IntArray, private val icons: IntArray) : ExerciseRepository {
        override fun colors() = colors
        override fun icons() = icons
    }

    @Test
    fun `test changing states`() {
        val viewModel = ExerciseViewModel(Mock(mockData, mockData))

        assertEquals(SettingsIconState(mockData, mockData), viewModel.settingsIconState.value)
        assertEquals(AddingExerciseState(icon = mockData[0], color = mockData[0]), viewModel.addingExerciseState.value)

        viewModel.changeTitle("exercise 1")
        assertEquals(AddingExerciseState(icon = 1, color = 1, title = "exercise 1"), viewModel.addingExerciseState.value)

        viewModel.checkColor(2)
        assertEquals(AddingExerciseState(icon = 1, color = 2, title = "exercise 1"), viewModel.addingExerciseState.value)

        viewModel.checkIcon(3)
        assertEquals(AddingExerciseState(icon = 3, color = 2, title = "exercise 1"), viewModel.addingExerciseState.value)
    }

}