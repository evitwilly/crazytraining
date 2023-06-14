package ru.freeit.crazytraining.exercise.list.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.layoutParams
import ru.freeit.crazytraining.core.theming.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.extensions.padding
import ru.freeit.crazytraining.core.theming.view.CoreButton

class ExerciseEditButtonState(
    private val imageResource: Int,
    private val buttons: List<Button>
) {

    fun bindImageView(imageView: ImageView) {
        imageView.setImageResource(imageResource)
    }

    fun bindButtons(parent: LinearLayout) {
        val context = parent.context
        parent.removeAllViews()
        buttons.forEachIndexed { index, buttonModel ->
            val buttonView =
                CoreButton(context, CornerRadiusType.small, CornerTreatmentStrategy.AllRounded())
            buttonView.setText(buttonModel.stringResource)
            buttonView.setOnClickListener(buttonModel.clickListener)
            buttonView.padding(horizontal = context.dp(8), vertical = context.dp(4))
            buttonView.layoutParams(linearLayoutParams().wrap().marginStart(if (index > 0) context.dp(8) else 0))
            parent.addView(buttonView)
        }
    }

    class Button(
        val stringResource: Int,
        val clickListener: View.OnClickListener
    )

}