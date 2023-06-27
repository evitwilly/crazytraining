package ru.freeit.crazytraining.core.theming.colors

import ru.freeit.crazytraining.core.theming.CoreColors

class Colors(
    private val primaryColor: Int = CoreColors.greenMedium,
    private val primaryDarkColor: Int = CoreColors.greenDark,
    private val colorOnPrimary: Int = CoreColors.white,
    private val primaryBackgroundColor: Int,
    private val secondaryBackgroundColor: Int,
    private val primaryTextColor: Int,
    private val unselectedColor: Int,
    private val colorError: Int
) {

    operator fun get(type: ColorAttributes): Int {
        return when(type) {
            ColorAttributes.primaryColor -> primaryColor
            ColorAttributes.primaryDarkColor -> primaryDarkColor
            ColorAttributes.colorOnPrimary -> colorOnPrimary
            ColorAttributes.primaryBackgroundColor -> primaryBackgroundColor
            ColorAttributes.secondaryBackgroundColor -> secondaryBackgroundColor
            ColorAttributes.primaryTextColor -> primaryTextColor
            ColorAttributes.unselectedColor -> unselectedColor
            ColorAttributes.transparent -> CoreColors.transparent
            ColorAttributes.colorError -> colorError
        }
    }

}