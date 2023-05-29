package ru.freeit.crazytraining.settings.viewmodel_states

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.freeit.crazytraining.core.models.WeekdayModel

internal class WeekdayStateTest {

    @Test
    fun `test equals`() {
        val weekdayState1 = WeekdayState(WeekdayModel.SUNDAY, true)
        val weekdayState2 = WeekdayState(WeekdayModel.SUNDAY, false)

        assertEquals(false, weekdayState1 == weekdayState2)

        val weekdayState3 = WeekdayState(WeekdayModel.SATURDAY, true)
        val weekdayState4 = WeekdayState(WeekdayModel.SUNDAY, true)

        assertEquals(false, weekdayState3 == weekdayState4)

        val weekdayState5 = WeekdayState(WeekdayModel.THURSDAY, false)
        val weekdayState6 = WeekdayState(WeekdayModel.THURSDAY, false)

        assertEquals(true, weekdayState5 == weekdayState6)
    }

    @Test
    fun `test when state has been changed`() {
        val weekdayState1 = WeekdayState(WeekdayModel.SUNDAY, true)

        val actual1 = weekdayState1.withChecked(false)
        val expected1 = WeekdayState(WeekdayModel.SUNDAY, false)
        assertEquals(expected1, actual1)

        val weekdayState2 = WeekdayState(WeekdayModel.MONDAY, false)

        val actual2 = weekdayState2.withChecked(true)
        val expected2 = WeekdayState(WeekdayModel.MONDAY, true)
        assertEquals(expected2, actual2)
    }

}