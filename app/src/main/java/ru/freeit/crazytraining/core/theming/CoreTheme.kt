package ru.freeit.crazytraining.core.theming

import ru.freeit.crazytraining.core.theming.typeface.TypefaceStyle

enum class CoreTheme(
    val primaryColor: Int = CoreColors.greenMedium,
    val primaryBackgroundColor: Int,
    val secondaryBackgroundColor: Int,
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
    val chipTextStyle: TypefaceStyle = TypefaceStyle.MEDIUM,
    val trainingDateTitleBottomLineColor: Int = CoreColors.greenMedium,
    val trainingDateTitleStyle: TypefaceStyle = TypefaceStyle.SEMI_BOLD,
    val trainingDateTitleSize: Float = 24f,
    val primaryButtonTextColor: Int = CoreColors.white,
    val primaryButtonBackgroundColor: Int = CoreColors.greenMedium,
    val primaryButtonRippleColor: Int = CoreColors.greenDark,
    val primaryButtonTextSize: Float = 17f,
    val primaryButtonTextStyle: TypefaceStyle = TypefaceStyle.SEMI_BOLD
) {

    LIGHT(
        primaryBackgroundColor = CoreColors.white,
        secondaryBackgroundColor = CoreColors.white,
        primaryTextColor = CoreColors.black,
        primaryImageColor = CoreColors.black,
        rippleColor = CoreColors.greenMedium,
        chipSelectedColor = CoreColors.greenMedium,
        chipUnselectedColor = CoreColors.black
    ),

    DARK(
        primaryBackgroundColor = CoreColors.black,
        secondaryBackgroundColor = CoreColors.grayMedium,
        primaryTextColor = CoreColors.white,
        primaryImageColor = CoreColors.white,
        rippleColor = CoreColors.greenMedium,
        chipSelectedColor = CoreColors.greenMedium,
        chipUnselectedColor = CoreColors.greenMedium
    )

}