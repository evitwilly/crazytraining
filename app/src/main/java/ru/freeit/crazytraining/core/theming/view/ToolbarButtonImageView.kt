package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.extensions.roundRipple

class ToolbarButtonImageView(ctx: Context): CoreImageView(ctx) {

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)
        roundRipple(theme.rippleColor)
    }

}