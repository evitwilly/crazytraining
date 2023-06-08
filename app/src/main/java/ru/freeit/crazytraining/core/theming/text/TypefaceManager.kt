package ru.freeit.crazytraining.core.theming.text

import android.content.res.AssetManager
import android.graphics.Typeface

class TypefaceManager(private val assetManager: AssetManager) {

    private val typefaces = hashMapOf<String, Typeface>()

    fun typeface(style: TypefaceStyle): Typeface {
        val path = style.typefaceManagerPath
        val savedTypeface = typefaces[path]
        return if (savedTypeface != null) {
            savedTypeface
        } else {
            val typeface = Typeface.createFromAsset(assetManager, path)
            typefaces[path] = typeface
            typeface
        }
    }

}