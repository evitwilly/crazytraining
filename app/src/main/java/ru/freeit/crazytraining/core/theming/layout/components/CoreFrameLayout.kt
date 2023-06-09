package ru.freeit.crazytraining.core.theming.layout.components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.FrameLayout
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.colors.ColorType.*
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy

class CoreFrameLayout @JvmOverloads constructor(
    ctx: Context,
    private val backgroundColor: ColorType = primaryBackgroundColor,
    private val cornerRadiusStyle: CornerRadiusType = CornerRadiusType.medium,
    private val cornerTreatmentStrategy: CornerTreatmentStrategy = CornerTreatmentStrategy.None()
): FrameLayout(ctx) {

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        val gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadii = cornerTreatmentStrategy.floatArrayOf(theme.cornerRadiusStyle.style(cornerRadiusStyle))
        gradientDrawable.setColor(theme.colorsStyle.color(backgroundColor))
        background = gradientDrawable
    }

    private val themeManager = (context.applicationContext as App).themeManager

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(onThemeChanged)
    }

}