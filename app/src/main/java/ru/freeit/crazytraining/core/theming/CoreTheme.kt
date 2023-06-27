package ru.freeit.crazytraining.core.theming

import ru.freeit.crazytraining.core.theming.colors.Colors
import ru.freeit.crazytraining.core.theming.corners.ShapeStyle
import ru.freeit.crazytraining.core.theming.text.TextStyle
import ru.freeit.crazytraining.core.theming.text.TypefaceStyle

enum class CoreTheme(
    val textStyle: TextStyle = TextStyle(
        title1 = TypefaceStyle.SEMI_BOLD to 24f,
        title2 = TypefaceStyle.SEMI_BOLD to 19f,
        title3 = TypefaceStyle.BOLD to 17f,
        body1 = TypefaceStyle.REGULAR to 17f,
        body2 = TypefaceStyle.MEDIUM to 16f,
        body3 = TypefaceStyle.REGULAR to 24f,
        caption1 = TypefaceStyle.SEMI_BOLD to 17f,
        caption2 = TypefaceStyle.REGULAR to 13f
    ),
    val shapeStyle: ShapeStyle = ShapeStyle(
        small = 8f,
        medium = 16f,
        big = 32f
    ),
    val colors: Colors
) {

    LIGHT(
        colors = Colors(
            primaryBackgroundColor = CoreColors.white,
            secondaryBackgroundColor = CoreColors.white,
            primaryTextColor = CoreColors.black,
            unselectedColor = CoreColors.black,
            colorError = CoreColors.red
        )
    ),

    DARK(
        colors = Colors(
            primaryBackgroundColor = CoreColors.black,
            secondaryBackgroundColor = CoreColors.grayMedium,
            primaryTextColor = CoreColors.white,
            unselectedColor = CoreColors.greenMedium,
            colorError = CoreColors.red
        )
    )

}