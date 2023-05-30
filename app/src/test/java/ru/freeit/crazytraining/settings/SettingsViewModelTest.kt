package ru.freeit.crazytraining.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.core.models.WeekdayModel
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayListState
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayState

/**
 * Test for ViewModel [SettingsViewModel]
 */
internal class SettingsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `test when cache is empty`() {
        val viewModel = SettingsViewModel(CheckedWeekdaysRepository.Test())

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
        val repository = CheckedWeekdaysRepository.Test(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.TUESDAY,
            WeekdayModel.SUNDAY
        ))
        val viewModel = SettingsViewModel(repository)

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
        val repository = CheckedWeekdaysRepository.Test()
        val viewModel = SettingsViewModel(repository)

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

}