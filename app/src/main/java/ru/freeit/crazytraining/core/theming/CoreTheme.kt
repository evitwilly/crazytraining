package ru.freeit.crazytraining.core.theming

import ru.freeit.crazytraining.core.theming.typeface.TypefaceStyle

enum class CoreTheme(
    val backgroundColor: Int,
    val primaryTextColor: Int,
    val toolbarTitleSize: Float,
    val toolbarTitleStyle: TypefaceStyle,
    val primaryImageColor: Int,
    val rippleColor: Int
) {

    LIGHT(
        backgroundColor = CoreColors.white,
        primaryTextColor = CoreColors.black,
        toolbarTitleSize = 18f,
        toolbarTitleStyle = TypefaceStyle.MEDIUM,
        primaryImageColor = CoreColors.black,
        rippleColor = CoreColors.greenMedium
    ),

    DARK(
        backgroundColor = CoreColors.black,
        primaryTextColor = CoreColors.white,
        toolbarTitleSize = 18f,
        toolbarTitleStyle = TypefaceStyle.MEDIUM,
        primaryImageColor = CoreColors.white,
        rippleColor = CoreColors.greenMedium
    )

}