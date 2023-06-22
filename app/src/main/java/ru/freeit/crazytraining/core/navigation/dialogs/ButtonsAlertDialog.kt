package ru.freeit.crazytraining.core.navigation.dialogs

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextType
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class ButtonsAlertDialog() : CoreDialog() {

    override val name: String = "ButtonsAlertDialog"

    constructor(title: String, message: String, buttons: Buttons) : this() {
        arguments = bundleOf(
            title_key to title,
            message_key to message,
            buttons_key to buttons.ordinal
        )
    }

    override fun createView(context: Context): View {
        val contentView = CoreLinearLayout(
            ctx = context,
            cornerRadiusStyle = radius,
            backgroundColor = ColorType.secondaryBackgroundColor
        )
        contentView.clipChildren = true
        contentView.padding(top = context.dp(12))
        contentView.orientation = LinearLayout.VERTICAL

        val titleView = CoreTextView(context, textStyle = TextType.Title2)
        titleView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16)))
        contentView.addView(titleView)

        val descView = CoreTextView(context)
        descView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(4)))
        contentView.addView(descView)

        val buttonsView = CoreLinearLayout(context)
        buttonsView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginTop(context.dp(12)))
        buttonsView.orientation = LinearLayout.HORIZONTAL
        contentView.addView(buttonsView)

        val fragmentResult = ButtonsAlertDialogResult(parentFragmentManager)

        fun createButton(stringResource: Int): CoreButton {
            val buttonView = CoreButton(context)
            buttonView.setText(stringResource)
            buttonView.padding(context.dp(12))
            buttonView.layoutParams(linearLayoutParams().wrap().weight(1f))
            return buttonView
        }

        val args = arguments

        val title = args?.getString(title_key).orEmpty()
        titleView.text = title
        titleView.isVisible = title.isNotBlank()

        val message = args?.getString(message_key).orEmpty()
        descView.text = message

        val buttons = args?.getInt(buttons_key, Buttons.OK.ordinal) ?: Buttons.OK.ordinal
        when (Buttons.values()[buttons]) {
            Buttons.OK -> {
                val button = createButton(R.string.ok)
                button.setOnClickListener {
                    fragmentResult.okClick()
                    dismiss()
                }
                buttonsView.addView(button)
            }
            Buttons.OK_CANCEL -> {
                val okButton = createButton(R.string.ok)
                okButton.setOnClickListener {
                    fragmentResult.okClick()
                    dismiss()
                }
                buttonsView.addView(okButton)

                val spaceView = View(context)
                spaceView.layoutParams(linearLayoutParams().width(context.dp(1)).matchHeight())
                buttonsView.addView(spaceView)

                val cancelButton = createButton(R.string.cancel)
                cancelButton.setOnClickListener {
                    fragmentResult.cancelClick()
                    dismiss()
                }
                buttonsView.addView(cancelButton)
            }
            Buttons.ONLY_MESSAGES -> {}
        }

        return contentView
    }

    enum class Buttons {
        OK,
        OK_CANCEL,
        ONLY_MESSAGES
    }

    private companion object {
        const val title_key = "CoreDialog_title_key"
        const val message_key = "CoreDialog_message_key"
        const val buttons_key = "CoreDialog_buttons_key"
    }

}