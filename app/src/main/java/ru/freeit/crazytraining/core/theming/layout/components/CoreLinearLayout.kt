package ru.freeit.crazytraining.core.theming.layout.components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.LinearLayout
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.colors.ColorType.*
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy

open class CoreLinearLayout @JvmOverloads constructor(
    ctx: Context,
    private val backgroundColor: ColorType = primaryBackgroundColor,
    private val cornerRadiusStyle: CornerRadiusType = CornerRadiusType.medium,
    private val cornerTreatmentStrategy: CornerTreatmentStrategy = CornerTreatmentStrategy.None()
): LinearLayout(ctx) {

    protected open fun onThemeChanged(theme: CoreTheme) {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadii = cornerTreatmentStrategy.floatArrayOf(theme.cornerRadiusStyle.style(cornerRadiusStyle))
        gradientDrawable.setColor(theme.colorsStyle.color(backgroundColor))
        background = gradientDrawable
    }

    protected val themeManager = (context.applicationContext as App).themeManager

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

}