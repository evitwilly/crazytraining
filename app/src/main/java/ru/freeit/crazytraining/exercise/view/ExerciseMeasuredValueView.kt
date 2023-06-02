package ru.freeit.crazytraining.exercise.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import androidx.annotation.StringRes
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.fontSize
import ru.freeit.crazytraining.core.theming.extensions.padding
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.typeface.TypefaceStyle
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class ExerciseMeasuredValueView(ctx: Context) : CoreLinearLayout(ctx) {

    var checked: Boolean = false
        set(value) {
            field = value
            drawState(themeManager.selected_theme)
        }

    private val titleView = CoreTextView(context)
    private val contentView = CoreTextView(context)

    init {
        isClickable = true
        isFocusable = true
        orientation = VERTICAL
        padding(context.dp(12))

        titleView.fontSize(18f)
        titleView.fontFamily(TypefaceStyle.BOLD)
        addView(titleView)

        contentView.fontSize(17f)
        contentView.fontFamily(TypefaceStyle.MEDIUM)
        addView(contentView)
    }

    override fun onThemeChanged(theme: CoreTheme) {
        drawState(theme)
    }

    fun changeTitle(@StringRes resource: Int) {
        titleView.setText(resource)
    }

    fun changeContent(@StringRes resource: Int) {
        contentView.setText(resource)
    }

    private fun drawState(theme: CoreTheme) {
        val gradientDrawable = if (checked) {
            GradientDrawable().apply {
                cornerRadius = context.dp(16f)
                setColor(theme.primaryColor)
            }
        } else {
            GradientDrawable().apply {
                cornerRadius = context.dp(16f)
                setStroke(context.dp(2), theme.primaryColor)
            }
        }
        background =
            RippleDrawable(ColorStateList.valueOf(theme.rippleColor), gradientDrawable, null)
    }

}