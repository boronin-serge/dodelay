package ru.boronin.dodelay.features.home

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import ru.boronin.dodelay.common.presentation.mvvm.BaseView
import ru.boronin.dodelay.common.presentation.view.DayInfo
import ru.boronin.dodelay.databinding.FragmentHomeBinding
import ru.boronin.dodelay.utils.lifecycle.viewBinding
import ru.boronin.dodelay.utils.other.listenWith

@AndroidEntryPoint
class HomeFragment : BaseView() {
  override val binding by viewBinding(FragmentHomeBinding::inflate)

  override fun onViewBound(view: View) {
    initObservers()

    val info = listOf(
      DayInfo(0),
      DayInfo(2),
      DayInfo(0),
      DayInfo(0),
      DayInfo(4),
      DayInfo(0)
    )

    binding.weekView.makeWeek(info)
  }

  // region private

  private fun initListeners() {
  }

  private fun initObservers() {
    with(binding) {
      btnAddProduct.clicks().listenWith(subscriptions) {
      }
    }
  }

  // endregion
}
