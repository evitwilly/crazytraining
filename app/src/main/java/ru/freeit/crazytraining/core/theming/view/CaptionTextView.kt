package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.extensions.fontSize

class CaptionTextView(ctx: Context) : CoreTextView(ctx) {

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)
        typeface = typefaceManager.typeface(theme.captionStyle)
        fontSize(theme.captionSize)
    }

}