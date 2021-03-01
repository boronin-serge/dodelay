package ru.boronin.dodelay.features.home

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import ru.boronin.dodelay.common.presentation.mvvm.BaseView
import ru.boronin.dodelay.databinding.FragmentHomeBinding
import ru.boronin.dodelay.utils.lifecycle.viewBinding
import ru.boronin.dodelay.utils.other.listenWith

@AndroidEntryPoint
class HomeFragment : BaseView() {
  override val binding by viewBinding(FragmentHomeBinding::inflate)

  override fun onViewBound(view: View) {
    initObservers()
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
