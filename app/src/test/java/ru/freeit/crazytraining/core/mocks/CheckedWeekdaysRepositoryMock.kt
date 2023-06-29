package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.core.models.WeekdayModel
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository

class CheckedWeekdaysRepositoryMock(params: List<WeekdayModel> = emptyList()) : CheckedWeekdaysRepository {

    private val weekdays: MutableList<WeekdayModel> = mutableListOf()

    init {
        weekdays.addAll(params)
    }

    override fun readCheckedWeekdays(): List<WeekdayModel> {
        return weekdays
    }

    override fun removeCheckedWeekday(model: WeekdayModel) {
        weekdays.remove(model)
    }

    override fun saveCheckedWeekday(model: WeekdayModel) {
        weekdays.add(model)
    }

}