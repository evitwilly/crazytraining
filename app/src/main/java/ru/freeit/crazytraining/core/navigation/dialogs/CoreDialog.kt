package ru.freeit.crazytraining.core.navigation.dialogs

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.extensions.dp

abstract class CoreDialog : DialogFragment() {

    protected open val radius: CornerRadiusType = CornerRadiusType.small
    abstract val name: String

    protected abstract fun createView(context: Context): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return createView(inflater.context)
    }

    override fun onResume() {
        super.onResume()
        val ctx = requireContext()
        val themeManager = (ctx.applicationContext as App).themeManager
        dialog?.window?.setBackgroundDrawable(GradientDrawable().apply {
            cornerRadius = themeManager.selected_theme.cornerRadiusStyle.style(ctx, radius)
        })
        val attributes = dialog?.window?.attributes
        attributes?.width = requireContext().resources.displayMetrics.widthPixels - ctx.dp(32)
        attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT
        attributes?.gravity = Gravity.CENTER
        dialog?.window?.attributes = attributes
    }

}