package ru.freeit.crazytraining.core.theming.layout.components

import android.content.Context
import android.widget.FrameLayout
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme

class CoreFrameLayout(ctx: Context): FrameLayout(ctx) {

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        setBackgroundColor(theme.backgroundColor)
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