package ru.freeit.crazytraining.core.theming.layout.params


import android.view.ViewGroup
import ru.freeit.crazytraining.core.theming.layout.params.AbstractLP

private const val match = ViewGroup.LayoutParams.MATCH_PARENT
private const val wrap = ViewGroup.LayoutParams.WRAP_CONTENT

class ViewGroupLP(params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(wrap, wrap)) : AbstractLP<ViewGroup.LayoutParams, ViewGroupLP>(params, match, wrap) {

    override fun with(params: ViewGroup.LayoutParams): ViewGroupLP = ViewGroupLP(params)

}