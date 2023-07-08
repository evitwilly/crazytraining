package ru.freeit.crazytraining.training.dialogs

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.navigation.dialogs.CoreDialog
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.core.theming.view.CoreEditText
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.training.view.TrainingRatingView

class FinishingTrainingDialog : CoreDialog() {

    override val name: String = "FinishingTrainingDialog"

    override fun createView(context: Context): View {
        val contentView = CoreLinearLayout(
            ctx = context,
            shape = radius,
            backgroundColor = ColorAttributes.secondaryBackgroundColor
        )
        contentView.clipChildren = true
        contentView.orientation = LinearLayout.VERTICAL

        val titleView = CoreTextView(
            ctx = context,
            textStyle = TextAttribute.Title2
        )
        titleView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16)).marginEnd(context.dp(16)))
        titleView.setText(R.string.training_finishing)
        contentView.addView(titleView)

        val commentView = CoreEditText(context)
        commentView.changeLines(3)
        commentView.changeHint(R.string.write_comment_about_training)
        commentView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(12)))
        contentView.addView(commentView)

        val captionRatingView = CoreTextView(
            ctx = context,
            textStyle = TextAttribute.Body2
        )
        captionRatingView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16)).marginEnd(context.dp(16))
            .marginTop(context.dp(12)))
        captionRatingView.setText(R.string.choose_rating_colon)
        contentView.addView(captionRatingView)

        val ratingView = TrainingRatingView(context)
        ratingView.changeRating(4)
        ratingView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(16)))
        contentView.addView(ratingView)

        val buttonView = CoreButton(context)
        buttonView.setText(R.string.finish_training)
        buttonView.padding(context.dp(8))
        buttonView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginTop(context.dp(24)))
        contentView.addView(buttonView)

        return contentView
    }

}