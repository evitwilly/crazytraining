package ru.freeit.crazytraining.core.repository

import java.text.SimpleDateFormat
import java.util.*

interface CalendarRepository {
    fun weekday(): Int
    fun weekdayMonthYearDateString(): String

    class Base : CalendarRepository {

        override fun weekday(): Int {
            return calendar.get(Calendar.DAY_OF_WEEK)
        }

        override fun weekdayMonthYearDateString(): String {
            val newLocale = Locale.getDefault()
            if (locale.language != newLocale.language) {
                locale = newLocale
                simpleDateFormat = SimpleDateFormat(weekdayMonthYearFormat, locale)
            }
            return simpleDateFormat.format(Date())
        }

        private companion object {
            const val weekdayMonthYearFormat = "EEEE\ndd MMM, yyyy"

            val calendar = Calendar.getInstance()
            var locale = Locale.getDefault()
            var simpleDateFormat = SimpleDateFormat(weekdayMonthYearFormat, locale)
        }

    }

}