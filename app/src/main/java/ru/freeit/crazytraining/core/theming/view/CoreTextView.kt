package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme

abstract class CoreTextView(ctx: Context): AppCompatTextView(ctx) {

    private val themeManager = (context.applicationContext as App).themeManager
    protected val typefaceManager = (context.applicationContext as App).typefaceManager

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

    protected open fun onThemeChanged(theme: CoreTheme) {
        setTextColor(theme.primaryTextColor)
    }

}