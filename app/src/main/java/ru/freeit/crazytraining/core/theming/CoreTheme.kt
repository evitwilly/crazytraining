package ru.freeit.crazytraining.core.theming

import ru.freeit.crazytraining.core.theming.colors.ColorsStyle
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusStyle
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
    val cornerRadiusStyle: CornerRadiusStyle = CornerRadiusStyle(
        small = 8f,
        medium = 16f,
        big = 32f
    ),
    val colorsStyle: ColorsStyle
) {

    LIGHT(
        colorsStyle = ColorsStyle(
            primaryBackgroundColor = CoreColors.white,
            secondaryBackgroundColor = CoreColors.white,
            primaryTextColor = CoreColors.black,
            unselectedColor = CoreColors.black,
            colorError = CoreColors.red
        )
    ),

    DARK(
        colorsStyle = ColorsStyle(
            primaryBackgroundColor = CoreColors.black,
            secondaryBackgroundColor = CoreColors.grayMedium,
            primaryTextColor = CoreColors.white,
            unselectedColor = CoreColors.greenMedium,
            colorError = CoreColors.red
        )
    )

}