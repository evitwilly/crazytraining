package ru.freeit.crazytraining.training.dialogs.viewmodel_states

import android.widget.LinearLayout
import android.widget.TextView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.text.TextType
import ru.freeit.crazytraining.core.theming.view.CoreEditText
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.training.dialogs.text_watchers.MaxTextWatcher

sealed class MeasuredValuesState {

    open val isVisibleExternalError: Boolean = false

    abstract fun bindViews(titleView: TextView, editLayoutView: LinearLayout, amountListener: (Int) -> Unit)

    object Quantity : MeasuredValuesState() {

        override fun bindViews(
            titleView: TextView,
            editLayoutView: LinearLayout,
            amountListener: (Int) -> Unit
        ) {
            titleView.setText(R.string.quantity_dialog_title)

            editLayoutView.removeAllViews()

            val ctx = titleView.context

            val quantityEditView = CoreEditText(titleView.context, TextType.Title2, verticalPadding = 2)
            quantityEditView.numbered()
            quantityEditView.changeHint("10")
            quantityEditView.requestFocus()
            quantityEditView.addTextWatcher(MaxTextWatcher(
                editView = quantityEditView,
                max = 1000,
                isErrorHandling = true,
                amountListener = amountListener
            ))
            quantityEditView.changeText("1")
            quantityEditView.layoutParams(linearLayoutParams().width(ctx.dp(100)).wrapHeight())

            editLayoutView.addView(quantityEditView)
        }

    }

    object Distance : MeasuredValuesState() {

        override val isVisibleExternalError: Boolean = true

        override fun bindViews(
            titleView: TextView,
            editLayoutView: LinearLayout,
            amountListener: (Int) -> Unit
        ) {
            titleView.setText(R.string.distance_dialog_title)

            editLayoutView.removeAllViews()

            val ctx = titleView.context

            val distanceKilometersEditView = CoreEditText(ctx, TextType.Title2, verticalPadding = 2)
            distanceKilometersEditView.numbered()
            distanceKilometersEditView.layoutParams(linearLayoutParams().width(ctx.dp(56)).wrapHeight())
            editLayoutView.addView(distanceKilometersEditView)

            val kilometerTextView = CoreTextView(ctx)
            kilometerTextView.layoutParams(linearLayoutParams().wrap().marginStart(ctx.dp(8)).marginEnd(ctx.dp(8)))
            kilometerTextView.setText(R.string.kilometers)
            editLayoutView.addView(kilometerTextView)

            val distanceMetersEditView = CoreEditText(ctx, TextType.Title2, verticalPadding = 2)
            distanceMetersEditView.numbered()
            distanceMetersEditView.requestFocus()
            distanceMetersEditView.layoutParams(linearLayoutParams().width(ctx.dp(56)).wrapHeight())
            editLayoutView.addView(distanceMetersEditView)

            val metersTextView = CoreTextView(ctx)
            metersTextView.setText(R.string.meters)
            metersTextView.layoutParams(linearLayoutParams().wrap().marginStart(ctx.dp(8)).marginEnd(ctx.dp(8)))
            editLayoutView.addView(metersTextView)

            fun changeAmount() {
                val kilometers = distanceKilometersEditView.text.toIntOrNull() ?: 0
                val meters = distanceMetersEditView.text.toIntOrNull() ?: 0
                val amount = kilometers * 1000 + meters
                if (amount <= 0) {
                    distanceKilometersEditView.error = CoreEditText.Error.Field
                    distanceMetersEditView.error = CoreEditText.Error.Field
                } else {
                    distanceKilometersEditView.error = CoreEditText.Error.Empty
                    distanceMetersEditView.error = CoreEditText.Error.Empty
                }
                amountListener.invoke(amount)
            }

            distanceKilometersEditView.addTextWatcher(MaxTextWatcher(
                editView = distanceKilometersEditView,
                max = 1000,
                amountListener = { changeAmount() }
            ))
            distanceMetersEditView.addTextWatcher(MaxTextWatcher(
                editView = distanceMetersEditView,
                max = 1000,
                amountListener = { changeAmount() }
            ))

            distanceMetersEditView.changeText("100")

        }

    }

    object Time : MeasuredValuesState() {

        override val isVisibleExternalError: Boolean = true

        override fun bindViews(
            titleView: TextView,
            editLayoutView: LinearLayout,
            amountListener: (Int) -> Unit
        ) {
            titleView.setText(R.string.time_dialog_title)

            editLayoutView.removeAllViews()

            val ctx = titleView.context

            val timeHoursEditView = CoreEditText(ctx, TextType.Title2, verticalPadding = 2)
            timeHoursEditView.numbered()
            timeHoursEditView.layoutParams(linearLayoutParams().width(ctx.dp(56)).wrapHeight())
            editLayoutView.addView(timeHoursEditView)

            val hoursTextView = CoreTextView(ctx)
            hoursTextView.layoutParams(linearLayoutParams().wrap().marginStart(ctx.dp(8)).marginEnd(ctx.dp(8)))
            hoursTextView.setText(R.string.hours)
            editLayoutView.addView(hoursTextView)

            val timeMinutesEditView = CoreEditText(ctx, TextType.Title2, verticalPadding = 2)
            timeMinutesEditView.numbered()
            timeMinutesEditView.requestFocus()
            timeMinutesEditView.layoutParams(linearLayoutParams().width(ctx.dp(56)).wrapHeight())
            editLayoutView.addView(timeMinutesEditView)

            val minutesTextView = CoreTextView(ctx)
            minutesTextView.setText(R.string.minutes)
            minutesTextView.layoutParams(linearLayoutParams().wrap().marginStart(ctx.dp(8)).marginEnd(ctx.dp(8)))
            editLayoutView.addView(minutesTextView)

            fun changeAmount() {
                val hours = timeHoursEditView.text.toIntOrNull() ?: 0
                val minutes = timeMinutesEditView.text.toIntOrNull() ?: 0
                val amount = hours * 60 + minutes
                if (amount <= 0) {
                    timeHoursEditView.error = CoreEditText.Error.Field
                    timeMinutesEditView.error = CoreEditText.Error.Field
                } else {
                    timeHoursEditView.error = CoreEditText.Error.Empty
                    timeMinutesEditView.error = CoreEditText.Error.Empty
                }
                amountListener.invoke(amount)
            }

            timeHoursEditView.addTextWatcher(MaxTextWatcher(
                editView = timeHoursEditView,
                max = 24,
                amountListener = { changeAmount() }
            ))
            timeMinutesEditView.addTextWatcher(MaxTextWatcher(
                editView = timeMinutesEditView,
                max = 60,
                amountListener = { changeAmount() }
            ))

            timeMinutesEditView.changeText("1")

        }

    }

}