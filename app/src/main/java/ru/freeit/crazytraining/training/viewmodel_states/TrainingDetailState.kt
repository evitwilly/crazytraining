package ru.freeit.crazytraining.training.viewmodel_states

import android.view.Gravity
import androidx.core.view.isVisible
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.layout.components.CoreFrameLayout
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreImageButtonView
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

class TrainingDetailStateListeners(
    val addListener: (ExerciseModel) -> Unit,
    val removeListener: (ExerciseSetModel) -> Unit,
    val plusListener: (ExerciseSetModel) -> Unit,
    val minusListener: (ExerciseSetModel) -> Unit
)

class TrainingDetailState(
    val model: ExerciseModel,
    private val sets: List<ExerciseSetModel>
) {

    fun bindSetsViews(parent: CoreLinearLayout, listeners: TrainingDetailStateListeners) {
        parent.removeAllViews()
        parent.isVisible = sets.isNotEmpty()

        val formattedSets = hashMapOf<ExerciseSetModel, Int>()
        sets.sortedByDescending { it.millis }.forEach { set ->
            if (formattedSets.containsKey(set)) {
                val value = formattedSets[set] ?: 1
                formattedSets[set] = value + 1
            } else {
                formattedSets[set] = 1
            }
        }

        val context = parent.context

        formattedSets.entries.forEach { entry ->

            val layoutView = CoreFrameLayout(context, backgroundColor = ColorAttributes.transparent)
            layoutView.padding(context.dp(4))
            layoutView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(4)))
            parent.addView(layoutView)

            val buttonSize = context.dp(24)
            val buttonMargin = context.dp(8)

            val titleView = CoreTextView(context, textStyle = TextAttribute.Body2)
            titleView.layoutParams(frameLayoutParams().matchWidth().wrapHeight()
                .gravity(Gravity.CENTER_VERTICAL)
                .marginEnd(buttonSize * 2 + buttonMargin * 2))
            layoutView.addView(titleView)

            val model = entry.key
            val number = entry.value

            val resources = context.resources
            titleView.text = resources.getString(
                R.string.set_title,
                resources.getQuantityString(R.plurals.set, number, number),
                model.amountString(resources)
            )

            val plusButton = CoreImageButtonView(context)
            plusButton.setImageResource(R.drawable.ic_add)
            plusButton.padding(context.dp(4))
            plusButton.setOnClickListener { listeners.plusListener.invoke(model) }
            plusButton.layoutParams(frameLayoutParams().width(buttonSize).height(buttonSize)
                .marginEnd(buttonSize + buttonMargin)
                .gravity(Gravity.END or Gravity.CENTER_VERTICAL))
            layoutView.addView(plusButton)

            val removeButton = CoreImageButtonView(context)
            removeButton.setImageResource(if (number > 1) R.drawable.ic_minus else R.drawable.ic_close)
            removeButton.padding(context.dp(4))
            removeButton.layoutParams(frameLayoutParams().width(buttonSize).height(buttonSize)
                .gravity(Gravity.END or Gravity.CENTER_VERTICAL))
            removeButton.setOnClickListener {
                if (number > 1) {
                    listeners.minusListener.invoke(model)
                } else {
                    listeners.removeListener.invoke(model)
                }
            }
            layoutView.addView(removeButton)

        }

    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is TrainingDetailState) return false

        return model == other.model && sets == other.sets
    }

    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + sets.hashCode()
        return result
    }

}