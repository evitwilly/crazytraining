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

    override fun timeStringFrom(millis: Long): String {
        return hourMinutesSimpleDateFormat.format(millis)
    }

    override fun dateStringFrom(millis: Long): String {
        return dateSimpleDateFormat.format(millis)
    }

    override fun dateTimeMillis(): Long = System.currentTimeMillis()

    private fun checkLocale(callback: (Locale) -> Unit) {
        val newLocale = Locale.getDefault()
        if (locale.language != newLocale.language) {
            locale = newLocale
            callback.invoke(newLocale)
        }
    }

    private companion object {
        const val weekdayMonthYearFormat = "EEEE\ndd MMMM, yyyy"
        const val dateFormat = "dd.MM.yyyy"
        const val hourMinutesFormat = "HH:mm"

        val calendar = Calendar.getInstance()
        var locale = Locale.getDefault()
        var weekdayMonthYearSimpleDateFormat = SimpleDateFormat(weekdayMonthYearFormat, locale)

        val hourMinutesSimpleDateFormat = SimpleDateFormat(hourMinutesFormat, locale)
        val dateSimpleDateFormat = SimpleDateFormat(dateFormat, locale)
    }

}