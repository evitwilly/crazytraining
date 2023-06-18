package ru.freeit.crazytraining.training.dialogs

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import ru.freeit.crazytraining.core.navigation.dialogs.CoreDialog
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.layoutParams
import ru.freeit.crazytraining.core.theming.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.extensions.padding
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextType
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class MeasuredValuesDialog : CoreDialog() {

    override val name: String = "MeasuredValuesDialog"

    override fun createView(context: Context): View {
        val contentView = CoreLinearLayout(
            ctx = context,
            cornerRadiusStyle = radius,
            backgroundColor = ColorType.secondaryBackgroundColor
        )
        contentView.padding(horizontal = context.dp(16), vertical = context.dp(12))
        contentView.orientation = LinearLayout.VERTICAL

        val titleView = CoreTextView(context, textStyle = TextType.Body1)
        titleView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        contentView.addView(titleView)

        val editLayoutView = CoreLinearLayout(context)
        editLayoutView.orientation = LinearLayout.HORIZONTAL
        contentView.addView(editLayoutView)

        return contentView
    }

}