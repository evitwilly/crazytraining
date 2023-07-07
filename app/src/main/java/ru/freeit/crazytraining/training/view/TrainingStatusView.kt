package ru.freeit.crazytraining.training.view

import android.content.Context
import android.view.Gravity
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreImageView
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class TrainingStatusView(ctx: Context) : CoreLinearLayout(ctx) {

    private val titleView = CoreTextView(context, textStyle = TextAttribute.Body3)
    private val imageView = CoreImageView(context, tintColor = ColorAttributes.primaryColor)

    init {
        orientation = VERTICAL

        padding(context.dp(16))

        titleView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        addView(titleView)

        imageView.adjustViewBounds = true
        imageView.layoutParams(linearLayoutParams().wrapWidth().height(context.dp(200))
            .gravity(Gravity.CENTER_HORIZONTAL)
            .marginTop(context.dp(32)))
        addView(imageView)
    }

    fun changeTitle(string: String) {
        titleView.text = string
    }

    fun changeIcon(drawableResource: Int) {
        imageView.setImageResource(drawableResource)
    }

}