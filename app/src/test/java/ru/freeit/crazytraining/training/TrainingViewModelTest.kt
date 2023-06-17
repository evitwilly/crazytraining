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
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.training.viewmodel_states.TrainingTextState

internal class TrainingViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule: TestRule = MainDispatcherRule()

    class CalendarRepositoryMock(
        private val calendarVariable: Int,
        private val date: String = ""
    ) : CalendarRepository {

        override fun weekday(dateTime: Long): Int {
            return calendarVariable
        }

        override fun weekdayMonthYearDateString(dateTime: Long): String = date

        override fun timeStringFrom(timeMillis: Long): String = ""

    }

    @Test
    fun `test title when today is training`() {
        val date = "Saturday, 3 June, 2023"
        val calendar = CalendarRepositoryMock(2, date)
        val checkedWeekdaysRepository = CheckedWeekdaysRepository.Test(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val viewModel = TrainingViewModel(ExerciseListRepositoryMock(), calendar, checkedWeekdaysRepository)
        viewModel.updateState()

        assertEquals(TrainingTextState(R.string.training, date), viewModel.textState.value)
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

        assertEquals(TrainingTextState(R.string.weekend, ""), viewModel.textState.value)
    }

}