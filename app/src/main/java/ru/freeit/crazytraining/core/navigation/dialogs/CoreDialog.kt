package ru.freeit.crazytraining.core.navigation.dialogs

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.frameLayoutParams
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy
import ru.freeit.crazytraining.core.theming.layout.components.CoreFrameLayout
import ru.freeit.crazytraining.core.theming.view.CoreImageButtonView

abstract class CoreDialog : DialogFragment() {

    protected open val radius: CornerRadiusType = CornerRadiusType.small
    abstract val name: String

    protected abstract fun createView(context: Context): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val context = inflater.context
        val contentView = CoreFrameLayout(context, ColorType.secondaryBackgroundColor)

        val buttonSize = context.dp(24)

        val closeButtonView = CoreImageButtonView(context, cornerRadiusType = CornerRadiusType.small, cornerTreatmentStrategy = CornerTreatmentStrategy.StartBottomTopEndRounded())
        closeButtonView.setImageResource(R.drawable.ic_close)
        closeButtonView.padding(context.dp(4))
        closeButtonView.layoutParams(frameLayoutParams().width(buttonSize).height(buttonSize).gravity(Gravity.END))
        closeButtonView.setOnClickListener { dismiss() }
        contentView.addView(closeButtonView)

        val view = createView(context)
        view.layoutParams(frameLayoutParams().match().marginTop(buttonSize))
        contentView.addView(view)
        return contentView
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