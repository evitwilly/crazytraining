package ru.freeit.crazytraining.settings.viewmodel_states

import ru.freeit.crazytraining.core.models.WeekdayModel

class WeekdayState(val model: WeekdayModel, val checked: Boolean) {

    fun withChecked(checked: Boolean) = WeekdayState(model, checked)

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is WeekdayState) return false
        return model == other.model && checked == other.checked
    }

}