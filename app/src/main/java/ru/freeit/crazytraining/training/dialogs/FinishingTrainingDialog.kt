package ru.freeit.crazytraining.training.dialogs

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.os.bundleOf
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

class FinishingTrainingDialog() : CoreDialog() {

    override val name: String = "FinishingTrainingDialog"

    private var commentView: CoreEditText? = null
    private var ratingView: TrainingRatingView? = null

    constructor(trainingType: String) : this() {
        arguments = bundleOf(training_type to trainingType)
    }

    override fun createView(context: Context, bundle: Bundle?): View {
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
        titleView.setText(R.string.training_completed)
        contentView.addView(titleView)

        val commentView = CoreEditText(context)
        commentView.changeLines(3)
        commentView.changeHint(R.string.write_comment_about_training)
        commentView.changeText(bundle?.getString(comment_key).orEmpty())
        commentView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(12)))
        contentView.addView(commentView)
        this.commentView = commentView

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
        ratingView.rating = bundle?.getInt(rating_key) ?: 4
        ratingView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(16)))
        contentView.addView(ratingView)
        this.ratingView = ratingView

        val buttonView = CoreButton(context)
        buttonView.setText(R.string.estimate)
        buttonView.padding(context.dp(8))
        buttonView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
            .marginTop(context.dp(24)))
        contentView.addView(buttonView)

        val trainingType = arguments?.getString(training_type).orEmpty()
        val fragmentResult = FinishingTrainingDialogResult(parentFragmentManager)
        val clickListener = View.OnClickListener {
            fragmentResult.successResult(trainingType, commentView.text, ratingView.rating)
            dismiss()
        }
        buttonView.setOnClickListener(clickListener)
        closeButtonView?.setOnClickListener(clickListener)

        return contentView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(comment_key, commentView?.text.orEmpty())
        outState.putInt(rating_key, ratingView?.rating ?: 4)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        commentView = null
        ratingView = null
    }

    private companion object {
        const val comment_key = "comment"
        const val rating_key = "rating"

        const val training_type = "training_type"
    }

}