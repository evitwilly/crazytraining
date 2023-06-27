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

    operator fun get(attr: TextAttribute): Pair<TypefaceStyle, Float> {
        return when (attr) {
            TextAttribute.Title1 -> title1
            TextAttribute.Title2 -> title2
            TextAttribute.Title3 -> title3
            TextAttribute.Body1 -> body1
            TextAttribute.Body2 -> body2
            TextAttribute.Body3 -> body3
            TextAttribute.Caption1 -> caption1
            TextAttribute.Caption2 -> caption2
        }
    }

}