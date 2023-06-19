package ru.freeit.crazytraining.training.dialogs

import android.widget.LinearLayout
import android.widget.TextView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.layoutParams
import ru.freeit.crazytraining.core.theming.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.text.TextType
import ru.freeit.crazytraining.core.theming.view.CoreEditText
import ru.freeit.crazytraining.core.theming.view.CoreTextView

sealed interface MeasuredValuesState {

    fun bindViews(titleView: TextView, editLayoutView: LinearLayout, amountListener: (Int) -> Unit)

    object Quantity : MeasuredValuesState {

        override fun bindViews(
            titleView: TextView,
            editLayoutView: LinearLayout,
            amountListener: (Int) -> Unit
        ) {
            titleView.setText(R.string.quantity_dialog_title)

            editLayoutView.removeAllViews()

            val ctx = titleView.context

            val quantityEditView = CoreEditText(titleView.context, TextType.Title2)
            quantityEditView.numbered()
            quantityEditView.changeText("1")
            quantityEditView.layoutParams(linearLayoutParams().width(ctx.dp(50)).wrapHeight())
            quantityEditView.changeTextListener { amountListener.invoke(it.toIntOrNull() ?: 0) }

            editLayoutView.addView(quantityEditView)
        }

    }

    object Distance : MeasuredValuesState {

        override fun bindViews(
            titleView: TextView,
            editLayoutView: LinearLayout,
            amountListener: (Int) -> Unit
        ) {
            titleView.setText(R.string.distance_dialog_title)

            editLayoutView.removeAllViews()

            val ctx = titleView.context

            val distanceKilometersEditView = CoreEditText(ctx)
            distanceKilometersEditView.numbered()
            distanceKilometersEditView.layoutParams(linearLayoutParams().width(ctx.dp(50)).wrapHeight())
            distanceKilometersEditView.changeTextListener { amountListener.invoke(it.toIntOrNull() ?: 0) }
            editLayoutView.addView(distanceKilometersEditView)

            val kilometerTextView = CoreTextView(ctx)
            kilometerTextView.layoutParams(linearLayoutParams().wrap().marginStart(ctx.dp(8)).marginEnd(ctx.dp(8)))
            kilometerTextView.setText(R.string.km)
            editLayoutView.addView(kilometerTextView)

            val distanceMetersEditView = CoreEditText(ctx)
            distanceMetersEditView.numbered()
            distanceMetersEditView.layoutParams(linearLayoutParams().width(ctx.dp(50)).wrapHeight())
            distanceMetersEditView.changeTextListener { amountListener.invoke(it.toIntOrNull() ?: 0) }
            editLayoutView.addView(distanceMetersEditView)

            val metersTextView = CoreTextView(ctx)
            metersTextView.setText(R.string.m)
            metersTextView.layoutParams(linearLayoutParams().wrap().marginStart(ctx.dp(8)).marginEnd(ctx.dp(8)))
            editLayoutView.addView(metersTextView)

        }

    }

    object Time : MeasuredValuesState {

        override fun bindViews(
            titleView: TextView,
            editLayoutView: LinearLayout,
            amountListener: (Int) -> Unit
        ) {
            titleView.setText(R.string.time_dialog_title)

            editLayoutView.removeAllViews()

            val ctx = titleView.context

            val timeHoursEditView = CoreEditText(ctx)
            timeHoursEditView.numbered()
            timeHoursEditView.layoutParams(linearLayoutParams().width(ctx.dp(50)).wrapHeight())
            timeHoursEditView.changeTextListener { amountListener.invoke(it.toIntOrNull() ?: 0) }
            editLayoutView.addView(timeHoursEditView)

            val hoursTextView = CoreTextView(ctx)
            hoursTextView.layoutParams(linearLayoutParams().wrap().marginStart(ctx.dp(8)).marginEnd(ctx.dp(8)))
            hoursTextView.setText(R.string.hours)
            editLayoutView.addView(hoursTextView)

            val timeMinutesEditView = CoreEditText(ctx)
            timeMinutesEditView.numbered()
            timeMinutesEditView.layoutParams(linearLayoutParams().width(ctx.dp(50)).wrapHeight())
            timeMinutesEditView.changeTextListener { amountListener.invoke(it.toIntOrNull() ?: 0) }
            editLayoutView.addView(timeMinutesEditView)

            val minutesTextView = CoreTextView(ctx)
            minutesTextView.setText(R.string.minutes)
            minutesTextView.layoutParams(linearLayoutParams().wrap().marginStart(ctx.dp(8)).marginEnd(ctx.dp(8)))
            editLayoutView.addView(minutesTextView)
        }

    }

}