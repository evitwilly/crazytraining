package ru.freeit.crazytraining.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.core.models.WeekdayModel
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayListState
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayState


internal class SettingsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun test() {
        val viewModel = SettingsViewModel()

        val weekdays = WeekdayModel.values()

        val expected1 = WeekdayListState(weekdays.map { WeekdayState(it, false) })
        assertEquals(expected1, viewModel.state.value)

        viewModel.changeWeekdayState(WeekdayState(WeekdayModel.FRIDAY, true))

        val expected2 = WeekdayListState(weekdays.map { WeekdayState(it, it == WeekdayModel.FRIDAY) })
        assertEquals(expected2, viewModel.state.value)

        viewModel.changeWeekdayState(WeekdayState(WeekdayModel.SATURDAY, true))

        val expected3 = WeekdayListState(weekdays.map { WeekdayState(it, it == WeekdayModel.FRIDAY || it == WeekdayModel.SATURDAY) })
        assertEquals(expected3, viewModel.state.value)

        viewModel.changeWeekdayState(WeekdayState(WeekdayModel.FRIDAY, false))
        viewModel.changeWeekdayState(WeekdayState(WeekdayModel.SATURDAY, false))

        val expected4 = WeekdayListState(weekdays.map { WeekdayState(it, false) })
        assertEquals(expected4, viewModel.state.value)
    }

}