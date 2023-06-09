package ru.freeit.crazytraining.exercise.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import androidx.annotation.StringRes
import ru.freeit.crazytraining.core.theming.CoreColors
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.theming.extensions.*
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextType
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class ExerciseMeasuredValueView(ctx: Context) : CoreLinearLayout(ctx) {

    var checked: Boolean = false
        set(value) {
            field = value
            drawState(themeManager.selected_theme)
        }

    private val titleView = CoreTextView(context, textStyle = TextType.Title3)
    private val contentView = CoreTextView(context, textStyle = TextType.Body2)

    init {
        isClickable = true
        isFocusable = true
        orientation = VERTICAL
        padding(context.dp(12))

        titleView.includeFontPadding = false
        titleView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        addView(titleView)

        contentView.includeFontPadding = false
        contentView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(4)))
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
        val radius = context.dp(theme.cornerRadiusStyle.style(CornerRadiusType.medium))
        val primaryColor = theme.colorsStyle.color(ColorType.primaryColor)
        val gradientDrawable = if (checked) {
            GradientDrawable().apply {
                cornerRadius = radius
                setColor(primaryColor)
            }
        } else {
            GradientDrawable().apply {
                cornerRadius = radius
                setStroke(context.dp(2), primaryColor)
            }
        }
        background = RippleDrawable(
            ColorStateList.valueOf(primaryColor),
            gradientDrawable,
            GradientDrawable().apply {
                cornerRadius = radius
                setColor(CoreColors.white)
            }
        )
    }

}