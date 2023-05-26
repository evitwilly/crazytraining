package ru.freeit.crazytraining.core.theming.layout.params

import android.widget.FrameLayout
import ru.freeit.crazytraining.core.theming.layout.params.AbstractMarginLP

private const val match = FrameLayout.LayoutParams.MATCH_PARENT
private const val wrap = FrameLayout.LayoutParams.WRAP_CONTENT

class FrameLayoutLP(private val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(match, wrap)) : AbstractMarginLP<FrameLayout.LayoutParams, FrameLayoutLP>(params, match, wrap) {

    fun gravity(grav: Int) = FrameLayoutLP(params.apply { gravity = grav })

    override fun with(params: FrameLayout.LayoutParams): FrameLayoutLP = FrameLayoutLP(params)

}