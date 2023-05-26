package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme

class CoreTextView(ctx: Context): AppCompatTextView(ctx) {

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        setTextColor(theme.primaryTextColor)
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