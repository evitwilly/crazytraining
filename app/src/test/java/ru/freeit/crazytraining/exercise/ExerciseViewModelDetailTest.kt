package ru.freeit.crazytraining.exercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.core.mocks.ExerciseListRepositoryMock
import ru.freeit.crazytraining.core.rules.MainDispatcherRule
import ru.freeit.crazytraining.exercise.detail.ExerciseDetailViewModel
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseSettingsState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseUnitListState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseUnitListItemState
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseDetailState
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseListState

@OptIn(ExperimentalCoroutinesApi::class)
internal class ExerciseViewModelDetailTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule: TestRule = MainDispatcherRule()

    @Test
    fun `test argument`() {
        val viewModel = ExerciseDetailViewModel(
            argument = ExerciseModel(title = "exercise 1", unit = ExerciseUnitModel.QUANTITY, id = 1),
            listRepository = ExerciseListRepositoryMock()
        )

        assertEquals(ExerciseSettingsState(
            title = "exercise 1",
            unitListState = ExerciseUnitListState(listOf(ExerciseUnitListItemState(ExerciseUnitModel.QUANTITY, true)))
        ), viewModel.exerciseSettingsState.value)
    }

    @Test
    fun `test changing states`() {
        val viewModel = ExerciseDetailViewModel(
            argument = null,
            listRepository = ExerciseListRepositoryMock()
        )

        val measuredState = ExerciseUnitListState(ExerciseUnitModel.unitListItemStates)
        assertEquals(ExerciseSettingsState(unitListState = measuredState), viewModel.exerciseSettingsState.value)

        viewModel.changeTitle("exercise 1")
        assertEquals(ExerciseSettingsState(title = "exercise 1", unitListState = measuredState), viewModel.exerciseSettingsState.value)

        val newUnitState = ExerciseUnitListItemState(ExerciseUnitModel.DISTANCE, true)
        viewModel.checkMeasuredState(newUnitState)
        assertEquals(ExerciseSettingsState(title = "exercise 1", unitListState = measuredState.withCheckedState(newUnitState)), viewModel.exerciseSettingsState.value)
    }

    @Test
    fun `test apply button when adding new exercise`() = runTest {
        val repository = ExerciseListRepositoryMock()
        val viewModel = ExerciseDetailViewModel(
            argument = null,
            listRepository = repository
        )
        viewModel.checkMeasuredState(ExerciseUnitListItemState(ExerciseUnitModel.DISTANCE, true))

        viewModel.apply()

        assertEquals(ExerciseListState(emptyList()), repository.exercises())

        viewModel.changeTitle("exercise 1")

        viewModel.apply()

        assertEquals(ExerciseListState(listOf(ExerciseDetailState(ExerciseModel("exercise 1", ExerciseUnitModel.DISTANCE), true))), repository.exercises())
    }

    @Test
    fun `test apply button when editing existing exercise`() = runTest {
        val repository = ExerciseListRepositoryMock()
        val viewModel = ExerciseDetailViewModel(
            argument = ExerciseModel("exercise", ExerciseUnitModel.DISTANCE, 1),
            listRepository = repository
        )

        viewModel.changeTitle("exercise 2")

        viewModel.apply()

        assertEquals(ExerciseListState(listOf(ExerciseDetailState(ExerciseModel("exercise 2", ExerciseUnitModel.DISTANCE, 1), true))), repository.exercises())
    }

}