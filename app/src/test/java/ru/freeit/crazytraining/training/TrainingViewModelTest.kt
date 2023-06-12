package ru.freeit.crazytraining.training

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.mocks.ExerciseListRepositoryMock
import ru.freeit.crazytraining.core.models.WeekdayModel
import ru.freeit.crazytraining.core.repository.CalendarRepository
import ru.freeit.crazytraining.core.rules.MainDispatcherRule
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseListState

internal class TrainingViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule: TestRule = MainDispatcherRule()

    class CalendarRepositoryMock(private val calendarVariable: Int) : CalendarRepository {

        override fun weekday(dateTime: Long): Int {
            return calendarVariable
        }

        override fun weekdayMonthYearDateString(dateTime: Long): String = ""

        override fun timeStringFrom(timeMillis: Long): String = ""

    }

    @Test
    fun `test title when today is training`() {
        val calendar = CalendarRepositoryMock(2)
        val checkedWeekdaysRepository = CheckedWeekdaysRepository.Test(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val viewModel = TrainingViewModel(ExerciseListRepositoryMock(), calendar, checkedWeekdaysRepository)
        viewModel.updateState()

        assertEquals(R.string.training, viewModel.titleState.value)
    }

    @Test
    fun `test title when today is weekend`() {
        val calendar = CalendarRepositoryMock(1)
        val checkedWeekdaysRepository = CheckedWeekdaysRepository.Test(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val viewModel = TrainingViewModel(ExerciseListRepositoryMock(), calendar, checkedWeekdaysRepository)
        viewModel.updateState()

        assertEquals(R.string.weekend, viewModel.titleState.value)
    }

    @Test
    fun `test exercises`() {
        val exercise1 = ExerciseModel(1, 1, "exercise 1", ExerciseMeasuredValueModel.DISTANCE)
        val exercise2 = ExerciseModel(2, 2, "exercise 2", ExerciseMeasuredValueModel.QUANTITY)
        val exercises = listOf(exercise1, exercise2)
        val exerciseListRepository = ExerciseListRepositoryMock(exercises)

        val viewModel = TrainingViewModel(exerciseListRepository, CalendarRepositoryMock(1), CheckedWeekdaysRepository.Test())
        viewModel.updateState()

        assertEquals(ExerciseListState(exercises), viewModel.exerciseListState.value)
    }

}