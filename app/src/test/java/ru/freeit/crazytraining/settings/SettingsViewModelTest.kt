package ru.freeit.crazytraining.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.core.mocks.*
import ru.freeit.crazytraining.core.models.WeekdayModel
import ru.freeit.crazytraining.core.rules.MainDispatcherRule
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayListState
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayState
import ru.freeit.crazytraining.training.model.TrainingModel

/**
 * Test for ViewModel [SettingsViewModel]
 */
internal class SettingsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule: TestRule = MainDispatcherRule()

    @Test
    fun `test when cache is empty`() {
        val viewModel = SettingsViewModel(
            SavedInstanceStateMock(),
            CheckedWeekdaysRepositoryMock(),
            CalendarRepositoryMock(),
            TrainingRepositoryMock()
        )

        val expected = WeekdayListState(
            listOf(
                WeekdayState(WeekdayModel.MONDAY, false),
                WeekdayState(WeekdayModel.TUESDAY, false),
                WeekdayState(WeekdayModel.WEDNESDAY, false),
                WeekdayState(WeekdayModel.THURSDAY, false),
                WeekdayState(WeekdayModel.FRIDAY, false),
                WeekdayState(WeekdayModel.SATURDAY, false),
                WeekdayState(WeekdayModel.SUNDAY, false)
            )
        )
        assertEquals(expected, viewModel.state.value)
    }

    @Test
    fun `test when weekdays has been saved in cache`() {
        val repository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.TUESDAY,
            WeekdayModel.SUNDAY
        ))
        val viewModel = SettingsViewModel(
            SavedInstanceStateMock(),
            repository,
            CalendarRepositoryMock(),
            TrainingRepositoryMock()
        )

        val expected = WeekdayListState(
            listOf(
                WeekdayState(WeekdayModel.MONDAY, true),
                WeekdayState(WeekdayModel.TUESDAY, true),
                WeekdayState(WeekdayModel.WEDNESDAY, false),
                WeekdayState(WeekdayModel.THURSDAY, false),
                WeekdayState(WeekdayModel.FRIDAY, false),
                WeekdayState(WeekdayModel.SATURDAY, false),
                WeekdayState(WeekdayModel.SUNDAY, true)
            )
        )
        assertEquals(expected, viewModel.state.value)
    }

    @Test
    fun `test when weekday state has been changed`() {
        val repository = CheckedWeekdaysRepositoryMock()
        val viewModel = SettingsViewModel(
            SavedInstanceStateMock(),
            repository,
            CalendarRepositoryMock(),
            TrainingRepositoryMock()
        )

        viewModel.changeWeekdayState(WeekdayState(WeekdayModel.TUESDAY, true))
        viewModel.changeWeekdayState(WeekdayState(WeekdayModel.THURSDAY, true))
        viewModel.changeWeekdayState(WeekdayState(WeekdayModel.SUNDAY, true))

        val expected1 = WeekdayListState(
            listOf(
                WeekdayState(WeekdayModel.MONDAY, false),
                WeekdayState(WeekdayModel.TUESDAY, true),
                WeekdayState(WeekdayModel.WEDNESDAY, false),
                WeekdayState(WeekdayModel.THURSDAY, true),
                WeekdayState(WeekdayModel.FRIDAY, false),
                WeekdayState(WeekdayModel.SATURDAY, false),
                WeekdayState(WeekdayModel.SUNDAY, true)
            )
        )
        assertEquals(expected1, viewModel.state.value)

        viewModel.changeWeekdayState(WeekdayState(WeekdayModel.TUESDAY, false))
        viewModel.changeWeekdayState(WeekdayState(WeekdayModel.SUNDAY, false))

        val expected2 = WeekdayListState(
            listOf(
                WeekdayState(WeekdayModel.MONDAY, false),
                WeekdayState(WeekdayModel.TUESDAY, false),
                WeekdayState(WeekdayModel.WEDNESDAY, false),
                WeekdayState(WeekdayModel.THURSDAY, true),
                WeekdayState(WeekdayModel.FRIDAY, false),
                WeekdayState(WeekdayModel.SATURDAY, false),
                WeekdayState(WeekdayModel.SUNDAY, false)
            )
        )
        assertEquals(expected2, viewModel.state.value)
    }

    @Test
    fun `test when today is training and it has not finished`() {

        val trainingRepository =  TrainingRepositoryMock(trainings = listOf(TrainingModel(id = 1, millis = 1000, date = "11.11.2111", active = true)))

        val viewModel = SettingsViewModel(
            SavedInstanceStateMock(),
            CheckedWeekdaysRepositoryMock(mutableListOf(
                WeekdayModel.MONDAY,
                WeekdayModel.TUESDAY,
                WeekdayModel.SUNDAY
            )),
            CalendarRepositoryMock(calendarVariable = 2, dateString = "11.11.2111"),
            trainingRepository
        )

        viewModel.changeWeekdayState(WeekdayState(WeekdayModel.MONDAY, false))

        assertEquals(true, viewModel.acceptDialogState.value)
        assertEquals(WeekdayListState(
            listOf(
                WeekdayState(WeekdayModel.MONDAY, true),
                WeekdayState(WeekdayModel.TUESDAY, true),
                WeekdayState(WeekdayModel.WEDNESDAY, false),
                WeekdayState(WeekdayModel.THURSDAY, false),
                WeekdayState(WeekdayModel.FRIDAY, false),
                WeekdayState(WeekdayModel.SATURDAY, false),
                WeekdayState(WeekdayModel.SUNDAY, true)
            )
        ), viewModel.state.value)

        viewModel.dialogOkClick()

        assertEquals(emptyList<TrainingModel>(), trainingRepository.items)
        assertEquals(WeekdayListState(
            listOf(
                WeekdayState(WeekdayModel.MONDAY, false),
                WeekdayState(WeekdayModel.TUESDAY, true),
                WeekdayState(WeekdayModel.WEDNESDAY, false),
                WeekdayState(WeekdayModel.THURSDAY, false),
                WeekdayState(WeekdayModel.FRIDAY, false),
                WeekdayState(WeekdayModel.SATURDAY, false),
                WeekdayState(WeekdayModel.SUNDAY, true)
            )
        ), viewModel.state.value)
    }

}