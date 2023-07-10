package ru.freeit.crazytraining.settings.repository

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.freeit.crazytraining.core.cache.PersistentStringStorage
import ru.freeit.crazytraining.core.models.WeekdayModel

/**
 * Test for repository [CheckedWeekdaysRepository]
 */
internal class CheckedWeekdaysRepositoryTest {

    class StringStorage(private var data: String = "") : PersistentStringStorage {

        override fun save(key: String, value: String) { data = value }

        override fun string(key: String, default: String): String {
            return data
        }

        override fun saveNow(key: String, value: String) { data = value }

    }

    @Test
    fun `test when storage has not string data`() {
        val repository = CheckedWeekdaysRepositoryImpl(StringStorage())

        assertEquals(emptyList<WeekdayModel>(), repository.readCheckedWeekdays())
    }

    @Test
    fun `test when storage has some string data`() {
        val storage = StringStorage("${WeekdayModel.MONDAY.ordinal},${WeekdayModel.SUNDAY.ordinal}")
        val repository = CheckedWeekdaysRepositoryImpl(storage)

        assertEquals(listOf(WeekdayModel.MONDAY, WeekdayModel.SUNDAY), repository.readCheckedWeekdays())
    }

    @Test
    fun `test when checked weekdays has been changed`() {
        val repository = CheckedWeekdaysRepositoryImpl(StringStorage())

        repository.saveCheckedWeekday(WeekdayModel.WEDNESDAY)
        repository.saveCheckedWeekday(WeekdayModel.SUNDAY)
        repository.saveCheckedWeekday(WeekdayModel.MONDAY)

        assertEquals(listOf(WeekdayModel.WEDNESDAY, WeekdayModel.SUNDAY, WeekdayModel.MONDAY), repository.readCheckedWeekdays())

        repository.removeCheckedWeekday(WeekdayModel.WEDNESDAY)
        repository.removeCheckedWeekday(WeekdayModel.MONDAY)

        assertEquals(listOf(WeekdayModel.SUNDAY), repository.readCheckedWeekdays())
    }

}