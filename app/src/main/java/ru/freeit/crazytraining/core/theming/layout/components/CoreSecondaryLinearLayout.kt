package ru.freeit.crazytraining.core.theming.layout.components

import android.content.Context
import ru.freeit.crazytraining.core.theming.CoreTheme

class CoreSecondaryLinearLayout(ctx: Context) : CoreLinearLayout(ctx) {

    override fun onThemeChanged(theme: CoreTheme) {
        setBackgroundColor(theme.secondaryBackgroundColor)
    }

}