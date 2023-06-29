package ru.freeit.crazytraining.settings.repository

import ru.freeit.crazytraining.core.models.WeekdayModel


interface CheckedWeekdaysRepository {
    fun saveCheckedWeekday(model: WeekdayModel)
    fun removeCheckedWeekday(model: WeekdayModel)
    fun readCheckedWeekdays(): List<WeekdayModel>
}