package ru.freeit.crazytraining.core.theming.layout.params

import androidx.recyclerview.widget.RecyclerView

private const val match = RecyclerView.LayoutParams.MATCH_PARENT
private const val wrap = RecyclerView.LayoutParams.WRAP_CONTENT

class RecyclerViewLP(params : RecyclerView.LayoutParams = RecyclerView.LayoutParams(wrap, wrap)) : AbstractMarginLP<RecyclerView.LayoutParams, RecyclerViewLP>(params, match, wrap) {

    override fun with(params: RecyclerView.LayoutParams) = RecyclerViewLP(params)

}