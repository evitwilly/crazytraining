package ru.freeit.crazytraining.training.dialogs.viewmodel_states

import android.widget.LinearLayout
import android.widget.TextView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreEditText
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.training.dialogs.text_watchers.MaxTextWatcher

sealed class ExerciseUnitDialogState {

    open val isVisibleExternalError: Boolean = false

    abstract fun bindViews(titleView: TextView, editLayoutView: LinearLayout, amountListener: (Int) -> Unit)

    object Quantity : ExerciseUnitDialogState() {

        override fun bindViews(
            titleView: TextView,
            editLayoutView: LinearLayout,
            amountListener: (Int) -> Unit
        ) {
            titleView.setText(R.string.quantity_dialog_title)

            editLayoutView.removeAllViews()

            val ctx = titleView.context

            val quantityEditView = CoreEditText(titleView.context, TextAttribute.Title2, verticalPadding = 2)
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

    object Distance : ExerciseUnitDialogState() {

        override val isVisibleExternalError: Boolean = true

        override fun bindViews(
            titleView: TextView,
            editLayoutView: LinearLayout,
            amountListener: (Int) -> Unit
        ) {
            titleView.setText(R.string.distance_dialog_title)

            editLayoutView.removeAllViews()

            val ctx = titleView.context

            val distanceKilometersEditView = CoreEditText(ctx, TextAttribute.Title2, verticalPadding = 2)
            distanceKilometersEditView.numbered()
            distanceKilometersEditView.layoutParams(linearLayoutParams().width(ctx.dp(56)).wrapHeight())
            editLayoutView.addView(distanceKilometersEditView)

            val kilometerTextView = CoreTextView(ctx)
            kilometerTextView.layoutParams(linearLayoutParams().wrap().marginStart(ctx.dp(8)).marginEnd(ctx.dp(8)))
            kilometerTextView.setText(R.string.kilometers)
            editLayoutView.addView(kilometerTextView)

            val distanceMetersEditView = CoreEditText(ctx, TextAttribute.Title2, verticalPadding = 2)
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

    object Time : ExerciseUnitDialogState() {

        override val isVisibleExternalError: Boolean = true

        override fun bindViews(
            titleView: TextView,
            editLayoutView: LinearLayout,
            amountListener: (Int) -> Unit
        ) {
            titleView.setText(R.string.time_dialog_title)

            editLayoutView.removeAllViews()

            val ctx = titleView.context

            val timeMinutesEditView = CoreEditText(ctx, TextAttribute.Title2, verticalPadding = 2)
            timeMinutesEditView.numbered()
            timeMinutesEditView.layoutParams(linearLayoutParams().width(ctx.dp(56)).wrapHeight())
            editLayoutView.addView(timeMinutesEditView)

            val hoursTextView = CoreTextView(ctx)
            hoursTextView.layoutParams(linearLayoutParams().wrap().marginStart(ctx.dp(8)).marginEnd(ctx.dp(8)))
            hoursTextView.setText(R.string.minutes)
            editLayoutView.addView(hoursTextView)

            val timeSecondsEditView = CoreEditText(ctx, TextAttribute.Title2, verticalPadding = 2)
            timeSecondsEditView.numbered()
            timeSecondsEditView.requestFocus()
            timeSecondsEditView.layoutParams(linearLayoutParams().width(ctx.dp(56)).wrapHeight())
            editLayoutView.addView(timeSecondsEditView)

            val minutesTextView = CoreTextView(ctx)
            minutesTextView.setText(R.string.seconds)
            minutesTextView.layoutParams(linearLayoutParams().wrap().marginStart(ctx.dp(8)).marginEnd(ctx.dp(8)))
            editLayoutView.addView(minutesTextView)

            fun changeAmount() {
                val minutes = timeMinutesEditView.text.toIntOrNull() ?: 0
                val seconds = timeSecondsEditView.text.toIntOrNull() ?: 0
                val amount = minutes * 60 + seconds
                if (amount <= 0) {
                    timeMinutesEditView.error = CoreEditText.Error.Field
                    timeSecondsEditView.error = CoreEditText.Error.Field
                } else {
                    timeMinutesEditView.error = CoreEditText.Error.Empty
                    timeSecondsEditView.error = CoreEditText.Error.Empty
                }
                amountListener.invoke(amount)
            }

            timeMinutesEditView.addTextWatcher(MaxTextWatcher(
                editView = timeMinutesEditView,
                max = 60,
                amountListener = { changeAmount() }
            ))
            timeSecondsEditView.addTextWatcher(MaxTextWatcher(
                editView = timeSecondsEditView,
                max = 60,
                amountListener = { changeAmount() }
            ))

            timeSecondsEditView.changeText("10")

        }

    }

}