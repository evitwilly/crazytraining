package ru.freeit.crazytraining.core.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import ru.freeit.crazytraining.core.theming.layout.params.AbstractLP
import kotlin.math.roundToInt

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    android.os.Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

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

fun Int.withAlpha(alpha: Float): Int {
    val alphaInteger = (255 * alpha).toInt()
    return this + (alphaInteger shl 24)
}

fun View.layoutParams(params: AbstractLP<*, *>) {
    layoutParams = params.build()
}

fun Context.bitmapByResource(@DrawableRes resource: Int, size: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(this, resource)
    val svgBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvasBitmap = Canvas(svgBitmap)
    drawable?.setBounds(0, 0, dp(32), dp(32))
    drawable?.draw(canvasBitmap)
    return svgBitmap
}