package ru.freeit.crazytraining.core.repository

import java.util.*

interface CalendarRepository {
    fun weekday(): Int

    class Base : CalendarRepository {

        override fun weekday(): Int {
            return calendar.get(Calendar.DAY_OF_WEEK)
        }

        private companion object {
            val calendar = Calendar.getInstance()
        }

    }

}