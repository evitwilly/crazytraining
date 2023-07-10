package ru.freeit.crazytraining.settings.repository

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.freeit.crazytraining.core.cache.PersistentStringStorage
import ru.freeit.crazytraining.core.models.WeekdayModel

internal class CheckedWeekdaysRepositoryImplTest {

    class PersistentStringStorageMock(var data: String = "") : PersistentStringStorage {
        override fun save(key: String, value: String) { data = value }
        override fun saveNow(key: String, value: String) { data = value }
        override fun string(key: String, default: String) = data
    }

    @Test
    fun `test save checked weekday`() {
        val storage = PersistentStringStorageMock()
        val repository = CheckedWeekdaysRepositoryImpl(storage)
        repository.saveCheckedWeekday(WeekdayModel.TUESDAY)

        assertEquals("${WeekdayModel.TUESDAY.ordinal}", storage.data)

        repository.saveCheckedWeekday(WeekdayModel.SATURDAY)

        assertEquals("${WeekdayModel.TUESDAY.ordinal}$sep${WeekdayModel.SATURDAY.ordinal}", storage.data)

        repository.saveCheckedWeekday(WeekdayModel.TUESDAY)

        assertEquals("${WeekdayModel.TUESDAY.ordinal}$sep${WeekdayModel.SATURDAY.ordinal}", storage.data)
    }

    @Test
    fun `test remove checked weekday`() {
        val storage = PersistentStringStorageMock(
            "${WeekdayModel.MONDAY.ordinal}$sep${WeekdayModel.TUESDAY.ordinal}$sep${WeekdayModel.THURSDAY.ordinal}$sep${WeekdayModel.SUNDAY.ordinal}"
        )
        val repository = CheckedWeekdaysRepositoryImpl(storage)

        repository.removeCheckedWeekday(WeekdayModel.MONDAY)

        assertEquals("${WeekdayModel.TUESDAY.ordinal}$sep${WeekdayModel.THURSDAY.ordinal}$sep${WeekdayModel.SUNDAY.ordinal}", storage.data)

        repository.removeCheckedWeekday(WeekdayModel.SUNDAY)

        assertEquals("${WeekdayModel.TUESDAY.ordinal}$sep${WeekdayModel.THURSDAY.ordinal}", storage.data)

        repository.removeCheckedWeekday(WeekdayModel.TUESDAY)
        repository.removeCheckedWeekday(WeekdayModel.THURSDAY)

        assertEquals("", storage.data)

        repository.removeCheckedWeekday(WeekdayModel.SATURDAY)

        assertEquals("", storage.data)
    }

    @Test
    fun `test read checked weekdays`() {
        val storage = PersistentStringStorageMock()
        val repository = CheckedWeekdaysRepositoryImpl(storage)

        assertEquals(emptyList<WeekdayModel>(), repository.readCheckedWeekdays())

        storage.data = "${WeekdayModel.MONDAY.ordinal}$sep${WeekdayModel.WEDNESDAY.ordinal}$sep${WeekdayModel.FRIDAY.ordinal}"

        assertEquals(listOf(WeekdayModel.MONDAY, WeekdayModel.WEDNESDAY, WeekdayModel.FRIDAY), repository.readCheckedWeekdays())
    }

    private companion object {
        const val sep = ","
    }

}