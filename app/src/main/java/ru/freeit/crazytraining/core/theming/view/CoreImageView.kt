package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.colors.ColorType.primaryTextColor

open class CoreImageView @JvmOverloads constructor(
    ctx: Context,
    private var tintColor: ColorType = primaryTextColor
): AppCompatImageView(ctx) {

    private val themeManager = (context.applicationContext as App).themeManager

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

    protected open fun onThemeChanged(theme: CoreTheme) {
        setColorFilter(theme.colorsStyle.color(tintColor))
    }

    fun changeTint(color: ColorType) {
        tintColor = color
        setColorFilter(themeManager.selected_theme.colorsStyle.color(color))
    }

}