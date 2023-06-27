package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes.primaryTextColor

open class CoreImageView @JvmOverloads constructor(
    ctx: Context,
    private var tintColor: ColorAttributes = primaryTextColor
): AppCompatImageView(ctx) {

    val themeManager = (context.applicationContext as App).themeManager

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

    protected open fun onThemeChanged(theme: CoreTheme) {
        setColorFilter(theme.colors[tintColor])
    }

    fun changeTint(color: ColorAttributes) {
        tintColor = color
        setColorFilter(themeManager.selected_theme.colors[color])
    }

}