package ru.freeit.crazytraining.core.repository

interface CalendarRepository {

    fun weekday(dateTime: Long = System.currentTimeMillis()): Int
    fun weekdayMonthYearDateString(dateTime: Long = System.currentTimeMillis()): String

    fun timeStringFrom(millis: Long = System.currentTimeMillis()): String
    fun dateStringFrom(millis: Long = System.currentTimeMillis()): String

    fun nowDateTimeMillis(): Long

    fun dateStringWithoutDays(millis: Long = System.currentTimeMillis(), days: Int): String

}