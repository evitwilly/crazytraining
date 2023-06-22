package ru.freeit.crazytraining.core.theming.text

class TextStyle(
    private val title1: Pair<TypefaceStyle, Float>,
    private val title2: Pair<TypefaceStyle, Float>,
    private val title3: Pair<TypefaceStyle, Float>,
    private val body1: Pair<TypefaceStyle, Float>,
    private val body2: Pair<TypefaceStyle, Float>,
    private val body3: Pair<TypefaceStyle, Float>,
    private val caption1: Pair<TypefaceStyle, Float>,
    private val caption2: Pair<TypefaceStyle, Float>
) {
    fun style(type: TextType): Pair<TypefaceStyle, Float> {
        return when (type) {
            TextType.Title1 -> title1
            TextType.Title2 -> title2
            TextType.Title3 -> title3
            TextType.Body1 -> body1
            TextType.Body2 -> body2
            TextType.Body3 -> body3
            TextType.Caption1 -> caption1
            TextType.Caption2 -> caption2
        }
    }
}