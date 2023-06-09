package ru.freeit.crazytraining.exercise.detail.viewmodel_states

import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel
import ru.freeit.crazytraining.exercise.detail.view.ExerciseUnitView

class ExerciseUnitListState(private val items: List<ExerciseUnitListItemState>) {

    val checkedUnitModel: ExerciseUnitModel
        get() = items.find { it.checked }?.model ?: ExerciseUnitModel.QUANTITY

    fun withCheckedState(newState: ExerciseUnitListItemState): ExerciseUnitListState {
        val newItems = items.map { state -> if (state.model == newState.model) newState else state.withChangedChecked(false) }
        return ExerciseUnitListState(newItems)
    }

    fun bindView(parent: CoreLinearLayout, checkListener: (state: ExerciseUnitListItemState) -> Unit) {
        parent.removeAllViews()

        val context = parent.context
        items.forEach { state ->
            val exerciseUnitView = ExerciseUnitView(context)
            exerciseUnitView.checked = state.checked
            exerciseUnitView.changeMeasuredValueModel(state.model)
            exerciseUnitView.setOnClickListener { checkListener.invoke(state.withChangedChecked(true)) }
            exerciseUnitView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(12)))
            parent.addView(exerciseUnitView)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseUnitListState) return false

        return items == other.items
    }

    override fun hashCode(): Int = items.hashCode()

}