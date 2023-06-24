package ru.freeit.crazytraining.core.viewmodel

import android.os.Bundle

interface SavedInstanceState {
    fun <T> parcelable(key: String, `class`: Class<T>): T?
}

class SavedInstanceStateImpl(private val bundle: Bundle?) : SavedInstanceState {

    override fun <T> parcelable(key: String, `class`: Class<T>): T? {
        return when {
            android.os.Build.VERSION.SDK_INT >= 33 -> bundle?.getParcelable(key, `class`)
            else -> @Suppress("DEPRECATION") bundle?.getParcelable(key) as? T
        }
    }

}