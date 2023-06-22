package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.core.repository.CalendarRepository

class CalendarRepositoryMock(
    private val calendarVariable: Int = 0,
    private val date: String = ""
) : CalendarRepository {

        override fun weekday(dateTime: Long): Int {
            return calendarVariable
        }

        override fun weekdayMonthYearDateString(dateTime: Long): String = date

        override fun timeStringFrom(millis: Long): String = ""

        override fun dateStringFrom(millis: Long): String = ""

        override fun dateTimeMillis(): Long = 0

    }