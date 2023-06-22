package ru.freeit.crazytraining.settings.viewmodel_states

import android.view.ViewGroup
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.extensions.viewGroupLayoutParams
import ru.freeit.crazytraining.core.theming.view.ChipView

class WeekdayListState(private val states: List<WeekdayState>) {

    fun withStateChanged(newState: WeekdayState): WeekdayListState {
        val newStates = states.map { state ->
            if (state.model == newState.model) newState else state
        }

        return WeekdayListState(newStates)
    }

    fun bindView(weekdaysLayoutView: ViewGroup, stateListener: (state: WeekdayState) -> Unit) {
        val context = weekdaysLayoutView.context

        if (weekdaysLayoutView.childCount != states.size) {
            weekdaysLayoutView.removeAllViews()
            states.forEach { state ->
                val chipView = ChipView(context)
                chipView.text = context.getString(state.model.stringResource)
                chipView.checked = state.checked
                chipView.changeCheckedListener { checked -> stateListener.invoke(state.withChecked(checked)) }
                chipView.padding(horizontal = context.dp(12), vertical = context.dp(8))
                chipView.layoutParams(viewGroupLayoutParams().wrap())
                weekdaysLayoutView.addView(chipView)
            }
        } else {
            states.forEachIndexed { index, state ->
                val chipView = weekdaysLayoutView.getChildAt(index) as? ChipView
                chipView?.checked = state.checked
            }
        }

    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is WeekdayListState) return false

        return states == other.states
    }

    override fun hashCode(): Int = states.hashCode()

}