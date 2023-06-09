package ru.freeit.crazytraining.settings.repository

import ru.freeit.crazytraining.core.cache.PersistentStringStorage
import ru.freeit.crazytraining.core.models.WeekdayModel

class CheckedWeekdaysRepositoryImpl(private val stringStorage: PersistentStringStorage) : CheckedWeekdaysRepository {

    override fun saveCheckedWeekday(model: WeekdayModel) {
        val savedWeekdays = readCheckedWeekdays()
        if (!savedWeekdays.contains(model)) {
            val savedCheckedWeekdaysString = (savedWeekdays + setOf(model)).map { it.ordinal }.joinToString(separator)
            stringStorage.save(data_key, savedCheckedWeekdaysString)
        }
    }

    override fun removeCheckedWeekday(model: WeekdayModel) {
        val savedWeekdays = readCheckedWeekdays()
        if (savedWeekdays.contains(model)) {
            val savedCheckedWeekdaysString = (savedWeekdays - setOf(model)).map { it.ordinal }.joinToString(separator)
            stringStorage.save(data_key, savedCheckedWeekdaysString)
        }
    }

    override fun readCheckedWeekdays(): List<WeekdayModel> {
        val savedCheckedWeekdaysString = stringStorage.string(data_key, "")
        if (savedCheckedWeekdaysString.isEmpty())
            return listOf()

        val models = WeekdayModel.values()
        return savedCheckedWeekdaysString.split(separator).map { checkedWeekdayString ->
            models[checkedWeekdayString.toInt()]
        }
    }

    private companion object {
        const val data_key = "CheckedWeekdaysRepository_checked_weekday_models_key"
        const val separator = ","
    }
}