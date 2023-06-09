package ru.freeit.crazytraining.core.repository

import java.text.SimpleDateFormat
import java.util.*

class CalendarRepositoryImpl : CalendarRepository {

    override fun weekday(dateTime: Long): Int {
        calendar.timeInMillis = dateTime
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    override fun weekdayMonthYearDateString(dateTime: Long): String {
        checkLocale { locale -> weekdayMonthYearSimpleDateFormat =
            SimpleDateFormat(weekdayMonthYearFormat, locale)
        }
        return weekdayMonthYearSimpleDateFormat.format(Date())
    }

    override fun timeStringFrom(timeMillis: Long): String {
        checkLocale {  locale -> hourMinutesSimpleDateFormat =
            SimpleDateFormat(hourMinutesFormat, locale)
        }
        return hourMinutesSimpleDateFormat.format(timeMillis)
    }

    private fun checkLocale(callback: (Locale) -> Unit) {
        val newLocale = Locale.getDefault()
        if (locale.language != newLocale.language) {
            locale = newLocale
            callback.invoke(newLocale)
        }
    }

    private companion object {
        const val weekdayMonthYearFormat = "EEEE\ndd MMM, yyyy"
        const val hourMinutesFormat = "HH:mm"

        val calendar = Calendar.getInstance()
        var locale = Locale.getDefault()
        var weekdayMonthYearSimpleDateFormat = SimpleDateFormat(weekdayMonthYearFormat, locale)
        var hourMinutesSimpleDateFormat = SimpleDateFormat(hourMinutesFormat, locale)
    }

}