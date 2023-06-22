package ru.freeit.crazytraining.core.extensions

import android.content.Context
import android.util.TypedValue
import android.widget.TextView

fun TextView.fontSize(sp: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, sp)
}

fun Context.fontSizeInPixels(sp: Float): Float {
    return sp * resources.displayMetrics.scaledDensity
}