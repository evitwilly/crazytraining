package ru.freeit.crazytraining.exercise.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.core.mocks.ExerciseActiveRepositoryMock
import ru.freeit.crazytraining.core.mocks.ExerciseListRepositoryMock
import ru.freeit.crazytraining.core.mocks.SavedInstanceStateMock
import ru.freeit.crazytraining.core.rules.MainDispatcherRule
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel
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
        val exercises = listOf(ExerciseModel(id = 1, title = "title 1", unit = ExerciseUnitModel.QUANTITY))
        val exerciseRepository = ExerciseListRepositoryMock(exercises)
        val activeRepository = ExerciseActiveRepositoryMock(params = listOf(1))

        val viewModel = ExerciseListViewModel(SavedInstanceStateMock(), exerciseRepository, activeRepository)

        viewModel.updateState()

        val expected1 = ExerciseListState(items = exercises.map { ExerciseDetailState(it, true) })
        assertEquals(expected1, viewModel.exerciseListState.value)

        exerciseRepository.changeItems(listOf(
            ExerciseModel(id = 1, title = "title 1", unit = ExerciseUnitModel.QUANTITY),
            ExerciseModel(id = 2, title = "title 2", unit = ExerciseUnitModel.DISTANCE)
        ))

        activeRepository.changeItems(listOf(1, 2))

        viewModel.updateState()

        val expected2 = ExerciseListState(items = listOf(
            ExerciseDetailState(ExerciseModel("title 1", ExerciseUnitModel.QUANTITY), true),
            ExerciseDetailState(ExerciseModel("title 2", ExerciseUnitModel.DISTANCE), true),
        ))
        assertEquals(expected2, viewModel.exerciseListState.value)
    }

    @Test
    fun `test remove`() {
        val exercises = listOf(
            ExerciseModel("title 1", ExerciseUnitModel.QUANTITY),
            ExerciseModel("title 2", ExerciseUnitModel.DISTANCE)
        )
        val exerciseRepository = ExerciseListRepositoryMock(exercises)
        val activeRepository = ExerciseActiveRepositoryMock()
        val viewModel = ExerciseListViewModel(SavedInstanceStateMock(), exerciseRepository, activeRepository)

        viewModel.updateState()
        viewModel.cache(ExerciseModel("title 1", ExerciseUnitModel.QUANTITY))
        viewModel.remove()

        val expected1 = ExerciseListState(listOf(ExerciseDetailState(ExerciseModel("title 2", ExerciseUnitModel.DISTANCE), true)))
        assertEquals(expected1, viewModel.exerciseListState.value)

        viewModel.cache(ExerciseModel("title 2", ExerciseUnitModel.DISTANCE))
        viewModel.remove()

        val expected2 = ExerciseListState(emptyList())
        assertEquals(expected2, viewModel.exerciseListState.value)
    }

    @Test
    fun `test changeStatus`() {
        val exercises = listOf(
            ExerciseModel(id = 1, title = "title 1", unit = ExerciseUnitModel.QUANTITY),
            ExerciseModel(id = 2, title = "title 2", unit = ExerciseUnitModel.DISTANCE)
        )
        val exerciseRepository = ExerciseListRepositoryMock(exercises)
        val activeRepository = ExerciseActiveRepositoryMock(listOf(1, 2))
        val viewModel = ExerciseListViewModel(SavedInstanceStateMock(), exerciseRepository, activeRepository)

        viewModel.changeStatus(ExerciseModel(id = 1, title = "title 1", unit = ExerciseUnitModel.QUANTITY), false)

        assertEquals(listOf(2), activeRepository.activeExerciseIds())

        viewModel.changeStatus(ExerciseModel(id = 2, title = "title 2", unit = ExerciseUnitModel.DISTANCE), false)

        assertEquals(emptyList<Int>(), activeRepository.activeExerciseIds())
    }

}