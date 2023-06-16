package ru.freeit.crazytraining.exercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.core.mocks.ExerciseListRepositoryMock
import ru.freeit.crazytraining.core.mocks.ExerciseResourcesRepositoryMock
import ru.freeit.crazytraining.core.rules.MainDispatcherRule
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.exercise.detail.ExerciseDetailViewModel
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseSettingsState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseMeasuredValueListState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseMeasuredValueState

@OptIn(ExperimentalCoroutinesApi::class)
internal class ExerciseViewModelDetailTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule: TestRule = MainDispatcherRule()

    private val mockData = intArrayOf(1, 2, 3)

    @Test
    fun `test argument`() {
        val viewModel = ExerciseDetailViewModel(
            argument = ExerciseModel(1, 2, "exercise 1", ExerciseMeasuredValueModel.QUANTITY, emptyList(), 1),
            listRepository = ExerciseListRepositoryMock(),
            resourcesRepository = ExerciseResourcesRepositoryMock(mockData, mockData)
        )

        assertEquals(ExerciseSettingsState(
            1,
            2,
            "exercise 1",
            ExerciseMeasuredValueListState(listOf(ExerciseMeasuredValueState(ExerciseMeasuredValueModel.QUANTITY, true)))
        ), viewModel.exerciseSettingsState.value)
    }

    @Test
    fun `test changing states`() {
        val viewModel = ExerciseDetailViewModel(
            argument = null,
            listRepository = ExerciseListRepositoryMock(),
            resourcesRepository = ExerciseResourcesRepositoryMock(mockData, mockData)
        )

        val measuredState = ExerciseMeasuredValueListState(ExerciseMeasuredValueModel.measuredStates)
        assertEquals(ExerciseSettingsState(icon = mockData[0], color = mockData[0], measuredState = measuredState), viewModel.exerciseSettingsState.value)

        viewModel.changeTitle("exercise 1")
        assertEquals(ExerciseSettingsState(icon = 1, color = 1, title = "exercise 1", measuredState = measuredState), viewModel.exerciseSettingsState.value)

        viewModel.checkColor(2)
        assertEquals(ExerciseSettingsState(icon = 1, color = 2, title = "exercise 1", measuredState = measuredState), viewModel.exerciseSettingsState.value)

        viewModel.checkIcon(3)
        assertEquals(ExerciseSettingsState(icon = 3, color = 2, title = "exercise 1", measuredState = measuredState), viewModel.exerciseSettingsState.value)

        val newMeasuredState = ExerciseMeasuredValueState(ExerciseMeasuredValueModel.DISTANCE, true)
        viewModel.checkMeasuredState(newMeasuredState)
        assertEquals(ExerciseSettingsState(icon = 3, color = 2, title = "exercise 1", measuredState = measuredState.withCheckedState(newMeasuredState)), viewModel.exerciseSettingsState.value)
    }

    @Test
    fun `test apply button when adding new exercise`() = runTest {
        val repository = ExerciseListRepositoryMock()
        val viewModel = ExerciseDetailViewModel(
            argument = null,
            listRepository = repository,
            resourcesRepository = ExerciseResourcesRepositoryMock(mockData, mockData)
        )
        viewModel.checkColor(1)
        viewModel.checkIcon(1)
        viewModel.checkMeasuredState(ExerciseMeasuredValueState(ExerciseMeasuredValueModel.DISTANCE, true))

        viewModel.apply()

        assertEquals(emptyList<ExerciseModel>(), repository.exercises())

        viewModel.changeTitle("exercise 1")

        viewModel.apply()

        assertEquals(listOf(ExerciseModel(1, 1, "exercise 1", ExerciseMeasuredValueModel.DISTANCE)), repository.exercises())
    }

    @Test
    fun `test apply button when editing existing exercise`() = runTest {
        val repository = ExerciseListRepositoryMock()
        val viewModel = ExerciseDetailViewModel(
            argument = ExerciseModel(1, 2, "exercise", ExerciseMeasuredValueModel.DISTANCE, emptyList(), 1),
            listRepository = repository,
            resourcesRepository = ExerciseResourcesRepositoryMock(mockData, mockData)
        )
        viewModel.checkIcon(2)

        viewModel.apply()

        assertEquals(listOf(ExerciseModel(2, 2, "exercise", ExerciseMeasuredValueModel.DISTANCE, emptyList(), 1)), repository.exercises())
    }

}