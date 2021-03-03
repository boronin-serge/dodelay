package ru.boronin.dodelay.common.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.isVisible
import ru.boronin.common.extension.core.findDrawable
import ru.boronin.dodelay.R
import ru.boronin.dodelay.databinding.ViewDayBinding
import ru.boronin.dodelay.utils.lifecycle.viewBindingCall

class DayView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
  private val binding = viewBindingCall {
    ViewDayBinding.inflate(it, this, true)
  }

  fun setDate(date: String) {
    binding.tvDate.text = date
  }

  fun setDayLetter(letter: String) {
    binding.tvDayLetter.text = letter
  }

  fun setBottomNumber(number: Int) {
    binding.tvNumber.text = number.toString()
  }

  fun setState(state: DayViewState) {
    val backgroundResId = when (state) {
      DayViewState.TODAY -> R.drawable.circle_primary
      DayViewState.ERROR -> R.drawable.circle_error
      DayViewState.DEFAULT -> R.drawable.circle_lighten_dark
    }
    binding.tvDayLetter.background = context.findDrawable(backgroundResId)
    binding.tvNumber.isVisible = state == DayViewState.ERROR
  }
}

enum class DayViewState {
  TODAY,
  ERROR,
  DEFAULT
}
