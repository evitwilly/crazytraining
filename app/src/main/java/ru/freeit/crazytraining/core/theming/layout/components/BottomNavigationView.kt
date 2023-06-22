package ru.freeit.crazytraining.core.theming.layout.components

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.text.TextType
import ru.freeit.crazytraining.core.theming.view.CoreImageView
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class BottomNavigationView(ctx: Context) : CoreLinearLayout(ctx, backgroundColor = ColorType.secondaryBackgroundColor) {

    private var selectedTabIndex = -1

    private val tabViews = mutableListOf<TabView>()

    init {
        id = R.id.bottom_navigation_view
        elevation = context.dp(8f)
        orientation = HORIZONTAL
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(super_state_key, super.onSaveInstanceState())
        bundle.putInt(selected_tab_key, selectedTabIndex)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            changeSelectedTab(state.getInt(selected_tab_key))
            super.onRestoreInstanceState(state.getParcelable(super_state_key))
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    fun changeSelectedTab(newIndex: Int) {
        if (selectedTabIndex == newIndex) return
        if (newIndex in 0..tabViews.lastIndex) {
            selectedTabIndex = newIndex
            tabViews.forEachIndexed { index, tabView ->
                val selected = newIndex == index
                tabView.wasSelected = selected
                if (selected) tabView.performClick()
            }
        }
    }

    fun changeTabs(tabs: List<BottomNavigationTab>) {
        removeAllViews()
        tabViews.clear()

        tabs.forEachIndexed { index, tab ->

            val cornerTreatmentStrategy = when {
                tabs.size == 1 -> CornerTreatmentStrategy.None()
                index == 0 -> CornerTreatmentStrategy.EndElliptical()
                index == tabs.size - 1 -> CornerTreatmentStrategy.StartElliptical()
                else -> CornerTreatmentStrategy.AllElliptical()
            }

            val tabContentView = TabView(context, cornerTreatmentStrategy)
            tabContentView.changeImage(tab.drawableResource)
            tabContentView.changeTitle(tab.stringResource)
            tabContentView.wasSelected = selectedTabIndex == index
            tabContentView.setOnClickListener {
                tab.click()
                changeSelectedTab(index)
            }
            tabContentView.layoutParams(linearLayoutParams().width(0).matchHeight().weight(1f))
            addView(tabContentView)
            tabViews.add(tabContentView)
        }
    }

    class BottomNavigationTab(
        @DrawableRes val drawableResource: Int,
        @StringRes val stringResource: Int,
        private val clickListener: () -> Unit
    ) {
        fun click() {
            clickListener.invoke()
        }
    }

    private class TabView(ctx: Context, cornerTreatmentStrategy: CornerTreatmentStrategy) : CoreLinearLayout(
        ctx,
        backgroundColor = ColorType.transparent,
        cornerRadiusStyle = CornerRadiusType.maximum,
        cornerTreatmentStrategy = cornerTreatmentStrategy,
        rippleColor = ColorType.primaryColor
    ) {

        private val imageView = CoreImageView(context)
        private val titleView = CoreTextView(context, textStyle = TextType.Caption2)

        var wasSelected: Boolean = false
            set(value) {
                field = value
                drawState()
            }

        init {
            orientation = VERTICAL
            gravity = Gravity.CENTER
            isClickable = true
            isFocusable = true

            imageView.adjustViewBounds = true
            imageView.layoutParams(linearLayoutParams().width(context.dp(24)).wrapHeight())
            addView(imageView)

            titleView.layoutParams(linearLayoutParams().wrap())
            addView(titleView)
        }

        private fun drawState() {
            if (wasSelected) {
                imageView.changeTint(ColorType.primaryColor)
                titleView.changeTextColor(ColorType.primaryColor)
            } else {
                imageView.changeTint(ColorType.primaryTextColor)
                titleView.changeTextColor(ColorType.primaryTextColor)
            }
        }

        fun changeImage(@DrawableRes drawableResource: Int) {
            imageView.setImageResource(drawableResource)
        }

        fun changeTitle(@StringRes stringResource: Int) {
            titleView.setText(stringResource)
        }

    }

    private companion object {
        const val super_state_key = "super_state_key"
        const val selected_tab_key = "selected_tab_key"
    }

}