package ru.boronin.dodelay.common.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import ru.boronin.common.extension.widget.getColor
import ru.boronin.common.utils.DEFAULT_STRING
import ru.boronin.dodelay.R
import ru.boronin.dodelay.databinding.ViewActionButtonBinding
import ru.boronin.dodelay.utils.lifecycle.viewBindingCall

class ActionButton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  private val binding = viewBindingCall {
    ViewActionButtonBinding.inflate(it, this, true)
  }

  private val activeTextColor = getColor(R.color.colorPrimaryDarkTextColor)
  private val inactiveTextColor = getColor(R.color.dark)

  init {
    context.withStyledAttributes(attrs, R.styleable.ActionButton) {
      if (hasValue(R.styleable.ActionButton_ab_text)) {
        binding.tvNext.text = getString(R.styleable.ActionButton_ab_text) ?: DEFAULT_STRING
      }

      if (hasValue(R.styleable.ActionButton_ab_textSize)) {
        binding.tvNext.textSize = getDimensionPixelSize(
          R.styleable.ActionButton_ab_textSize,
          0
        ).toFloat()
      }

      if (hasValue(R.styleable.ActionButton_ab_textColor)) {
        getColorStateList(R.styleable.ActionButton_ab_textColor)?.also {
          binding.tvNext.setTextColor(it)
        }
      }

      if (hasValue(R.styleable.ActionButton_ab_enable)) {
        enableView(
          getBoolean(R.styleable.ActionButton_ab_enable, true)
        )
      }

      if (hasValue(R.styleable.ActionButton_ab_icon)) {
        binding.ivIcon.isVisible = true
        binding.ivIcon.setImageDrawable(
          getDrawable(R.styleable.ActionButton_ab_icon)
        )
      }
    }
  }

  fun setProgressVisibility(visible: Boolean) {
    binding.progressBar.isVisible = visible
    binding.tvNext.visibility = if (visible) INVISIBLE else VISIBLE
    isClickable = visible.not()
  }

  fun setText(@StringRes resId: Int) {
    binding.tvNext.text = context.getString(resId)
  }

  fun setText(text: String) {
    binding.tvNext.text = text
  }

  fun enableView(enable: Boolean) {
    isEnabled = enable
    if (enable) {
      binding.tvNext.setTextColor(activeTextColor)
      binding.flActionButtonBackground.setBackgroundResource(R.drawable.bg_action_button)
    } else {
      binding.tvNext.setTextColor(inactiveTextColor)
      binding.flActionButtonBackground.setBackgroundResource(
        R.drawable.rectangle_rounded_light_gray
      )
    }
  }
}
