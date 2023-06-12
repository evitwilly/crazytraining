package ru.freeit.crazytraining.core.theming.colors

import ru.freeit.crazytraining.core.theming.CoreColors

class ColorsStyle(
    private val primaryColor: Int = CoreColors.greenMedium,
    private val primaryDarkColor: Int = CoreColors.greenDark,
    private val colorOnPrimary: Int = CoreColors.white,
    private val primaryBackgroundColor: Int,
    private val secondaryBackgroundColor: Int,
    private val primaryTextColor: Int,
    private val unselectedColor: Int,
    private val colorError: Int
) {
    fun color(type: ColorType): Int {
        return when(type) {
            ColorType.primaryColor -> primaryColor
            ColorType.primaryDarkColor -> primaryDarkColor
            ColorType.colorOnPrimary -> colorOnPrimary
            ColorType.primaryBackgroundColor -> primaryBackgroundColor
            ColorType.secondaryBackgroundColor -> secondaryBackgroundColor
            ColorType.primaryTextColor -> primaryTextColor
            ColorType.unselectedColor -> unselectedColor
            ColorType.transparent -> CoreColors.transparent
            ColorType.colorError -> colorError
        }
    }
}