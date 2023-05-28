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
    val captionSize: Float = 17f
) {

    LIGHT(
        backgroundColor = CoreColors.white,
        primaryTextColor = CoreColors.black,
        primaryImageColor = CoreColors.black,
        rippleColor = CoreColors.greenMedium
    ),

    DARK(
        backgroundColor = CoreColors.black,
        primaryTextColor = CoreColors.white,
        primaryImageColor = CoreColors.white,
        rippleColor = CoreColors.greenMedium
    )

}