package ru.freeit.crazytraining.core.theming

enum class CoreTheme(
    val backgroundColor: Int,
    val primaryTextColor: Int
) {

    LIGHT(
        backgroundColor = CoreColors.white,
        primaryTextColor = CoreColors.black
    ),

    DARK(
        backgroundColor = CoreColors.black,
        primaryTextColor = CoreColors.white
    )

}