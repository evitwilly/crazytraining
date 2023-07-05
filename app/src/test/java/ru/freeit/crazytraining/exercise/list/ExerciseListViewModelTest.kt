package ru.freeit.crazytraining.exercise.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.core.mocks.ExerciseListRepositoryMock
import ru.freeit.crazytraining.core.mocks.SavedInstanceStateMock
import ru.freeit.crazytraining.core.rules.MainDispatcherRule
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel
import ru.freeit.crazytraining.exercise.list.adapter.ExerciseEditButtonState
import ru.freeit.crazytraining.exercise.list.adapter.ExerciseEditButtonViewModel
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseDetailState
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseListState
import ru.freeit.crazytraining.exercise.model.ExerciseModel

@OptIn(ExperimentalCoroutinesApi::class)
internal class ExerciseListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule: TestRule = MainDispatcherRule()

    @Test
    fun `test updateState`() = runTest {
        val exercises = listOf(ExerciseModel("title 1", ExerciseUnitModel.QUANTITY))
        val repository = ExerciseListRepositoryMock(exercises)
        val buttons = listOf(ExerciseEditButtonState.Button(1) {})
        val viewModel = ExerciseListViewModel(SavedInstanceStateMock(), repository, buttons)

        viewModel.updateState()

        val expected1 = ExerciseListState(items = exercises.map { ExerciseDetailState(it, ExerciseEditButtonViewModel(buttons).apply { toggle() }) })
        assertEquals(expected1, viewModel.exerciseListState.value)

        repository.changeItems(listOf(
            ExerciseModel("title 1", ExerciseUnitModel.QUANTITY),
            ExerciseModel("title 2", ExerciseUnitModel.DISTANCE)
        ))

        viewModel.updateState()

        val editButtonViewModel = ExerciseEditButtonViewModel(buttons)
        editButtonViewModel.toggle()
        val expected2 = ExerciseListState(items = listOf(
            ExerciseDetailState(ExerciseModel("title 1", ExerciseUnitModel.QUANTITY), editButtonViewModel),
            ExerciseDetailState(ExerciseModel("title 2", ExerciseUnitModel.DISTANCE), ExerciseEditButtonViewModel(buttons))
        ))
        assertEquals(expected2, viewModel.exerciseListState.value)

    }

    @Test
    fun `test remove`() {
        val exercises = listOf(
            ExerciseModel("title 1", ExerciseUnitModel.QUANTITY),
            ExerciseModel("title 2", ExerciseUnitModel.DISTANCE)
        )
        val repository = ExerciseListRepositoryMock(exercises)
        val buttons = listOf(ExerciseEditButtonState.Button(1) {})
        val viewModel = ExerciseListViewModel(SavedInstanceStateMock(), repository, buttons)

        viewModel.updateState()
        viewModel.cache(ExerciseModel("title 1", ExerciseUnitModel.QUANTITY))
        viewModel.remove()

        val expected1 = ExerciseListState(items = listOf(
            ExerciseDetailState(
                ExerciseModel("title 2", ExerciseUnitModel.DISTANCE),
                ExerciseEditButtonViewModel(buttons).apply { toggle() }
            )
        ))
        assertEquals(expected1, viewModel.exerciseListState.value)

        viewModel.cache(ExerciseModel("title 2", ExerciseUnitModel.DISTANCE))
        viewModel.remove()

        val expected2 = ExerciseListState(items = emptyList())
        assertEquals(expected2, viewModel.exerciseListState.value)
    }

}