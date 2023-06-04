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
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository

internal class TrainingViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    class TestCalendarRepository(private val calendarVariable: Int) : CalendarRepository {
        override fun weekday(): Int {
            return calendarVariable
        }

        override fun weekdayMonthYearDateString(): String = ""
    }

    @Test
    fun `test title when today is training`() {
        val calendar = TestCalendarRepository(0)
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
        val calendar = TestCalendarRepository(1)
        val checkedWeekdaysRepository = CheckedWeekdaysRepository.Test(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val viewModel = TrainingViewModel(ExerciseListRepositoryMock(), calendar, checkedWeekdaysRepository)
        viewModel.updateState()

        assertEquals(R.string.weekend, viewModel.titleState.value)
    }

}