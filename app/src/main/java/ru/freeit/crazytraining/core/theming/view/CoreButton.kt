package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.corners.ShapeAttribute
import ru.freeit.crazytraining.core.theming.corners.ShapeTreatmentStrategy
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.text.TextAttribute.Caption1

open class CoreButton @JvmOverloads constructor(
    ctx: Context,
    private val shape: ShapeAttribute = ShapeAttribute.medium,
    private val shapeTreatmentStrategy: ShapeTreatmentStrategy = ShapeTreatmentStrategy.None()
) : CoreTextView(ctx, textColor = ColorAttributes.colorOnPrimary, textStyle = Caption1) {

    init {
        isClickable = true
        isFocusable = true
        gravity = Gravity.CENTER
        padding(horizontal = context.dp(8), vertical = context.dp(12))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)

        val gradientBackground = GradientDrawable()
        gradientBackground.setColor(theme.colors[ColorAttributes.primaryColor])
        gradientBackground.cornerRadii = shapeTreatmentStrategy.floatArrayOf(context.dp(theme.shapeStyle[shape]))

        val rippleColor = ColorStateList.valueOf(theme.colors[ColorAttributes.primaryDarkColor])

        background = RippleDrawable(rippleColor, gradientBackground, null)
    }

    fun changeStartIcon(@DrawableRes drawableRes: Int, size: Int = 16) {
        val drawable = AppCompatResources.getDrawable(context, drawableRes) ?: return
        drawable.setBounds(0, 0, context.dp(size), context.dp(size))
        drawable.setTint(themeManager.selected_theme.colors[ColorAttributes.colorOnPrimary])
        setCompoundDrawables(drawable, null, null, null)
        compoundDrawablePadding = context.dp(4)
    }

}