package ru.freeit.crazytraining.exercise.list.adapter

import android.widget.ImageView
import android.widget.LinearLayout
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseEditButtonState(
    private val imageResource: Int,
    private val buttons: List<Button>
) {

    fun bindImageView(imageView: ImageView) {
        imageView.setImageResource(imageResource)
    }

    fun bindButtons(parent: LinearLayout, model: ExerciseModel) {
        val context = parent.context
        parent.removeAllViews()
        buttons.forEachIndexed { index, buttonModel ->
            val buttonView = CoreButton(
                ctx = context,
                cornerRadiusType = CornerRadiusType.small,
                cornerTreatmentStrategy = CornerTreatmentStrategy.AllRounded()
            )
            buttonView.setText(buttonModel.stringResource)
            buttonView.setOnClickListener { buttonModel.clickListener.invoke(model) }
            buttonView.padding(horizontal = context.dp(8), vertical = context.dp(4))
            buttonView.layoutParams(linearLayoutParams().wrap().marginStart(if (index > 0) context.dp(8) else 0))
            parent.addView(buttonView)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseEditButtonState) return false

        return imageResource == other.imageResource && buttons == other.buttons
    }

    override fun hashCode(): Int {
        var result = imageResource
        result = 31 * result + buttons.hashCode()
        return result
    }

    class Button(
        val stringResource: Int,
        val clickListener: (ExerciseModel) -> Unit
    ) {

        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            if (other !is Button) return false

            return stringResource == other.stringResource
        }

        override fun hashCode() = stringResource

    }

}