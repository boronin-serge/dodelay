package ru.boronin.dodelay.common.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import java.util.*

class WeekView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

  init {
    orientation = HORIZONTAL
  }

  fun makeWeek(infoByDays: List<DayInfo>) {
    val day = Calendar.getInstance()

    removeAllViews()

    infoByDays.forEachIndexed { index, dayInfo ->
      val dayOfMonth = day.run {
        add(Calendar.DAY_OF_MONTH, 1)
        get(Calendar.DAY_OF_MONTH).toString()
      }
      val dayLetter = String.format("%ta", day).first().toString()
      val state = when {
        dayInfo.numberOfProducts > 0 -> DayViewState.ERROR
        index == 0 -> DayViewState.TODAY
        else -> DayViewState.DEFAULT
      }
      addView(
        DayView(context).apply {
          setDate(dayOfMonth)
          setDayLetter(dayLetter)
          setBottomNumber(dayInfo.numberOfProducts)
          setState(state)
          layoutParams = LayoutParams(
            0,
            LayoutParams.WRAP_CONTENT,
            1f
          )
        }
      )
    }
  }
}

data class DayInfo(
  val numberOfProducts: Int
)
