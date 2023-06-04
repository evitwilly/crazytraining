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
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.frameLayoutParams
import ru.freeit.crazytraining.core.theming.extensions.layoutParams
import ru.freeit.crazytraining.core.theming.layout.components.CoreFrameLayout
import ru.freeit.crazytraining.core.theming.view.ToolbarButtonImageView
import ru.freeit.crazytraining.core.theming.view.ToolbarTitleTextView
import ru.freeit.crazytraining.core.viewmodel.viewModelFactory

abstract class BaseFragment<T : BaseViewModel>: Fragment() {

    protected lateinit var viewModel: T
    protected lateinit var navigator: Navigator

    private var rootView: CoreFrameLayout? = null
    private var titleView: TextView? = null
    private var menuButtonView: ImageView? = null

    private val menuButtonSize = 48
    private val menuButtonMarginStart = 4
    private val toolbarHeight = 48

    protected open val viewModelKClass: Class<T>
        get() = BaseViewModel::class.java as Class<T>
    protected open fun viewModelConstructor(ctx: Context): T = BaseViewModel() as T

    protected abstract fun createView(context: Context, bundle: Bundle?): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val context = inflater.context

        val factory = viewModelFactory { viewModelConstructor(context) }
        viewModel = ViewModelProvider(this, factory)[viewModelKClass]

        val rootView = CoreFrameLayout(context)
        this.rootView = rootView

        val toolbarView = CoreFrameLayout(context)
        toolbarView.layoutParams(frameLayoutParams().matchWidth().height(context.dp(toolbarHeight)))
        rootView.addView(toolbarView)

        val titleView = ToolbarTitleTextView(context)
        this.titleView = titleView
        titleView.maxLines = 2
        titleView.ellipsize = TextUtils.TruncateAt.END
        val titleMargins = context.dp(menuButtonSize + menuButtonMarginStart + 8)
        titleView.layoutParams(frameLayoutParams().wrap().gravity(Gravity.CENTER).marginStart(titleMargins).marginEnd(titleMargins))
        toolbarView.addView(titleView)

        navigator = Navigator(parentFragmentManager)
        val backButtonView = ToolbarButtonImageView(context)
        backButtonView.isVisible = navigator.is_not_top_fragment
        backButtonView.layoutParams(frameLayoutParams().width(context.dp(menuButtonSize)).height(context.dp(menuButtonSize))
            .gravity(Gravity.START or Gravity.CENTER_VERTICAL).marginStart(context.dp(menuButtonMarginStart)))
        backButtonView.scaleType = ImageView.ScaleType.CENTER
        backButtonView.setImageResource(R.drawable.ic_back)
        backButtonView.isClickable = true
        backButtonView.isFocusable = true
        backButtonView.setOnClickListener { navigator.back() }
        toolbarView.addView(backButtonView)

        val menuButtonView = ToolbarButtonImageView(context)
        this.menuButtonView = menuButtonView
        menuButtonView.isVisible = false
        menuButtonView.layoutParams(frameLayoutParams().width(context.dp(menuButtonSize)).height(context.dp(menuButtonSize))
            .gravity(Gravity.END or Gravity.CENTER_VERTICAL).marginEnd(context.dp(menuButtonMarginStart)))
        menuButtonView.scaleType = ImageView.ScaleType.CENTER
        menuButtonView.isClickable = true
        menuButtonView.isFocusable = true
        toolbarView.addView(menuButtonView)

        val contentView = createView(context, savedInstanceState)
        contentView.layoutParams(frameLayoutParams().match().marginTop(context.dp(toolbarHeight)))
        rootView.addView(contentView, 0)

        val bubbleMessageView = BubbleMessageView(context)
        bubbleMessageView.layoutParams(frameLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(toolbarHeight + 8)))
        rootView.addView(bubbleMessageView)

        viewModel.bubbleMessageState.observe(viewLifecycleOwner) { message ->
            bubbleMessageView.show(getString(message))
        }

        viewModel.navigationBack.observe(viewLifecycleOwner) {
            navigator.back()
        }

        return rootView
    }

    protected fun changeTitle(text: String) {
        titleView?.text = text
    }

    protected fun changeMenuButtonVisible(visible: Boolean) {
        menuButtonView?.isVisible = visible
    }

    protected fun changeMenuButtonDrawableResource(@DrawableRes drawableResource: Int) {
        menuButtonView?.setImageResource(drawableResource)
    }

    protected fun changeMenuButtonClickListener(clickListener: View.OnClickListener) {
        menuButtonView?.setOnClickListener(clickListener)
    }

    protected fun addFloatingView(view: View) {
        rootView?.addView(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rootView = null
        titleView = null
        menuButtonView = null
    }

}