package ru.freeit.crazytraining.exercise.detail.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.Gravity
import androidx.core.view.isVisible
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.theming.CoreColors
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.corners.ShapeAttribute
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel

class ExerciseMeasuredValueView(ctx: Context) : CoreLinearLayout(ctx) {

    var checked: Boolean = false
        set(value) {
            field = value
            drawState(themeManager.selected_theme)
        }

    private val titleView = CoreTextView(context, textStyle = TextAttribute.Title3)
    private val contentView = CoreTextView(context)
    private val measuredView = CoreTextView(context, textStyle = TextAttribute.Body2)

    init {
        isClickable = true
        isFocusable = true
        orientation = VERTICAL
        padding(context.dp(12))

        titleView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        addView(titleView)

        contentView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginTop(context.dp(4)))
        addView(contentView)

        measuredView.layoutParams(
            linearLayoutParams().wrap().gravity(Gravity.END)
            .marginTop(context.dp(4)))
        addView(measuredView)
    }

    override fun onThemeChanged(theme: CoreTheme) {
        drawState(theme)
    }

    fun changeMeasuredValueModel(model: ExerciseMeasuredValueModel) {
        with(model) {
            titleView.setText(title)
            contentView.setText(description)
            measuredView.isVisible = unit != -1
            if (unit != -1) measuredView.setText(unit)
        }
    }

    private fun drawState(theme: CoreTheme) {
        val radius = context.dp(theme.shapeStyle[ShapeAttribute.medium])
        val primaryColor = theme.colors[ColorAttributes.primaryColor]

        val color = if (checked) {
            ColorAttributes.colorOnPrimary
        } else {
            ColorAttributes.primaryTextColor
        }

        titleView.changeTextColor(color)
        contentView.changeTextColor(color)
        measuredView.changeTextColor(color)

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