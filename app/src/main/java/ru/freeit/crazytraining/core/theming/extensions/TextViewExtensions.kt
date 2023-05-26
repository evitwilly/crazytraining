package ru.freeit.crazytraining.core.theming.extensions

import android.util.TypedValue
import android.widget.TextView

fun TextView.fontSize(sp: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, sp)
}