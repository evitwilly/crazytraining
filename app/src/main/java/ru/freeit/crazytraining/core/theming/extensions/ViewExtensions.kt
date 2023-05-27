package ru.freeit.crazytraining.core.theming.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import ru.freeit.crazytraining.core.theming.CoreColors
import ru.freeit.crazytraining.core.theming.layout.params.AbstractLP
import kotlin.math.roundToInt

fun Context.dp(dimen: Int) = (resources.displayMetrics.density * dimen).roundToInt()

fun Context.dp(dimen: Float) = (resources.displayMetrics.density * dimen)

fun View.padding(start: Int = paddingStart, top: Int = paddingTop, end: Int = paddingEnd, bottom: Int = paddingBottom) {
    setPadding(start, top, end, bottom)
}

fun View.padding(horizontal: Int, vertical: Int) {
    padding(horizontal, vertical, horizontal, vertical)
}

fun View.padding(all: Int) {
    padding(all, all, all, all)
}

fun View.layoutParams(params: AbstractLP<*, *>) {
    layoutParams = params.build()
}

fun View.roundRipple(color: Int) {
    background = RippleDrawable(
        ColorStateList.valueOf(color),
        null, GradientDrawable().apply {
            cornerRadius = 50f
            setColor(CoreColors.white)
        }
    )
}