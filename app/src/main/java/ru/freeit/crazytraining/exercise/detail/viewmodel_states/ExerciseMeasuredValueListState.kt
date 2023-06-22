package ru.freeit.crazytraining.exercise.detail.viewmodel_states

import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.exercise.detail.view.ExerciseMeasuredValueView

class ExerciseMeasuredValueListState(private val items: List<ExerciseMeasuredValueState>) {

    val checkedMeasuredModel: ExerciseMeasuredValueModel
        get() = items.find { it.checked }?.model ?: ExerciseMeasuredValueModel.QUANTITY

    fun withCheckedState(newState: ExerciseMeasuredValueState): ExerciseMeasuredValueListState {
        val newItems = items.map { state -> if (state.model == newState.model) newState else state.withChangedChecked(false) }
        return ExerciseMeasuredValueListState(newItems)
    }

    fun bindView(parent: CoreLinearLayout, checkListener: (state: ExerciseMeasuredValueState) -> Unit) {
        parent.removeAllViews()

        val context = parent.context
        items.forEach { state ->
            val exerciseMeasuredValueView = ExerciseMeasuredValueView(context)
            exerciseMeasuredValueView.checked = state.checked
            exerciseMeasuredValueView.changeMeasuredValueModel(state.model)
            exerciseMeasuredValueView.setOnClickListener { checkListener.invoke(state.withChangedChecked(true)) }
            exerciseMeasuredValueView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(12)))
            parent.addView(exerciseMeasuredValueView)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseMeasuredValueListState) return false

        return items == other.items
    }

    override fun hashCode(): Int = items.hashCode()

}