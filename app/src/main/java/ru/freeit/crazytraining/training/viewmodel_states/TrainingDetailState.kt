package ru.freeit.crazytraining.training.viewmodel_states

import android.view.Gravity
import androidx.core.view.isVisible
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.extensions.*
import ru.freeit.crazytraining.core.theming.layout.components.CoreFrameLayout
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextType
import ru.freeit.crazytraining.core.theming.view.CoreImageButtonView
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

class TrainingDetailState(
    val model: ExerciseModel,
    private val sets: List<ExerciseSetModel>
) {

    fun bindSetsViews(parent: CoreLinearLayout, removeListener: (ExerciseSetModel) -> Unit) {
        parent.removeAllViews()
        parent.isVisible = sets.isNotEmpty()

        val context = parent.context
        sets.forEachIndexed { index, model ->

            val layoutView = CoreFrameLayout(context, backgroundColor = ColorType.transparent)
            layoutView.padding(context.dp(4))
            layoutView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(4)))
            parent.addView(layoutView)

            val textView = CoreTextView(context, textStyle = TextType.Body2)
            textView.layoutParams(frameLayoutParams().wrap().gravity(Gravity.CENTER_VERTICAL))
            layoutView.addView(textView)

            val dateView = CoreTextView(context, textStyle = TextType.Caption2)
            dateView.layoutParams(frameLayoutParams().wrap().marginEnd(context.dp(40)).gravity(Gravity.END or Gravity.CENTER_VERTICAL))
            model.bindTime(dateView)
            layoutView.addView(dateView)

            val removeButton = CoreImageButtonView(context)
            removeButton.setImageResource(R.drawable.ic_close)
            removeButton.padding(context.dp(4))
            removeButton.layoutParams(frameLayoutParams().width(context.dp(24)).height(context.dp(24))
                .gravity(Gravity.END or Gravity.CENTER_VERTICAL))
            removeButton.setOnClickListener { removeListener.invoke(model) }
            layoutView.addView(removeButton)

            model.bindAmount(textView, index+1)
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