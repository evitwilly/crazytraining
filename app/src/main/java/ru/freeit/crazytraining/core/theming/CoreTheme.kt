package ru.freeit.crazytraining.core.theming

import ru.freeit.crazytraining.core.theming.typeface.TypefaceStyle

enum class CoreTheme(
    val backgroundColor: Int,
    val primaryTextColor: Int,
    val toolbarTitleSize: Float = 19f,
    val toolbarTitleStyle: TypefaceStyle = TypefaceStyle.SEMI_BOLD,
    val primaryImageColor: Int,
    val rippleColor: Int,
    val captionStyle: TypefaceStyle = TypefaceStyle.REGULAR,
    val captionSize: Float = 17f,
    val chipSelectedColor: Int,
    val chipSelectedTextColor: Int = CoreColors.white,
    val chipUnselectedColor: Int,
    val chipTextSize: Float = 16f,
    val chipTextStyle: TypefaceStyle = TypefaceStyle.MEDIUM
) {

    LIGHT(
        backgroundColor = CoreColors.white,
        primaryTextColor = CoreColors.black,
        primaryImageColor = CoreColors.black,
        rippleColor = CoreColors.greenMedium,
        chipSelectedColor = CoreColors.greenMedium,
        chipUnselectedColor = CoreColors.black
    ),

    DARK(
        backgroundColor = CoreColors.black,
        primaryTextColor = CoreColors.white,
        primaryImageColor = CoreColors.white,
        rippleColor = CoreColors.greenMedium,
        chipSelectedColor = CoreColors.greenMedium,
        chipUnselectedColor = CoreColors.greenMedium
    )

}