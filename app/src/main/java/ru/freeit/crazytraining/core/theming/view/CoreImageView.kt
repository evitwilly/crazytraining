package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme

open class CoreImageView(ctx: Context): AppCompatImageView(ctx) {

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
        setColorFilter(theme.primaryImageColor)
    }

}