package ru.freeit.crazytraining.training.dialogs.text_watchers

import android.text.Editable
import android.text.TextWatcher
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.view.CoreEditText

class MaxTextWatcher(
    private val editView: CoreEditText,
    private val max: Int = Int.MAX_VALUE,
    private val isErrorHandling: Boolean = false,
    private val amountListener: (Int) -> Unit
) : TextWatcher {

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        val string = text.toString()
        val newText = if (string.isBlank()) {
            if (isErrorHandling) {
                editView.error = CoreEditText.Error.Text(editView.context.getString(R.string.the_field_is_empty))
            }

            amountListener.invoke(0)

            ""
        } else {
            if (isErrorHandling) {
                editView.error = CoreEditText.Error.Empty
            }

            val integer = string.toIntOrNull() ?: 0
            val rangedNumber = if (integer > max) {
                max
            } else {
                integer
            }
            amountListener.invoke(rangedNumber)
            rangedNumber.toString()
        }
        editView.removeTextWatcher(this)
        editView.changeText(newText)
        editView.moveCursorToEnd()
        editView.addTextWatcher(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}

}