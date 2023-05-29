package ru.freeit.crazytraining.settings.viewmodel_states

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.freeit.crazytraining.core.models.WeekdayModel

internal class WeekdayListStateTest {

    @Test
    fun `test equals`() {
        val weekdayListState1 = WeekdayListState(listOf(WeekdayState(WeekdayModel.SATURDAY, true)))
        val weekdayListState2 = WeekdayListState(listOf(WeekdayState(WeekdayModel.SATURDAY, false)))
        assertEquals(false, weekdayListState1 == weekdayListState2)

        val weekdayListState3 = WeekdayListState(listOf(WeekdayState(WeekdayModel.MONDAY, true)))
        val weekdayListState4 = WeekdayListState(listOf(WeekdayState(WeekdayModel.SATURDAY, true)))
        assertEquals(false, weekdayListState3 == weekdayListState4)

        val weekdayListState5 = WeekdayListState(listOf(WeekdayState(WeekdayModel.SUNDAY, true)))
        val weekdayListState6 = WeekdayListState(listOf(WeekdayState(WeekdayModel.SUNDAY, true)))
        assertEquals(true, weekdayListState5 == weekdayListState6)
    }

    @Test
    fun `test when state has been changed`() {
        val weekdayListState1 = WeekdayListState(listOf(
            WeekdayState(WeekdayModel.MONDAY, false),
            WeekdayState(WeekdayModel.TUESDAY, false),
            WeekdayState(WeekdayModel.WEDNESDAY, true)
        ))

        val actual = weekdayListState1.withStateChanged(WeekdayState(WeekdayModel.TUESDAY, true))

        val expected = WeekdayListState(listOf(
            WeekdayState(WeekdayModel.MONDAY, false),
            WeekdayState(WeekdayModel.TUESDAY, true),
            WeekdayState(WeekdayModel.WEDNESDAY, true)
        ))
        assertEquals(expected, actual)
    }

}