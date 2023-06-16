package ru.freeit.crazytraining.exercise.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.core.mocks.ExerciseListRepositoryMock
import ru.freeit.crazytraining.core.rules.MainDispatcherRule
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel
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
        val exercises = listOf(ExerciseModel(1, 1, "title 1", ExerciseMeasuredValueModel.QUANTITY))
        val repository = ExerciseListRepositoryMock(exercises)
        val buttons = listOf(ExerciseEditButtonState.Button(1) {})
        val viewModel = ExerciseListViewModel(repository, buttons)

        viewModel.updateState()

        val expected1 = ExerciseListState(items = exercises.map { ExerciseDetailState(it, ExerciseEditButtonViewModel(buttons)) })
        assertEquals(expected1, viewModel.exerciseListState.value)

        repository.changeItems(listOf(
            ExerciseModel(1, 1, "title 1", ExerciseMeasuredValueModel.QUANTITY),
            ExerciseModel(2, 2, "title 2", ExerciseMeasuredValueModel.DISTANCE)
        ))

        viewModel.exerciseListState.value?.items?.first()?.editButtonViewModel?.toggle()
        viewModel.updateState()

        val editButtonViewModel = ExerciseEditButtonViewModel(buttons)
        editButtonViewModel.toggle()
        val expected2 = ExerciseListState(items = listOf(
            ExerciseDetailState(ExerciseModel(1, 1, "title 1", ExerciseMeasuredValueModel.QUANTITY), editButtonViewModel),
            ExerciseDetailState(ExerciseModel(2, 2, "title 2", ExerciseMeasuredValueModel.DISTANCE), ExerciseEditButtonViewModel(buttons))
        ))
        assertEquals(expected2, viewModel.exerciseListState.value)

    }

    @Test
    fun `test remove`() {
        val exercises = listOf(
            ExerciseModel(1, 1, "title 1", ExerciseMeasuredValueModel.QUANTITY),
            ExerciseModel(2, 2, "title 2", ExerciseMeasuredValueModel.DISTANCE)
        )
        val repository = ExerciseListRepositoryMock(exercises)
        val buttons = listOf(ExerciseEditButtonState.Button(1) {})
        val viewModel = ExerciseListViewModel(repository, buttons)

        viewModel.updateState()
        viewModel.cache(ExerciseModel(1, 1, "title 1", ExerciseMeasuredValueModel.QUANTITY))
        viewModel.remove()

        val expected1 = ExerciseListState(items = listOf(
            ExerciseDetailState(
                ExerciseModel(2, 2, "title 2", ExerciseMeasuredValueModel.DISTANCE),
                ExerciseEditButtonViewModel(buttons)
            )
        ))
        assertEquals(expected1, viewModel.exerciseListState.value)

        viewModel.cache(ExerciseModel(2, 2, "title 2", ExerciseMeasuredValueModel.DISTANCE))
        viewModel.remove()

        val expected2 = ExerciseListState(items = emptyList())
        assertEquals(expected2, viewModel.exerciseListState.value)
    }

}