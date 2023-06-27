package ru.freeit.crazytraining.core.theming.layout.components

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.corners.ShapeAttribute
import ru.freeit.crazytraining.core.theming.corners.ShapeTreatmentStrategy
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreImageView
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class BottomNavigationView(ctx: Context) : CoreLinearLayout(ctx, backgroundColor = ColorAttributes.secondaryBackgroundColor) {

    private var selectedTabIndex = -1

    private val tabViews = mutableListOf<TabView>()
    private val tabModels = mutableListOf<BottomNavigationTab>()

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
            selectedTabIndex = state.getInt(selected_tab_key)
            drawState()
            super.onRestoreInstanceState(state.getParcelable(super_state_key))
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    fun changeSelectedTab(newIndex: Int) {
        if (selectedTabIndex == newIndex) return

        selectedTabIndex = newIndex

        tabModels.getOrNull(newIndex)?.click()

        drawState()
    }

    fun changeTabs(tabs: List<BottomNavigationTab>) {
        removeAllViews()
        tabViews.clear()

        tabModels.clear()
        tabModels.addAll(tabs)

        tabs.forEachIndexed { index, tab ->

            val shapeTreatmentStrategy = when {
                tabs.size == 1 -> ShapeTreatmentStrategy.None()
                index == 0 -> ShapeTreatmentStrategy.EndElliptical()
                index == tabs.size - 1 -> ShapeTreatmentStrategy.StartElliptical()
                else -> ShapeTreatmentStrategy.AllElliptical()
            }

            val tabContentView = TabView(context, shapeTreatmentStrategy)
            tabContentView.changeImage(tab.drawableResource)
            tabContentView.changeTitle(tab.stringResource)
            tabContentView.wasSelected = selectedTabIndex == index
            tabContentView.setOnClickListener {
                if (selectedTabIndex != index) {
                    selectedTabIndex = index
                    drawState()
                    tab.click()
                }
            }
            tabContentView.layoutParams(linearLayoutParams().width(0).matchHeight().weight(1f))
            addView(tabContentView)
            tabViews.add(tabContentView)
        }
    }

    private fun drawState() {
        tabViews.forEachIndexed { index, tabView ->
            val selected = selectedTabIndex == index
            tabView.wasSelected = selected
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

    private class TabView(ctx: Context, shapeTreatmentStrategy: ShapeTreatmentStrategy) : CoreLinearLayout(
        ctx,
        backgroundColor = ColorAttributes.transparent,
        shape = ShapeAttribute.maximum,
        shapeTreatmentStrategy = shapeTreatmentStrategy,
        rippleColor = ColorAttributes.primaryColor
    ) {

        private val imageView = CoreImageView(context)
        private val titleView = CoreTextView(context, textStyle = TextAttribute.Caption2)

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
                imageView.changeTint(ColorAttributes.primaryColor)
                titleView.changeTextColor(ColorAttributes.primaryColor)
            } else {
                imageView.changeTint(ColorAttributes.primaryTextColor)
                titleView.changeTextColor(ColorAttributes.primaryTextColor)
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