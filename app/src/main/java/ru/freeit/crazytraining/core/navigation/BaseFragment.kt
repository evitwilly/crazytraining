package ru.freeit.crazytraining.core.navigation

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.extensions.*
import ru.freeit.crazytraining.core.theming.layout.components.CoreFrameLayout
import ru.freeit.crazytraining.core.theming.view.CorePrimaryImageView
import ru.freeit.crazytraining.core.theming.view.ToolbarTitleTextView

abstract class BaseFragment: Fragment() {

    protected lateinit var navigator: Navigator

    private var titleView: TextView? = null

    private val backButtonWidth = 32
    private val backButtonMarginStart = 12
    private val toolbarHeight = 48

    protected abstract fun createView(context: Context, bundle: Bundle?): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val context = inflater.context

        val rootView = CoreFrameLayout(context)

        val toolbarView = CoreFrameLayout(context)
        toolbarView.layoutParams(frameLayoutParams().matchWidth().height(context.dp(toolbarHeight)))
        rootView.addView(toolbarView)

        val titleView = ToolbarTitleTextView(context)
        this.titleView = titleView
        titleView.maxLines = 2
        titleView.ellipsize = TextUtils.TruncateAt.END
        val titleMargins = context.dp(backButtonWidth + backButtonMarginStart + 8)
        titleView.layoutParams(frameLayoutParams().wrap().gravity(Gravity.CENTER).marginStart(titleMargins).marginEnd(titleMargins))
        toolbarView.addView(titleView)

        navigator = Navigator(parentFragmentManager)
        val backButtonView = CorePrimaryImageView(context)
        backButtonView.isVisible = navigator.is_not_top_fragment
        backButtonView.layoutParams(frameLayoutParams().width(context.dp(backButtonWidth)).matchHeight()
            .marginStart(context.dp(backButtonMarginStart)))
        backButtonView.scaleType = ImageView.ScaleType.CENTER
        backButtonView.setImageResource(R.drawable.ic_back)
        backButtonView.isClickable = true
        backButtonView.isFocusable = true
        backButtonView.setOnClickListener { navigator.back() }
        toolbarView.addView(backButtonView)

        val contentView = createView(context, savedInstanceState)
        contentView.layoutParams(frameLayoutParams().match().marginTop(context.dp(toolbarHeight)))
        rootView.addView(contentView)

        return rootView
    }

    fun changeTitle(text: String) {
        titleView?.text = text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        titleView = null
    }

}