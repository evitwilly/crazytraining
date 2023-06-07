package ru.freeit.crazytraining.core.theming

import ru.freeit.crazytraining.core.theming.colors.ColorsStyle
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusStyle
import ru.freeit.crazytraining.core.theming.typeface.TypefaceStyle

enum class CoreTheme(
    val toolbarTitleSize: Float = 19f,
    val toolbarTitleStyle: TypefaceStyle = TypefaceStyle.SEMI_BOLD,
    val captionStyle: TypefaceStyle = TypefaceStyle.REGULAR,
    val captionSize: Float = 17f,
    val chipTextSize: Float = 16f,
    val chipTextStyle: TypefaceStyle = TypefaceStyle.MEDIUM,
    val trainingDateTitleStyle: TypefaceStyle = TypefaceStyle.SEMI_BOLD,
    val trainingDateTitleSize: Float = 24f,
    val primaryButtonTextSize: Float = 17f,
    val primaryButtonTextStyle: TypefaceStyle = TypefaceStyle.SEMI_BOLD,
    val cornerRadiusStyle: CornerRadiusStyle = CornerRadiusStyle(8f, 16f, 24f),
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