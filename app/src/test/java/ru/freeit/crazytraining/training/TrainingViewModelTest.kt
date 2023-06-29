package ru.freeit.crazytraining.training

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.mocks.*
import ru.freeit.crazytraining.core.models.WeekdayModel
import ru.freeit.crazytraining.core.rules.MainDispatcherRule
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel
import ru.freeit.crazytraining.training.viewmodel_states.TrainingListState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingTextState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingWeekendState

internal class TrainingViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule: TestRule = MainDispatcherRule()

    @Test
    fun `test title when today is training`() {
        val date = "Saturday, 3 June, 2023"
        val calendar = CalendarRepositoryMock(2, date)
        val checkedWeekdaysRepository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseListRepositoryMock(),
            ExerciseSetsRepositoryMock(),
            calendar,
            checkedWeekdaysRepository
        )
        viewModel.updateState()

        assertEquals(TrainingTextState(R.string.training, date), viewModel.textState.value)
        assertEquals(TrainingWeekendState.Training, viewModel.weekendState.value)
    }

    @Test
    fun `test title when today is weekend`() {
        val calendar = CalendarRepositoryMock(1)
        val checkedWeekdaysRepository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseListRepositoryMock(),
            ExerciseSetsRepositoryMock(),
            calendar,
            checkedWeekdaysRepository
        )
        viewModel.updateState()

        assertEquals(TrainingTextState(R.string.weekend, ""), viewModel.textState.value)
        assertEquals(TrainingWeekendState.Weekend, viewModel.weekendState.value)
    }

    @Test
    fun `test addSet`() {
        val exerciseSetsRepository = ExerciseSetsRepositoryMock()

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseListRepositoryMock(),
            exerciseSetsRepository,
            CalendarRepositoryMock(
                calendarVariable = 1,
                millis = 100,
                timeString = "22:22",
                dateString = "11.11.1111"
            ),
            CheckedWeekdaysRepositoryMock(WeekdayModel.values().toList())
        )

        viewModel.cacheExercise(ExerciseModel(id = 10, measuredValueModel = ExerciseMeasuredValueModel.TIME))

        viewModel.addSet(180)

        val expected = listOf(ExerciseSetModel(
            amount = 180,
            exerciseId = 10,
            measuredValueModel = ExerciseMeasuredValueModel.TIME,
            millis = 100,
            dateString = "11.11.1111",
            timeString = "22:22"
        ))
        assertEquals(expected, exerciseSetsRepository.data)
        assertEquals(TrainingListState(emptyList()), viewModel.trainingState.value)
    }

    @Test
    fun `test removeSet`() {
        val exerciseSetsRepository = ExerciseSetsRepositoryMock()
        val exerciseSet = ExerciseSetModel(
            id = 1000,
            amount = 1000,
            millis = 200,
            exerciseId = 5,
            measuredValueModel = ExerciseMeasuredValueModel.DISTANCE
        )
        exerciseSetsRepository.data.add(exerciseSet)

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseListRepositoryMock(),
            exerciseSetsRepository,
            CalendarRepositoryMock(calendarVariable = 1),
            CheckedWeekdaysRepositoryMock(WeekdayModel.values().toList())
        )

        viewModel.cacheExerciseSet(exerciseSet)

        viewModel.removeSet()

        assertEquals(emptyList<ExerciseSetModel>(), exerciseSetsRepository.data)
        assertEquals(TrainingListState(emptyList()), viewModel.trainingState.value)
    }

    @Test
    fun `test plusSimilarSet`() {
        val exerciseSetsRepository = ExerciseSetsRepositoryMock()
        val exerciseSet = ExerciseSetModel(
            id = 1000,
            amount = 1000,
            millis = 200,
            exerciseId = 5,
            measuredValueModel = ExerciseMeasuredValueModel.DISTANCE,
            dateString = "22:22:2222",
            timeString = "22:22"
        )
        exerciseSetsRepository.data.add(exerciseSet)

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseListRepositoryMock(),
            exerciseSetsRepository,
            CalendarRepositoryMock(
                calendarVariable = 1,
                millis = 500,
                dateString = "11:11:1111",
                timeString = "11:11"
            ),
            CheckedWeekdaysRepositoryMock(WeekdayModel.values().toList())
        )

        viewModel.plusSimilarSet(exerciseSet)

        val expected = listOf(
            exerciseSet,
            ExerciseSetModel(
                id = 0,
                amount = 1000,
                millis = 500,
                exerciseId = 5,
                measuredValueModel = ExerciseMeasuredValueModel.DISTANCE,
                dateString = "11:11:1111",
                timeString = "11:11"
            )
        )
        assertEquals(expected, exerciseSetsRepository.data)
        assertEquals(TrainingListState(emptyList()), viewModel.trainingState.value)
    }

}