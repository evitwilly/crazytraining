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
        caption = TypefaceStyle.SEMI_BOLD to 17f
    ),
    val cornerRadiusStyle: CornerRadiusStyle = CornerRadiusStyle(
        small = 8f,
        medium = 16f,
        big = 24f
    ),
    val colorsStyle: ColorsStyle
) {

    LIGHT(
        colorsStyle = ColorsStyle(
            primaryBackgroundColor = CoreColors.white,
            secondaryBackgroundColor = CoreColors.white,
            primaryTextColor = CoreColors.black,
            unselectedColor = CoreColors.black
        )
    ),

    DARK(
        colorsStyle = ColorsStyle(
            primaryBackgroundColor = CoreColors.black,
            secondaryBackgroundColor = CoreColors.grayMedium,
            primaryTextColor = CoreColors.white,
            unselectedColor = CoreColors.greenMedium
        )
    )

}