package ru.freeit.crazytraining.core.navigation.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class BubbleMessageView(ctx: Context) : CoreTextView(ctx, textColor = ColorAttributes.colorOnPrimary, textStyle = TextAttribute.Body2) {

    private var animator: Animator? = null
    private val hideRunnable = Runnable { isVisible = false }

    init {
        isVisible = false
        includeFontPadding = false
        background = GradientDrawable().apply {
            setColor(themeManager.selected_theme.colors[ColorAttributes.primaryColor])
            cornerRadius = context.dp(24f)
        }
        padding(context.dp(12))
    }

    fun show(message: String, duration: Long = 350, delay: Long = 3000) {
        text = message
        isVisible = true
        animator?.cancel()
        handler.removeCallbacks(hideRunnable)

        val scaleAnimator = ObjectAnimator.ofFloat(this, SCALE_X, 0f, 1f)
        val translationAnimator = ObjectAnimator.ofFloat(this, TRANSLATION_Y, -context.dp(24f), 0f)
        val animator = AnimatorSet()
        this.animator = animator
        animator.playTogether(scaleAnimator, translationAnimator)
        animator.duration = duration
        animator.doOnEnd {
            handler.postDelayed(hideRunnable, delay)
        }
        animator.start()
    }

}