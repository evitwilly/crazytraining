package ru.freeit.crazytraining.statistics.adapter

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.theming.adapter.CoreViewHolder
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.corners.ShapeTreatmentStrategy
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.statistics.viewmodel_states.StatisticsDetailState

class StatisticsViewHolder(
    view: View,
    private val titleView: CoreTextView,
    private val marksView: CoreLinearLayout
) : CoreViewHolder<StatisticsDetailState>(view) {

    override fun bind(item: StatisticsDetailState) {
        item.model.bindTitle(titleView)

        marksView.removeAllViews()
        val context = marksView.context
        item.marks.forEachIndexed { index, string ->
            val textView = CoreTextView(
                ctx = context,
                textStyle = TextAttribute.Body1
            )
            textView.text = string
            textView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
                .marginTop(if (index > 0) context.dp(4) else 0))
            marksView.addView(textView)
        }
    }

    companion object {
        fun from(context: Context): StatisticsViewHolder {
            val linearView = CoreLinearLayout(
                ctx = context,
                backgroundColor = ColorAttributes.secondaryBackgroundColor,
                shapeTreatmentStrategy = ShapeTreatmentStrategy.AllRounded()
            )
            linearView.elevation = context.dp(2f)
            linearView.orientation = LinearLayout.VERTICAL
            linearView.layoutParams(recyclerLayoutParams().matchWidth().wrapHeight())
            linearView.padding(context.dp(12))

            val titleView = CoreTextView(
                ctx = context,
                textStyle = TextAttribute.Title2
            )
            titleView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
            linearView.addView(titleView)

            val marksView = CoreLinearLayout(
                ctx = context,
                backgroundColor = ColorAttributes.transparent
            )
            marksView.orientation = LinearLayout.VERTICAL
            marksView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
                .marginTop(context.dp(12)))
            linearView.addView(marksView)

            return StatisticsViewHolder(linearView, titleView, marksView)
        }
    }

}