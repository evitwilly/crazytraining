package ru.freeit.crazytraining.exercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.core.mocks.ExerciseResourcesRepositoryMock
import ru.freeit.crazytraining.exercise.detail.ExerciseIconSettingsViewModel
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseIconSettingsListState

class ExerciseIconSettingsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `test arguments`() {
        val icons = intArrayOf(2, 4, 6, 8)
        val colors = intArrayOf(1, 3, 5, 7)
        val viewModel = ExerciseIconSettingsViewModel(ExerciseResourcesRepositoryMock(colors, icons), 8, 3)

        assertEquals(ExerciseIconSettingsListState.Icons(icons, 8, 3), viewModel.listState.value)
    }

    @Test
    fun `test toggle method`() {
        val icons = intArrayOf(2, 4, 6, 8)
        val colors = intArrayOf(1, 3, 5, 7)
        val viewModel = ExerciseIconSettingsViewModel(ExerciseResourcesRepositoryMock(colors, icons), 0, 0)

        assertEquals(ExerciseIconSettingsListState.Icons(icons, 2, 1), viewModel.listState.value)

        viewModel.toggle()

        assertEquals(ExerciseIconSettingsListState.Colors(colors, 2, 1), viewModel.listState.value)
    }

    @Test
    fun `test check methods`() {
        val icons = intArrayOf(2, 4, 6, 8)
        val colors = intArrayOf(1, 3, 5, 7)
        val viewModel = ExerciseIconSettingsViewModel(ExerciseResourcesRepositoryMock(colors, icons), 0, 0)

        viewModel.checkColor(5)

        assertEquals(ExerciseIconSettingsListState.Icons(icons, 2, 5), viewModel.listState.value)

        viewModel.checkIcon(6)

        assertEquals(ExerciseIconSettingsListState.Icons(icons, 6, 5), viewModel.listState.value)
    }

}