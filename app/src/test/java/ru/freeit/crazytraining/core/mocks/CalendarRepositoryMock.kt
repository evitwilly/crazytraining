package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.core.repository.CalendarRepository

class CalendarRepositoryMock(
    private val calendarVariable: Int = 0,
    private val date: String = "",
    private val millis: Long = 0,
    private val timeString: String = "",
    private val dateString: String = ""
) : CalendarRepository {

        override fun weekday(dateTime: Long): Int = calendarVariable

        override fun weekdayMonthYearDateString(dateTime: Long): String = date

        override fun timeStringFrom(millis: Long): String = timeString

        override fun dateStringFrom(millis: Long): String = dateString

        override fun dateTimeMillis(): Long = millis

    }