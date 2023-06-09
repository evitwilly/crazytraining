package ru.freeit.crazytraining.core.repository

interface CalendarRepository {

    fun weekday(dateTime: Long = System.currentTimeMillis()): Int
    fun weekdayMonthYearDateString(dateTime: Long = System.currentTimeMillis()): String
    fun timeStringFrom(timeMillis: Long): String

}