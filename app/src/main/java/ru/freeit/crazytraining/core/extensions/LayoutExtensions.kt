package ru.freeit.crazytraining.core.extensions

import android.view.View
import android.view.ViewGroup
import ru.freeit.crazytraining.core.theming.layout.params.FrameLayoutLP
import ru.freeit.crazytraining.core.theming.layout.params.LinearLayoutLP
import ru.freeit.crazytraining.core.theming.layout.params.RecyclerViewLP
import ru.freeit.crazytraining.core.theming.layout.params.ViewGroupLP

fun linearLayoutParams() = LinearLayoutLP()
fun frameLayoutParams() = FrameLayoutLP()
fun viewGroupLayoutParams() = ViewGroupLP()
fun recyclerLayoutParams() = RecyclerViewLP()

fun ViewGroup.addView(vararg views: View) {
    views.forEach { view ->
        addView(view)
    }
}
