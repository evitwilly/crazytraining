package ru.freeit.crazytraining.training.view

import android.content.Context
import android.view.Gravity
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreImageView
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class TrainingWeekendView(ctx: Context) : CoreLinearLayout(ctx) {

    init {
        orientation = VERTICAL

        padding(context.dp(16))

        val titleView = CoreTextView(context, textStyle = TextAttribute.Body3)
        titleView.setText(R.string.weekend_title)
        titleView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        addView(titleView)

        val imageView = CoreImageView(context, tintColor = ColorAttributes.primaryColor)
        imageView.setImageResource(R.drawable.ic_weekend)
        imageView.adjustViewBounds = true
        imageView.layoutParams(
            linearLayoutParams().width(context.dp(210)).wrapHeight().gravity(
                Gravity.CENTER_HORIZONTAL
            ))
        addView(imageView)
    }

}